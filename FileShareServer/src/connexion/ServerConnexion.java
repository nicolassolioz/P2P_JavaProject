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
import java.io.OutputStream;

import javax.swing.DefaultListModel;

public class ServerConnexion implements java.io.Serializable{

	public void connectServer() {
		ServerSocket mySkServer ;
		Socket srvSocket = null ;
		InetAddress localAddress=null;


		try {

			localAddress = InetAddress.getLocalHost();
			mySkServer = new ServerSocket(49999,5,localAddress);

			System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
			System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());

			mySkServer.setSoTimeout(30000);//set 30 sec timout

			interfaces.HomeInterface homeInterface = new interfaces.HomeInterface();
			homeInterface.lblServer.setText("online");
            //Listen to a client connection wait until a client connects			
			System.out.println("Waiting for a client connection:");
			srvSocket = mySkServer.accept(); 
			
			int usedPort = mySkServer.getLocalPort();
			System.out.println("Used port : " + usedPort);
			System.out.println("A client is connected");

			
			//get client username
			ObjectInputStream OinUsername = new ObjectInputStream(srvSocket.getInputStream());
			
			String username = OinUsername.readUTF();
			
			//send client list of available files with their respectives owners / IP numbers
			fileManager.FileManager files = new fileManager.FileManager();
			
			ObjectOutputStream OoutListClients = new ObjectOutputStream(srvSocket.getOutputStream());
			
			String[][] availableFiles = files.GetAvailableFiles(username);
			
			

			OoutListClients.writeObject(availableFiles);
			
			OoutListClients.flush();
			OinUsername.close();
			OoutListClients.close();
				
			mySkServer.close();
			srvSocket.close();

			System.out.println("Closing socket....");

		}catch (SocketException e) {

			//System.out.println("Connection Timed out");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}