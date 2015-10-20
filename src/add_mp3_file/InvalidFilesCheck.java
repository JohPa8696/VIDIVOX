package add_mp3_file;

import java.io.File;
import java.util.ArrayList;

import generic_frames.MessageFrame;

public class InvalidFilesCheck {
	/**
	 * InvalidFileCheck check if the files that the user entered is invalid and Open an error massage to let user know
	 * Otherwise it returns a true boolean value.
	 */
	private String videoFile;
	private String newFile;
	private String directory;
	private MessageFrame mf=null;
	private boolean isvalid1=true;
	private boolean isvalid2=true;
	private ArrayList<String> mp3Files= new ArrayList<String>();
	/**
	 * Constructor with inputs are the file names entered by user
	 * @param videoFile
	 * @param audioFile
	 * @param newFile
	 * @param directory
	 */
	public InvalidFilesCheck(String videoFile, String newFile, String directory){
		this.videoFile=videoFile;
		this.newFile=newFile;
		this.directory=directory;
		
	}
	/**
	 * Contructor with input is array list of the selected mp3 files
	 * @param mp3Files
	 */
	public InvalidFilesCheck(ArrayList<String> mp3Files){
		this.mp3Files=mp3Files;
	}
	/**
	 * Check user inputs for name of new file, video file and the directory are valid.
	 * @return
	 */
	public boolean ErrorChecking(){
		File video = new File(videoFile);
		File newFile= new File(this.newFile);
		File checkFileName=new File(this.newFile.substring(0, this.newFile.length()-4));
		File directory = new File(this.directory);
		String pattern = "^[a-zA-Z0-9_]*$";
		
		//checks that all fields are correct
		if (!video.exists() || video.isDirectory()
				|| newFile.exists() || !directory.exists()
				|| !checkFileName.getName().matches(pattern)) {

			if (video.getName().equals("")|| newFile.getName().equals("")|| directory.getName().equals("")) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 1",
						"Please fill in blank fields");
			} else if (!video.exists()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 2a", video.getName() + " does not exist");
			} else if (video.isDirectory()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 3a", video.getName() + " is a directory");
			} else if (!directory.exists()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 6", directory
						.toString() + " does not exist");
			} else if (newFile.exists()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 5a", newFile.getName() + " already exists");
			} else if (!checkFileName.getName().matches(pattern)) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 5b", newFile.getName() + " is an invalid name");
			}
			isvalid1=false;
			mf.setVisible(true);
		}
		
		return isvalid1;	
	}
	/**
	 * Check if the input mp3 files are valid or not
	 */
	public boolean mp3FilesCheck(){
		for(String s: mp3Files){
			File mp3= new File(s);
			if (mp3.isDirectory()) {
				if (mf != null) {
				mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 3b", mp3.getName() + " is a directory");
				isvalid2=false;
				mf.setVisible(true);
			} else if (!mp3.exists()) {
				if (mf != null) {
					mf.dispose();
				}	
				mf = new MessageFrame("Error", "ERROR 2b", mp3.getName() + " does not exist");
				isvalid2=false;
				mf.setVisible(true);
			}
		}
		return isvalid2;
	}
}
