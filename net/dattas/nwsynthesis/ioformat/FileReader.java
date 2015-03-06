package net.dattas.nwsynthesis.ioformat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
	
	public static void main(String[] args) {
		{
			FileReader fileReader = new FileReader();
			System.out.println(fileReader.readFileLine());
		}
	}
	@SuppressWarnings("deprecation")
	public String readFileLine()
	{
		String line = "";
		
		File file = new File("C:\\pwd.txt");
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;

	    try {
	      fis = new FileInputStream(file);

	      // Here BufferedInputStream is added for fast reading.
	      bis = new BufferedInputStream(fis);
	      dis = new DataInputStream(bis);

	      // dis.available() returns 0 if the file does not have more lines.
	      while (dis.available() != 0) {

	      // this statement reads the line from the file and print it to
	        // the console.
	       // System.out.println(dis.readLine());
	    	line = dis.readLine();  
	      }

	      // dispose all the resources after using them.
	      fis.close();
	      bis.close();
	      dis.close();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
		return line;
	}

}
