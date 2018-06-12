package connexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

// BASED ON CODE FROM https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
// ALL RIGHTS RESERVED

// SERVER

public class ConnectThread extends Thread{
	
	private static final Logger LOGGER = Logger.getLogger("MyLog");
	
	protected Socket socket;
	private volatile boolean value;
	public ConnectThread(Socket clientSocket) {
		
		// LOGGING PARAMETERS
		logging.CustomFileHandler customFh = new logging.CustomFileHandler();
		FileHandler fh = customFh.setFileHandler();
		LOGGER.addHandler(fh);
					
        this.socket = clientSocket;
    }
	
	public boolean getResult() {
		return value;
	}
	public void run() {
			
		System.out.println("RUN");
		try {
			//get client username + password + client port and check if they exist
			ObjectInputStream OinClient = new ObjectInputStream(socket.getInputStream());
				
			String clientInfo = OinClient.readUTF();
			String parts[] = clientInfo.split(";");
				
			String username = parts[0];
			String pwd = parts[1];
			int port = Integer.parseInt(parts[2]);
				
			boolean result = false;
			result = checkClient(username, pwd, port);
				
			ObjectOutputStream OoutListClients = new ObjectOutputStream(socket.getOutputStream());
				
			if(result == true)
			{
				//send client list of available files with their respectives owners / IP numbers
				fileManager.FileManager files = new fileManager.FileManager();			
				String[][] availableFiles = files.GetAvailableFiles(username);
				
				OoutListClients.writeObject(availableFiles);
				
				OoutListClients.flush();
				
				LOGGER.log(Level.INFO, "client connected");
			}
			else
			{		
				String[][] availableFiles = new String[1][1];
				availableFiles[0][0] = "failed connection";
				LOGGER.log(Level.WARNING, "failed to connect");
				OoutListClients.writeObject(availableFiles);
			}
			
			OoutListClients.flush();
			OoutListClients.close();
			OinClient.close();
							
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "error while fetching info from client", e);
			e.printStackTrace();
		}	
	}

	public boolean checkClient(String username, String pwd, int port) {
		// SOME CODE BASED ON = https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
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
				String usernameToTest = parts[0];
				String pwdToTest = parts[1]; 

				if(usernameToTest.equals(username) && pwdToTest.equals(pwd))
				{
					String newText = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + port + ";" + 1; 
					String oldText = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4] + ";" + 0;
					
					WriteDBConnect writeCo = new WriteDBConnect();
					writeCo.write(username, 1, port);
					return true;
				}				
			}
			
			frCheck.close();
			brCheck.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "error while connecting with client", e);
			e.printStackTrace();

		} finally {

			try {

				if (brLine != null)
					brLine.close();

				if (frLine != null)
					frLine.close();

			} catch (IOException ex) {

				LOGGER.log(Level.SEVERE, "error while connecting with client", ex);
				ex.printStackTrace();
			}
		}		
		return result;
	}
	
}
