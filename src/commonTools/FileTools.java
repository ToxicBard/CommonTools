package commonTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFileChooser;

public class FileTools {
	
	public static void writeObjectToFile(Serializable writeObject, String fileLocation){
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOut = null;
		File checkDirFile = new File(fileLocation);
		
		try {
			//If the directory doesn't exist, then create it
			if(!checkDirFile.getParentFile().exists()){
				checkDirFile.getParentFile().mkdirs();
			}
			
			//Create the necessary output streams
			fileOut = new FileOutputStream(fileLocation);
			objectOut = new ObjectOutputStream(fileOut);
			
			//Write the object
			objectOut.writeObject(writeObject);
			
			//Close and save
			objectOut.close();
			fileOut.close();
		} catch (IOException e) {
			CommonTools.processError("Error Writing to FileOutputStream");
		}
		
	}
	
	public static Object readObjectFromFile(String fileLocation){
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		Object toReturn = null;
		
		try {
			
			//Create the necessary input streams
			fileIn = new FileInputStream(fileLocation);
			objectIn = new ObjectInputStream(fileIn);
			
			//Read the object from file
			toReturn = objectIn.readObject();
			
			//Close the streams
			objectIn.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			CommonTools.processError("Error reading from FileInputStream");
		}
		
		return toReturn;
		
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

	public static File selectSavedDirectory(String dialogTitle, String saveFilePath){
		File loadedFolder = null;
		File selectedFolder = null;
		String loadedFolderPath = "";
		String selectedFolderPath = "";
		
		//Load directory from disk if it exists
		loadedFolder = loadSavedDirectory(saveFilePath);
		
		/*
		 * Allow the user to select a directory with a filechooser
		 * that starts out at the loaded location
		 */
		selectedFolder = getDirectoryFromUser(dialogTitle, loadedFolder);
		
		/*
		 * Store a value for the paths of the loaded file and the
		 * selected file so that they can be compared without worrying
		 * about null values
		 */
		if(loadedFolder != null){
			loadedFolderPath = loadedFolder.getAbsolutePath();
		}
		if(selectedFolder != null){
			selectedFolderPath = selectedFolder.getAbsolutePath();
		}
		
		/*
		 * If the user's selection is different from what was 
		 * loaded from disk, then save the new value to disk.
		 */
		if(!loadedFolderPath.equals(selectedFolderPath)){
			saveDirectoryToDisk(saveFilePath, selectedFolder);
		}
		
		return selectedFolder;
		
	}
	
	private static File loadSavedDirectory(String saveFilePath){
		BufferedReader br = loadReadFile(saveFilePath);
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
		File selectedFile = null;
		
		//Allow the user to select the directory.
		//Use the existing directory Files as the starting directory because
		//by this point they've been loaded from the save file if relevant.
		selectedFile = askUserForDirectory(dialogTitle, loadedFile);
		if(selectedFile == null){
			//If the user didn't pick anything, then we exit the program because we have nothing to go on.
			CommonTools.processError("You must select the former directory.");
		}
		
		return selectedFile;
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
	
	private static void saveDirectoryToDisk(String saveFilePath, File selectedFolder){
		BufferedWriter bw = openWriteFile(saveFilePath);
		
		//Write to and close the file.
		try {
			bw.write(selectedFolder.getAbsolutePath());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			CommonTools.processError("Error writing directory");
		}
		
	}
}
