package connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//BASED ON CODE FROM https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
// ALL RIGHTS RESERVED

public class ConnectThread extends Thread{
	
	protected Socket socket;

	public ConnectThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
	
	public void run() {
		{
		System.out.println("RUN");
		//get client username
		try {
			ObjectInputStream OinUsername = new ObjectInputStream(socket.getInputStream());
			
			String username = OinUsername.readUTF();
			
			//send client list of available files with their respectives owners / IP numbers
			fileManager.FileManager files = new fileManager.FileManager();
			
			ObjectOutputStream OoutListClients = new ObjectOutputStream(socket.getOutputStream());
			
			String[][] availableFiles = files.GetAvailableFiles(username);
			
			OoutListClients.writeObject(availableFiles);
			
			OoutListClients.flush();
			
			OoutListClients.close();
		
			OinUsername.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	}
	
}
