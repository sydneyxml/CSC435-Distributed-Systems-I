/*--------------------------------------------------------

1. Name / Date:

Ximan Liu
2020.5.31

2. Java version used, if not the official version for the class:

java 8

3. Precise command-line compilation examples / instructions:

> javac HostServer.java

4. Precise examples / instructions to run this program:

In separate shell windows:
// for MacOS
> java HostServer

5. List of files needed for running the program.

a. HostServer.java

6. Notes:

This file will run HostServer.java and hosting agents will migrate from one server and port to another one.

----------------------------------------------------------*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


// AgentWorker is created by AgentListener and dealt with requests made by AgentListener.
class AgentWorker extends Thread {
	
  // set up connection
  Socket sock;
  // agentHolder working with state and socket
  agentHolder parentAgentHolder;
  // set up local port
  int localPort;
  
  // constructor
  AgentWorker (Socket s, int prt, agentHolder ah) {
    sock = s;
    localPort = prt;
    parentAgentHolder = ah;
  }
  public void run() {
    
    //initialization
    PrintStream out = null;
    BufferedReader in = null;
    // server name "localhost"
    String NewHost = "localhost";
    // port number
    int NewHostMainPort = 4242;		
    String buf = "";
    int newPort;
    Socket clientSock;
    BufferedReader fromHostServer;
    PrintStream toHostServer;
    
    try {
      out = new PrintStream(sock.getOutputStream());
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      
      //read lines
      String inLine = in.readLine();
      // set up html string for proper length
      StringBuilder htmlString = new StringBuilder();
      
      // print
      System.out.println();
      System.out.println("Request line: " + inLine);
      
      if(inLine.indexOf("migrate") > -1) {
	// switch to new port if possible
    // set up a new socket
	clientSock = new Socket(NewHost, NewHostMainPort);
	fromHostServer = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
	// pass request to port
	toHostServer = new PrintStream(clientSock.getOutputStream());
	toHostServer.println("Please host me. Send my port! [State=" + parentAgentHolder.agentState + "]");
	toHostServer.flush();
	
	// read content
	for(;;) {
	  // read lines
	  buf = fromHostServer.readLine();
	  if(buf.indexOf("[Port=") > -1) {
	    break;
	  }
	}
	
	// set up a string for port
	String tempbuf = buf.substring( buf.indexOf("[Port=")+6, buf.indexOf("]", buf.indexOf("[Port=")) );
	// parse Int for tempbuf
	newPort = Integer.parseInt(tempbuf);
	// print out
	System.out.println("newPort is: " + newPort);
	
	// append content to html string
	htmlString.append(AgentListener.sendHTMLheader(newPort, NewHost, inLine));
	htmlString.append("<h3>We are migrating to host " + newPort + "</h3> \n");
	htmlString.append("<h3>View the source of this page to see how the client is informed of the new location.</h3> \n");
	// finish appending
	htmlString.append(AgentListener.sendHTMLsubmit());
	
	// print out
	System.out.println("Killing parent listening loop.");
	// set up socket for old socket
	ServerSocket ss = parentAgentHolder.sock;
	// close port
	ss.close();
	
	
      } else if(inLine.indexOf("person") > -1) {
    // increase agentState
	parentAgentHolder.agentState++;
	// add content to htmlString
	htmlString.append(AgentListener.sendHTMLheader(localPort, NewHost, inLine));
	htmlString.append("<h3>We are having a conversation with state   " + parentAgentHolder.agentState + "</h3>\n");
	htmlString.append(AgentListener.sendHTMLsubmit());
	
      } else {
    // let user know invalid
	htmlString.append(AgentListener.sendHTMLheader(localPort, NewHost, inLine));
	htmlString.append("You have not entered a valid request!\n");
	htmlString.append(AgentListener.sendHTMLsubmit());		
	
      }
      // output html
      AgentListener.sendHTMLtoStream(htmlString.toString(), out);
      
      // close socket
      sock.close();
      
      
    } catch (IOException ioe) {	// if there is something wrong
      System.out.println(ioe);
    }
  }
  
}

// agentHolder class
class agentHolder {
  // set up server socket
  ServerSocket sock;
  // set up agentState
  int agentState;
  
  // constructor
  agentHolder(ServerSocket s) { sock = s;}
}



// AgentListener is created by hostserver
class AgentListener extends Thread {
  //instance vars
  Socket sock;
  int localPort;
  
  // constructor
  AgentListener(Socket As, int prt) {
    sock = As;
    localPort = prt;
  }
  // initialize agentState to 0
  int agentState = 0;
  
  // run function
  public void run() {
    BufferedReader in = null;
    PrintStream out = null;
    String NewHost = "localhost";
    System.out.println("In AgentListener Thread");		
    try {
      String buf;
      out = new PrintStream(sock.getOutputStream());
      in =  new BufferedReader(new InputStreamReader(sock.getInputStream()));
      
      // read lines
      buf = in.readLine();
      
      // store the states
      if(buf != null && buf.indexOf("[State=") > -1) {
	// keep content from readlines
	String tempbuf = buf.substring(buf.indexOf("[State=")+7, buf.indexOf("]", buf.indexOf("[State=")));
	// parse to Int
	agentState = Integer.parseInt(tempbuf);
	// print out
	System.out.println("agentState is: " + agentState);
	
      }
      
      System.out.println(buf);
      // set up String Builder
      StringBuilder htmlResponse = new StringBuilder();
      // append content to htmlResponse
      htmlResponse.append(sendHTMLheader(localPort, NewHost, buf));
      htmlResponse.append("Now in Agent Looper starting Agent Listening Loop\n<br />\n");
      htmlResponse.append("[Port="+localPort+"]<br/>\n");
      htmlResponse.append(sendHTMLsubmit());
      // display
      sendHTMLtoStream(htmlResponse.toString(), out);
      
      // build up server socket a new connection
      ServerSocket servsock = new ServerSocket(localPort,2);
      // set up a new agentHolder
      agentHolder agenthold = new agentHolder(servsock);
      agenthold.agentState = agentState;
      
      // build up connection
      while(true) {
	sock = servsock.accept();
	// print out
	System.out.println("Got a connection to agent at port " + localPort);
	// build up connection
	new AgentWorker(sock, localPort, agenthold).start();
      }
      
    } catch(IOException ioe) {
      // error
      System.out.println("Either connection failed, or just killed listener loop for agent at port " + localPort);
      System.out.println(ioe);
    }
  }
  
  
  // send HTML content to header
  static String sendHTMLheader(int localPort, String NewHost, String inLine) {
    
    StringBuilder htmlString = new StringBuilder();
    
    htmlString.append("<html><head> </head><body>\n");
    htmlString.append("<h2>This is for submission to PORT " + localPort + " on " + NewHost + "</h2>\n");
    htmlString.append("<h3>You sent: "+ inLine + "</h3>");
    htmlString.append("\n<form method=\"GET\" action=\"http://" + NewHost +":" + localPort + "\">\n");
    htmlString.append("Enter text or <i>migrate</i>:");
    htmlString.append("\n<input type=\"text\" name=\"person\" size=\"20\" value=\"YourTextInput\" /> <p>\n");
    
    return htmlString.toString();
  }

  // finish html file
  static String sendHTMLsubmit() {
    return "<input type=\"submit\" value=\"Submit\"" + "</p>\n</form></body></html>\n";
  }
  
  // send HTML to stream
  static void sendHTMLtoStream(String html, PrintStream out) {
    
    out.println("HTTP/1.1 200 OK");
    out.println("Content-Length: " + html.length());
    out.println("Content-Type: text/html");
    out.println("");		
    out.println(html);
  }
  
}

// main class HostServer
public class HostServer {
  // build up listening at port
  public static int NextPort = 3000;
  
  public static void main(String[] a) throws IOException {
    int q_len = 6;
    int port = 4242;
    Socket sock;
    
    ServerSocket servsock = new ServerSocket(port, q_len);
    System.out.println("Elliott/Reagan DIA Master receiver started at port 4242.");
    System.out.println("Connect from 1 to 3 browsers using \"http:\\\\localhost:4242\"\n");
    // port 4242 for requests
    while(true) {
      // increase port
      NextPort = NextPort + 1;
      // build up acception for socket
      sock = servsock.accept();
      // print out
      System.out.println("Starting AgentListener at port " + NextPort);
      // wait for orders
      new AgentListener(sock, NextPort).start();
    }
    
  }
}


