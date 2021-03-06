package connexion;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import client.Client;

//check username and pwd with user.txt file, if ok, validate the connexion
public class ClientConnexion implements java.io.Serializable {
	
	public void getFile(String filename, String IP, int port, String path) {	
		try {
			// connect to server based on parameters given in constructor
			Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostName(),port);
			
			// send file name AND path to server
			ObjectOutputStream OoutFilename = new ObjectOutputStream(clientSocket.getOutputStream());

			OoutFilename.writeUTF(filename + ";" + path);	
			OoutFilename.flush();
					
			// get file from server			
			InputStream OinFile = clientSocket.getInputStream();
			
			// IOUtils is used from referenced libraries commons io 2.6	
			byte[] byteArray = IOUtils.toByteArray(OinFile);
			
			// write new file in share folder
			FileOutputStream fos = new FileOutputStream(
			new File(".\\downloads\\" + filename));

			fos.write(byteArray);
			fos.close();
			
			System.out.println("file downloaded successfuly");
					
			OoutFilename.close();
			clientSocket.close();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[][] connectClient(client.Client client, int clientPort) {
					
			String[][] result = null; //0 = failed connexion, 1 = succesful connexion
			Socket clientSocket = new Socket();
			InetAddress serverAddress = null;
	        	
			try {
				serverAddress = InetAddress.getLocalHost();
				System.out.println("Get the address of the server : "+ serverAddress.getHostAddress());

				//Ask the server to create a new socket
				try{

					clientSocket = new Socket(serverAddress.getHostAddress(),49999);

					System.out.println("We got the connexion to  "+ serverAddress);
					
					//send username + password + + client Port to server
					ObjectOutputStream OoutUsername = new ObjectOutputStream(clientSocket.getOutputStream());

					OoutUsername.writeUTF(client.getUsername() + ";" + client.getPwd() + ";" + clientPort);
					
					OoutUsername.flush();
										
					//get list of available files from server
					ObjectInputStream OinListClients = 
							new ObjectInputStream(clientSocket.getInputStream());
					
					try {
						result = (String[][]) OinListClients.readObject();
						if (result.length < 1)
						{
							result = new String[1][1];
							result[0][0] = "empty";
						}
						else
						{
							System.out.println(result);
							if(result[0][0].equals("failed connection"))
								result = null;
						}	
						
					} 
					catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
					OinListClients.close();
					OoutUsername.close();
					
					// CLIENT starts SERVER
					// to enable other CLIENTS to connect
					// USE SOCKET 50'000
					
					BecomeServer cServer = new BecomeServer();		
					
					Thread thread = new Thread()
					{
					    public void run()
					    {
					    	cServer.connectServer(clientPort);
					    }
					};
					
					thread.start();

				}
					
				catch(IOException ex)
				{
					System.out.println("connection didn't work");
				}	
				
			}catch (UnknownHostException e) {

				e.printStackTrace();

			}catch (@SuppressWarnings("hiding") IOException e) {

				e.printStackTrace();
			}		
			
			return result;
	}
	
	public void registerNewClient(client.Client client, String folderName) { 
		
		// should be server side normally
		String FILENAME = "../FileShareServer/src/db/db";
		
		try {		
			FileWriter fw = new FileWriter(FILENAME, true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.append(client.getUsername() + ";" + client.getPwd() + ";" + folderName + ";" + "IP" + ";" + "50000" + ";" + "0\n");
			bw.flush();
			bw.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
