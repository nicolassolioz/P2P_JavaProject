package logging;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class CustomFileHandler {

	// used to log into a file based on month and year
	public FileHandler setFileHandler()
	{
		FileHandler fh = null;   
		// CODE BASED ON https://stackoverflow.com/questions/7182996/java-get-month-integer-from-date
		// ALL RIGHTS RESERVED
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		String fileName = ".\\logs\\" + localDate.getMonthValue() + "-" + localDate.getYear();
		
		try {
			fh = new FileHandler(fileName, true);
			fh.setFormatter(new SimpleFormatter());
		} 
		
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return fh;
	}
}
