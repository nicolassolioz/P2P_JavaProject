package fileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import connexion.ConnectThread;

public class FileManager {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectThread.class.getName());
	
	public FileManager() {
		// LOGGING PARAMETERS
		logging.CustomFileHandler customFh = new logging.CustomFileHandler();
		FileHandler fh = customFh.setFileHandler();
		LOGGER.addHandler(fh);
	}
public String[][] GetAvailableFiles(String username) {
		
		/*username = getAvailableFiles for THIS username (excludes himself from loop)
		get all available files of connected clients where username != username AND isConnected
		use Buffered Reader, split the line, compare username and last binary digit
		then, get the url of shared folder and print out the name of every file*/

		String FILENAME = "../FileShareServer/src/db/db";
		String[][] availableFiles = null;
		String[][] result = null;
		
		ArrayList<String[][]> lstFiles = new ArrayList<>();

		try {			
			//count lines
			int nbLines = getNumberLines();
			
			FileReader frCheck = new FileReader(FILENAME);
			BufferedReader brCheck = new BufferedReader(frCheck);

			for(int i = 0;i<nbLines;i++)
			{
				String[] parts = brCheck.readLine().split(";");		
				if(!parts[0].equals(username))
				{
					if(parts[5].equals("1"))
					{
						availableFiles = RetrieveFiles(parts[4], parts[3],parts[2], 0);
						lstFiles.add(availableFiles);
					}
				}
			}		
			
			// LOOP THROUGH ARRAYLIST AND GET LENGTH OF ARRAY
			int totalLines = 0;
			for(int i = 0; i<lstFiles.size(); i++)
			{
				result = lstFiles.get(i);
				totalLines += result.length;
			}
			
			System.out.println("TOTAL NUMBER OF AVAILABLE FILES : " + totalLines);
			
			// LOOP THROUGH ARRAYLIST AND FILL STRING[][] RESULT
			String[][] temp = null;
			result = new String[totalLines][5];

			int z = 0;
			
			for(int i = 0; i< totalLines; i++)
			{	
				temp = lstFiles.get(z);
				System.out.println("LENGTH : " + temp.length);
				for(int y = 0; y<temp.length; y++)
				{
					System.out.println(y);
					result[i+y] = temp[y];
				}
				i +=  temp.length-1;
				z++;
				
				System.out.println(i);
			}
			
			
			
			
			frCheck.close();
			brCheck.close();
		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "error while fetching available files", e);
			e.printStackTrace();

		}	
		
		return result;
	}

	public String[][] RetrieveFiles(String socket, String IP, String sharedFolder, int startIndex) {
		//print content of files
		//loop through folder given in parameter and assign to new String
		//source : https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
		File folder = new File(sharedFolder);
		File[] listOfFiles = folder.listFiles();
		String[][] files = new String[listOfFiles.length][5];
	
		    for (int i = startIndex; i < listOfFiles.length + startIndex; i++) {
		    	files[i][0] = listOfFiles[i].getName();
		    	files[i][1] = IP;
		    	files[i][2] = socket;
		    	files[i][4] = sharedFolder;
		    	
		    	if (listOfFiles[i].isFile()) 
		    		files[i][3] = "f";		      
		    	else if (listOfFiles[i].isDirectory()) 
		    		files[i][3] = "d";
	
		    	}
		//********************************************************************
		    
		return files;
		
	}

	public int getNumberLines() {
		int result = 0;
		
		BufferedReader brLine = null;
		FileReader frLine = null;
		String FILENAME = "../FileShareServer/src/db/db";
		
		try {
			frLine = new FileReader(FILENAME);
			brLine = new BufferedReader(frLine);
			while (brLine.readLine() != null) result++;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "error while fetching files", e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "error while fetching files", e);
			e.printStackTrace();
		}
	
		return result;
	}

}
