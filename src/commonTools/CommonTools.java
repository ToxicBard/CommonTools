package commonTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

/*
 * TODO Abstract/Generalize the load/select/save
 * directory functionality from DirectoryCompare and
 * add it here
 */

public class CommonTools {

	public static void processError(String errorText){
		System.out.println(errorText);
		System.exit(0);
	}
	
	public static BufferedReader loadReadFile(String filePath){
		File myFile = new File(filePath);
		FileReader myFileReader = null;
		BufferedReader br = null;
		
		//Only try to load if the save file actually exists
		if(myFile.exists()){
			//Try to open the file.  If the file exists but can't
			//be opened, then tell the user and exit.
			try {
				myFileReader = new FileReader(myFile);
			} catch (FileNotFoundException e) {
				CommonTools.processError("Error loading file from disk.");
			}
			
			//Create a BufferedReader from the already-opened file
			br = new BufferedReader(myFileReader);
			
			return br;
		}
		
		//Return a null value if the file doesn't exist.
		return null;
	}
	
	public static BufferedWriter openWriteFile(String filePath){
		File myFile = new File(filePath);
		return openWriteFile(myFile);
	}
	
	public static BufferedWriter openWriteFile(File writeFile){
		FileWriter myFileWriter = null;
		BufferedWriter bw;

		try {
			//If the parent directory doesn't exist, then create it
			if(!writeFile.getParentFile().exists()){
				writeFile.getParentFile().mkdirs();
			}
			
			//If the file doesn't exist, then create it.
			if(!writeFile.exists()){
				writeFile.createNewFile();
			}
		} catch (IOException e) {
			CommonTools.processError("Error creating save file.");
		}
		
		//Open the file for writing whether it already existed or not.
		try {
			myFileWriter = new FileWriter(writeFile);
		} catch (IOException e1) {
			CommonTools.processError("Error Opening File Writer.");
		}
		
		bw = new BufferedWriter(myFileWriter);
		
		return bw;
	}
	
	public static boolean isDebugMode(){
		return java.lang.management.ManagementFactory.getRuntimeMXBean().
			    getInputArguments().toString().indexOf("jdwp") >= 0;
	}
	
	public static File selectSavedDirectory(String saveFilePath){
		File loadedFile = loadSavedDirectory(saveFilePath);
	}
	
	public static File loadSavedDirectory(String saveFilePath){
		BufferedReader br = CommonTools.loadReadFile(saveFilePath);
		File myDirectory = null;
		String inputDirectory = null;
		
		//Only try to read the file if it actually exists
		if(br != null){

			try {
				inputDirectory = br.readLine();
			} catch (IOException e) {
				CommonTools.processError("Error reading File.");
			}
			
			myDirectory = new File(inputDirectory);
			
			/*
			 * If the filename provided by the file doesn't exist or isn't a directory
			 * then clear out the directory variable.
			 * In this case we want to fail silently and continue execution.
			 */
			if(myDirectory.exists() == false || myDirectory.isDirectory() == false){
				myDirectory = null;
			}
			
			try {
				br.close();
			} catch (IOException e) {
				CommonTools.processError("Error closing file reader");
			}
		}
		
		return myDirectory;
	}
	
	private static File getDirectoryFromUser(String dialogTitle, File loadedFile){
		
	}
	
	//Asks the user for a directory based on a starting point, which has presumably been loaded from disk.
	private static File askUserForDirectory(String dialogTitle, File startingDirectory){
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(startingDirectory);
		fileChooser.setDialogTitle(dialogTitle);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION){
			return fileChooser.getSelectedFile();
		}
		
		return null;
	}
}
