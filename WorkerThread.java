import java.net.*;
import java.io.*;
import java.util.*;
public class WorkerThread implements Runnable{
	
	public Socket sock;

	public WorkerThread(Socket socket) {
		sock = socket;
	}
	
	public void run(){
		
		
		try{
			
			
			sock.close();
		}catch(IOException e){
			e.getMessage();
		}
	}

}
