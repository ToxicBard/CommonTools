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
	
	public static String toDigitsEnglishChars(String toProcess){
		String toReturn = "";
		
		//Go through each character in the String to build a return String
		for(char loopChar : toProcess.toCharArray()){
			//If the character is an English a-z character or a digit,
			//then add it.  Otherwise add an underscore in its place.
			if(Character.toString(loopChar).matches("[a-zA-Z-_0-9]")){
				toReturn += loopChar;
			}
			else{
				toReturn += "_";
			}
		}
		
		return toReturn;
	}
	
}
