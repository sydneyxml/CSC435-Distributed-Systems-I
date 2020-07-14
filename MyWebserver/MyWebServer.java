import java.io.*;	// I/O
import java.net.*;	// network lib


/*--------------------------------------------------------

1. Name / Date:

Ximan Liu
2020.5.3

2. Java version used, if not the official version for the class:

javac 11.0.4

3. Precise command-line compilation examples / instructions:

> javac MyWebServer.java

4. Precise examples / instructions to run this program:

In separate shell windows:

> java MyWebServer

All acceptable commands are displayed on the various consoles.

This runs across machines, in which case you have to pass the IP address of
the server to the clients. For exmaple, if the server is running at
140.192.1.22 then you would type:

> java JokeClient 140.192.1.22
> java JokeClientAdmin 140.192.1.22

5. List of files needed for running the program.

 a. MyWebServer.java

5. Notes:

copy from MyListener
MultiThreaded server --> Web server

----------------------------------------------------------*/


class ListenWorker extends Thread { // class ListenWorker
	
	// local socket
	Socket socket;
	// constructor 
	ListenWorker (Socket s) {socket = s;}          
	
	// run the program
	public void run(){
		
		// output from socket
		PrintStream out = null;
		// input from socket
		BufferedReader in = null;
		
		try {
			// assign
			out = new PrintStream(socket.getOutputStream());
			// get connection
			in = new BufferedReader
					(new InputStreamReader(socket.getInputStream()));

			String socketdata;
			
			while (1 > 0) {
				//read lines
				socketdata = in.readLine ();
				
				// if it is not null
				if (socketdata == null || socketdata.length() == 0) return;
				
				else {
					
					// substring socketdata
					String name = socketdata.substring(4, socketdata.length() - 9);
					
						String contenttype;
						
						// two mime types: text/plain, text/html
						// txt java
						if (name.endsWith(".java") || name.endsWith(".txt")) {
							contenttype = "text/plain";
							filecontent(name, contenttype, out);
							// print name on terminal
							System.out.println(name);
						}
					
						// html
						else if (name.endsWith(".html")) {
							System.out.println(name);
							contenttype = "text/html";
							filecontent(name, contenttype, out);
							// print name on terminal
							System.out.println(name);
						}
					
						// cgi
						else if (name.contains("cgi")){
							System.out.println(name);
							contenttype = "text/html";
							addnums(name, contenttype, out);
							// print name on terminal
							System.out.println(name);
						}
						
						// for text/plain header and directory
						else if(!name.endsWith("/")){
							System.out.println(name);
							contenttype = "text/plain";
							filecontent(name, contenttype, out);
							// print name on terminal
							System.out.println(name);
						}
						
						// for text/html header
						else if (name.endsWith("/")){
							System.out.println(name);
							contenttype = "text/html";
							directorycontent(name, contenttype, out);
							// print name on terminal
							System.out.println(name);
						}
					}	socket.close();	// close socket
				}
		} catch (IOException x) {
			System.out.println("Reset connection. Start listening! ");
			}
	}
	
	
	// from directory to browser
	public void directorycontent(String name, String contenttype, PrintStream in) {
		
		// send back MIME type information
		in.println("HTTP/1.1 200 OK");
		// content length
		in.println("Content-Length: 47");
		// from hints
		// content types
		in.println("Content-Type: " + contenttype + "\r\n\r\n");
	
		// from MyWebServer Tips
		File f = new File("./" + name + "/");
		File[] strFilesDirs = f.listFiles ( );
	
		// from ReadFiles.java
		// read contents of directory and return a directory in HTML
		// format
		if (strFilesDirs != null) {
		for (int i = 0; i < strFilesDirs.length; i++) {
			if (strFilesDirs[i].isDirectory()) {
				in.println("Directory: " + "<a href=\"" + strFilesDirs[i].getName() + "/\">" + strFilesDirs[i].getName() +"</a><br>" ) ;
			}
			else if ( strFilesDirs[i].isFile()) {
				in.println( "File: " + "<a href=\"" + strFilesDirs[i].getName() + "\">"+ strFilesDirs[i].getName() + "</a>" + "(" + strFilesDirs[i].length() + ")" + "<br>") ;
			}
		} 
		} 
	}

	
	// from file to browser
	public void filecontent(String name, String contenttype, PrintStream in) throws IOException{
		
		// initialize
		InputStream input = null;
		File f = null;
		
		// File f1 will be participated in directory
     	File f1 = new File(name);
		
		if(!f1.isFile() && !name.equals("/")) {
			
			// deal with slash
			input = new FileInputStream(name.substring(1, name.length()));
			f = new File(name.substring(1, name.length())); 
		
     	    // send back MIME type information
     	    in.println("HTTP/1.1 200 OK");
     	    // content length
     	    in.println("Content-Length: " + f.length());
     	    // from hints
     	    // content types
     	    in.println("Content-Type: " + contenttype + "\r\n\r\n");	
     	    // create a new space
     	    byte[] space = new byte[999999];
        	// read space content
     	    int br = input.read(space);
     	    // write space content
     	    in.write(space, 0, br);
     	    // clear flush
     	    in.flush();
     	    // close input
     	    input.close();
		}
	}

	
	// return HTML formatted output to client, pass data through it
	public void addnums(String name, String contenttype, PrintStream in){
		
		// view page source code from addnums web form
		// http://localhost:2540/cgi/addnums.fake-cgi
		// c in cgi starts at index of 22
		String line = name.substring(22, name.length());
		// create an array
		String [] word = line.split("[=&]");

		// certain index to split them apart
		String n0 = word[1];
		String n1 = word[3];
		String n2 = word[5];
		
		// return an integer
		int sum = Integer.parseInt(n1) + Integer.parseInt(n2);
		
		// Send back an HTML page, return name and sum of numbers
		String sendback = "Dear " + n0 + ", the sum of " + n1+ " and " + n2 + " is " + sum;
		// content length
		int back = sendback.length();

		// send back MIME type information
		in.print("HTTP/1.1 200 OK");
		// content length
		in.print("Content-Length: " + back);
		// from hints
		// content types
		in.print("Content-type: " + contenttype + "\r\n\r\n");
		in.print("<p>"+ sendback +"</p>");
	}
}	
	
	public class MyWebServer { // multi-thread server
		
		public static boolean cs = true;
	
		public static void main(String a[]) throws IOException {
			int length = 6;		/* Requests numbers */
			int port = 2540;	// localhost
			Socket socket;

			ServerSocket ss = new ServerSocket(port, length);

			System.out.println("Ximan Liu's Port listener running at 2540.\n");
			while (cs) {
				// wait for next connection to client
				socket = ss.accept();
				new ListenWorker (socket).start();
			}
		}
	}

