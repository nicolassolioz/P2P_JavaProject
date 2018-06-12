package connexion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStream;

import javax.swing.DefaultListModel;

public class ServerConnexion implements java.io.Serializable{

	private static final Logger LOGGER = Logger.getLogger(ConnectThread.class.getName());
	
	public Boolean isShuttingDown = false;
	
	/*public ServerConnexion() {
		
	}*/
	public void shutDown()
	{
		System.out.println("killing the server");
		isShuttingDown = true;
	}
	
	public void connectServer() {
		ServerSocket mySkServer = null ;
		
		Socket srvSocket = null ;
		InetAddress localAddress=null;

		// LOGGING PARAMETERS
		logging.CustomFileHandler customFh = new logging.CustomFileHandler();
		FileHandler fh = customFh.setFileHandler();
		LOGGER.addHandler(fh);

		try {

			localAddress = InetAddress.getLocalHost();
			mySkServer = new ServerSocket(49999,5,localAddress);

			System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
			System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());

			mySkServer.setSoTimeout(1000);

            //Listen to a client connection wait until a client connects			
			System.out.println("Waiting for a client connection:");

			
			while (!isShuttingDown) {
	            try {
	                srvSocket = mySkServer.accept();
	                // new thread for a client      
	                ConnectThread thread = new ConnectThread(srvSocket);
		            thread.start();
	            } catch (IOException e) {
	            	//e.printStackTrace();
	            }       
	        }

			mySkServer.close();
			
		}catch (SocketException e) {

			LOGGER.log(Level.WARNING, "error while connecting with server", e);
			e.printStackTrace();
		}
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, "error while connecting with server", e);
			e.printStackTrace();
		}

	}
	

}
