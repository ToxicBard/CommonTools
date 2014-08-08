package commonTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;


public class CommonTools {
	
	/*
	 * TODO Move all *.jar libraries to the CommonTools project
	 * 		and update references accordingly 
	 */

	public static void processError(String errorText){
		System.out.println(errorText);
		System.exit(0);
	}
	
	public static boolean isDebugMode(){
		return java.lang.management.ManagementFactory.getRuntimeMXBean().
			    getInputArguments().toString().indexOf("jdwp") >= 0;
	}
	
	public static int intParse(String parseString){
		int toReturn = 0;
	     try  
	     {  
	         toReturn = Integer.parseInt(parseString);  
	      } catch(NumberFormatException nfe)  
	      {  
	          toReturn = 0;
	      }  
	     return toReturn;
	}
	
}
