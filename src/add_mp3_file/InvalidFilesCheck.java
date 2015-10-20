package add_mp3_file;

import java.io.File;

import generic_frames.MessageFrame;

public class InvalidFilesCheck {
	/**
	 * InvalidFileCheck check if the files that the user entered is invalid and Open an error massage to let user know
	 * Otherwise it returns a true boolean value.
	 */
	private String videoFile;
	private String audioFile;
	private String newFile;
	private String directory;
	private MessageFrame mf=null;
	private boolean invalid=false;
	/**
	 * Constructor with inputs are the file names entered by user
	 * @param videoFile
	 * @param audioFile
	 * @param newFile
	 * @param directory
	 */
	public InvalidFilesCheck(String videoFile,String audioFile, String newFile, String directory){
		this.videoFile=videoFile;
		this.audioFile=audioFile;
		this.newFile=newFile;
		this.directory=directory;
		
	}
	
	public boolean ErrorChecking(){
		File video = new File(videoFile);
		File mp3 = new File(audioFile);
		File newFile= new File(this.newFile);
		File checkFileName=new File(this.newFile.substring(0, this.newFile.length()-4));
		File directory = new File(this.directory);
		String pattern = "^[a-zA-Z0-9_]*$";
		
		//checks that all fields are correct
		if (!video.exists() || video.isDirectory()
				|| !mp3.exists() || mp3.isDirectory()
				|| newFile.exists() || !directory.exists()
				|| !checkFileName.getName().matches(pattern)) {

			if (video.getName().equals("")|| mp3.getName().equals("")|| newFile.getName().equals("")|| directory.getName().equals("")) {
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
			} else if (mp3.isDirectory()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 3b", mp3.getName() + " is a directory");
			} else if (!mp3.exists()) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 2b", mp3.getName() + " does not exist");
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
			invalid=true;
			mf.setVisible(true);
		}
		
		return invalid;
			
	}
}
