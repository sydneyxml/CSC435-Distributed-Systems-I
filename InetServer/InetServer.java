

import java.io.*;
import java.net.*;

// Ximan Liu
// InetServer.java
// server -> run func(input/output to socket) -> print output(result to socket + back to client) -> close socket

class Worker extends Thread { // class Worker
	
	Socket sock; // local sock
	Worker (Socket s) {sock = s;} // constructor
	
	public void run(){
		// run the program
		
		PrintStream out = null;		// output
		BufferedReader in = null;	// input
		
		try {
			in = new BufferedReader
					(new InputStreamReader(sock.getInputStream()));		// get connection
			out = new PrintStream(sock.getOutputStream());
			// may not process
			
			try {
				String name;
				name = in.readLine ();	//read lines of data from socket
				System.out.println("Looking up " + name);
				printRemoteAddress(name, out);
			} catch (IOException x) {
				System.out.println("Server read error");
				x.printStackTrace ();
			}
			sock.close(); // close socket
		} catch (IOException ioe) {System.out.println(ioe);}
	}

	// print
	static void printRemoteAddress (String name, PrintStream out) {
		try {
			out.println("Looking up " + name + "...");
			InetAddress machine = InetAddress.getByName (name);	// get data
			out.println("Host name : " + machine.getHostName ());
			out.println("Host IP : " + toText (machine.getAddress ()));	// to socket
		} catch(UnknownHostException ex) {	// error
			out.println ("Failed in atempt to look up " + name);
			}
		}
	
	// format IP address
	static String toText (byte ip[]) {
		StringBuffer result = new StringBuffer ();
		for (int i = 0; i < ip.length; ++ i) {
			if (i > 0) result.append (".");
			result.append (0xff & ip[i]);
			}
		return result.toString ();
		}
	}

public class InetServer { // multi-thread server
	
	public static void main(String a[]) throws IOException {
		
		int q_len = 6; /* Operate systems catch >= 6 servers. Can be changed */
		int port = 1565;
		Socket sock;

		ServerSocket servsock = new ServerSocket(port, q_len);

		System.out.println
		("Clark Elliott's Inet server 1.8 starting up, listening at port 1565.\n");
		while (true) {
		sock = servsock.accept(); // serversocket accept connection and wait
		new Worker(sock).start(); // start thread
		}
	}

}

