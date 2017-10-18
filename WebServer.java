/**
 * WebServer Class
 * @author Sodienye Nkwonta ID: 30000197
 * 
 * 
 */

import java.util.*;
import java.io.*;
import java.net.*;


public class WebServer extends Thread {
	public boolean shutdown = false;
	public int portNum;
	public ServerSocket socket;

    /**
     * Default constructor to initialize the web server
     * 
     * @param port 	The server port at which the web server listens > 1024
     * 
     */
	public WebServer(int port) {
		try {
			socket = new ServerSocket(portNum);
			socket.setSoTimeout(1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
    /**
     * The main loop of the web server
     *   Opens a server socket at the specified server port
	 *   Remains in listening mode until shutdown signal
	 * 
     */
	public void run() {
		
		while (!shutdown) {
			try {
				Socket socket = socket.accept();		//accept a new connection
				WorkerThread connect = new WorkerThread(socket);
				connect.start();
//			- create a worker thread to handle the new connection
				} catch (SocketTimeoutException e) {
//					shutdown = true;
					System.out.println(e);
				}
			}// while
			// optional: a good implementation will wait for all running worker threads to
			//terminate before terminating the server. You can keep track of your worker
			//threads using a list and then call join() on each of them. A better approach is
			//to use Java ExecutorService to schedule workers using a FixedThreadPool executor
			//.
		
		socket.close();
	}

	
    /**
     * Signals the server to shutdown.
	 *
     */
	public void shutdown() {
		shutdown = true;
	}

	
	/**
	 * A simple driver.
	 */
	public static void main(String[] args) {
		int serverPort = 2225;

		// parse command line args
		if (args.length == 1) {
			serverPort = Integer.parseInt(args[0]);
		}
		
		if (args.length >= 2) {
			System.out.println("wrong number of arguments");
			System.out.println("usage: WebServer <port>");
			System.exit(0);
		}
		
		System.out.println("starting the server on port " + serverPort);
		
		WebServer server = new WebServer(serverPort);
		
		server.start();
		System.out.println("server started. Type \"quit\" to stop");
		System.out.println(".....................................");

		Scanner keyboard = new Scanner(System.in);
		while ( !keyboard.next().equals("quit") );
		
		System.out.println();
		System.out.println("shutting down the server...");
		server.shutdown();
		System.out.println("server stopped");
	}
	
}
