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
	 * TODO Write a timer class
	 */

	public static void processError(String errorText){
		System.out.println(errorText);
		System.exit(0);
	}
	
	public static boolean isDebugMode(){
		return java.lang.management.ManagementFactory.getRuntimeMXBean().
			    getInputArguments().toString().indexOf("jdwp") >= 0;
	}
	
}
