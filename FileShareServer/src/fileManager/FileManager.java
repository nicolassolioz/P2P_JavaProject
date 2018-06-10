package fileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
public String[][] GetAvailableFiles(String username) {
		
		/*username = getAvailableFiles for THIS username (excludes himself from loop)
		get all available files of connected clients where username != username AND isConnected
		use Buffered Reader, split the line, compare username and last binary digit
		then, get the url of shared folder and print out the name of every file*/

		String FILENAME = "../FileShareServer/src/db/db";
		String[][] availableFiles = null;
		int startIndex = 0;

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
					if(parts[4].equals("1"))
					{
						availableFiles = RetrieveFiles(parts[3],parts[2], startIndex);
						startIndex = availableFiles.length;
					}
				}
			}			
			frCheck.close();
			brCheck.close();
		} catch (IOException e) {

			e.printStackTrace();

		}	
		
		return availableFiles;
	}

	public String[][] RetrieveFiles(String IP, String sharedFolder, int startIndex) {
		//print content of files
		//loop through folder given in parameter and assign to new String
		//source : https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
		File folder = new File(sharedFolder);
		File[] listOfFiles = folder.listFiles();
		String[][] files = new String[listOfFiles.length][3];
	
		    for (int i = startIndex; i < listOfFiles.length + startIndex; i++) {
		    	files[i][0] = listOfFiles[i].getName();
		    	files[i][1] = IP;
		    	
		    	if (listOfFiles[i].isFile()) 
		    		files[i][2] = "f";		      
		    	else if (listOfFiles[i].isDirectory()) 
		    		files[i][2] = "d";
	
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}
}
