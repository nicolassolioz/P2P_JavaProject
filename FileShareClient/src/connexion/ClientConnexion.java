package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
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

import client.Client;

//check username and pwd with user.txt file, if ok, validate the connexion
public class ClientConnexion implements java.io.Serializable {
	
	public void registerNewClient(client.Client client) {
		
		//check if username doesn't already exist...
		String FILENAME = "../FileShareServer/src/db/db";
		String folderName = "Shared" + client.getUsername();
		
		try {
			FileWriter fw = new FileWriter(FILENAME, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.newLine();
			bw.write(client.getUsername() + ";" + client.getPwd() + ";" + folderName + ";" + "IP" + ";" + "0");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String[][] connectClient(client.Client client) {
					
			String[][] result = null; //0 = failed connexion, 1 = succesful connexion
			Socket clientSocket = new Socket();
			InetAddress serverAddress = null;
	        
			//check if client exist, then connect to server
			
			try {
				serverAddress = InetAddress.getLocalHost();
				System.out.println("Get the address of the server : "+ serverAddress.getHostAddress());

				//Ask the server to create a new socket
				try{
					//get number of connections and change [] and port number based on that
					clientSocket = new Socket(serverAddress.getHostAddress(),49999);

					System.out.println("We got the connexion to  "+ serverAddress);
					
					//send username to server
					ObjectOutputStream OoutUsername = new ObjectOutputStream(clientSocket.getOutputStream());

					OoutUsername.writeUTF(client.getUsername());
					
					OoutUsername.flush();
										
					//get list of available files from server
					ObjectInputStream OinListClients = 
							new ObjectInputStream(clientSocket.getInputStream());
					
					

					try {
						result = (String[][]) OinListClients.readObject();
						if (result == null)
						{
							result = new String[1][1];
							result[0][0] = "empty";
						}
							
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
					OinListClients.close();
					OoutUsername.close();
					
	
				}
					
				catch(IOException ex)
				{
					System.out.println("connection didn't work CLIENT CONNEXION.");
				}	
				
			}catch (UnknownHostException e) {

				e.printStackTrace();

			}catch (@SuppressWarnings("hiding") IOException e) {

				e.printStackTrace();
			}		
			
			return result;
	}
	
	public void writeDBConnect(client.Client client, int connectDigit) {
		
		String FILENAME = "../FileShareServer/src/db/db";
		fileManager.FileManager files = new fileManager.FileManager();
		int nbLines = files.getNumberLines();
				
		//write in db.txt that the client is connected
		try {			
			FileReader frDB = new FileReader(FILENAME);
			BufferedReader brDB = new BufferedReader(frDB);							
			String[] toWrite = new String[nbLines];
			
			//loop through file and find the line to replace
			for(int i = 0; i<nbLines; i++)
			{
				String[] parts = brDB.readLine().split(";");	
				if(parts[0].equals(client.getUsername()))
				{				
					if(connectDigit == 1)
						parts[4] = "1";
					else
						parts[4] = "0";			
				}	
				
				toWrite[i] = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4];
			}
			
			//fill file with lines to write
			FileWriter fw = new FileWriter(FILENAME);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i<nbLines;i++)
			{
				bw.write(toWrite[i]);
				bw.newLine();
			}
			
			//close all streams
			bw.flush();
			fw.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public boolean checkClient(client.Client client) {
		//src = https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
		BufferedReader brLine = null;
		FileReader frLine = null;
		String FILENAME = "../FileShareServer/src/db/db";
		
		Boolean result = false;

		try {
			frLine = new FileReader(FILENAME);
			brLine = new BufferedReader(frLine);
			
			//count lines
			int nbLines = 0;
			while (brLine.readLine() != null) nbLines++;
			
			FileReader frCheck = new FileReader(FILENAME);
			BufferedReader brCheck = new BufferedReader(frCheck);

			for(int i = 0;i<nbLines;i++)
			{
				String[] parts = brCheck.readLine().split(";");
				String username = parts[0];
				String pwd = parts[1]; 

				if(username.equals(client.getUsername()) && pwd.equals(client.getPwd()))
				{
					String newText = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + 1; 
					String oldText = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + 0; 
					writeDBConnect(client, 1);
					return true;
				}				
			}
			
			frCheck.close();
			brCheck.close();
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (brLine != null)
					brLine.close();

				if (frLine != null)
					frLine.close();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}		
		return result;
	}
	
}