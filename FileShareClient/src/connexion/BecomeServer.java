package connexion;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

// class used when client connects to server
// now, the client enables connections to himself in order to send files

public class BecomeServer {
	
	// boolean used to indicate to server to shutdown
	public Boolean isShuttingDown = false;
	
	public void shutDown()
	{
		System.out.println("killing the server");
		isShuttingDown = true;
	}
	
	public void connectServer(int port) {
		ServerSocket mySkServer = null ;
		
		Socket srvSocket = null ;
		InetAddress localAddress=null;

		try {
			localAddress = InetAddress.getLocalHost();
			mySkServer = new ServerSocket(port,5,localAddress);

			System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());

			mySkServer.setSoTimeout(1000);

            // Listen to a client connection wait until a client connects			
			System.out.println("Waiting for a client connection:");
	
			while (!isShuttingDown) {
	            try {
	                srvSocket = mySkServer.accept();
	                // new thread for a new connection   
	                ConnectThread thread = new ConnectThread(srvSocket);
		            thread.start();
	            } catch (IOException e) {
	                // no new connection
	            }       
	        }

			mySkServer.close();
			
		}catch (SocketException e) {
			e.printStackTrace();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
