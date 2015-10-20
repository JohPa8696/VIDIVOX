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
	private String videoDuration;
	private int minute;
	private int second;
	private MessageFrame mf=null;
	private boolean isValid1=true;
	private boolean isValid2=true;
	private boolean isValid3=true;
	private ArrayList<String> mp3Files= new ArrayList<String>();
	private ArrayList<String> startTimes= new ArrayList<String>();
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
	 * Constructor take inputs as a array list of start times and the video duration that is being merge
	 * @param startFiles
	 * @param videoDuration
	 */
	public InvalidFilesCheck(ArrayList<String> startTimes, String videoDuration){
		this.startTimes=startTimes;
		this.videoDuration=videoDuration;
		int minute=Integer.parseInt(videoDuration.substring(0,1));
		int second=Integer.parseInt(videoDuration.substring(3,4));
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
			isValid1=false;
			mf.setVisible(true);
		}
		
		return isValid1;	
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
				isValid2=false;
				mf.setVisible(true);
			} else if (!mp3.exists()) {
				if (mf != null) {
					mf.dispose();
				}	
				mf = new MessageFrame("Error", "ERROR 2b", mp3.getName() + " does not exist");
				isValid2=false;
				mf.setVisible(true);
			}
		}
		return isValid2;
	}
	/**
	 * Check if the starts are valid
	 * @return
	 */
	public boolean startTimesCheck(){
		for (String s: startTimes){
			String[] substrings=s.split(":");
			for(String substring: substrings){
				if(isInteger(substring)){
				}else{
					if (mf != null) {
						mf.dispose();
					}
					mf = new MessageFrame("Error", "ERROR 9", "Invalid start time");
					isValid3=false;
					mf.setVisible(true);
					return isValid3;
				}
			}
			int min=Integer.parseInt(s.substring(0,1));
			int sec=Integer.parseInt(s.substring(3,4));
			System.out.println(sec + "----" +min);
			if((min*60 +sec)>(minute*60+second)){
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 9", "Invalid start time");
				isValid3=false;
				mf.setVisible(true);
			}else if (sec>=60){
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 9", "Invalid start time");
				isValid3=false;
				mf.setVisible(true);		
			}
		}
		return isValid3;
	}
	
	/**
	 * Check if the string contains only numbers.
	 * @param string
	 * @return true if the string contain only numeric value
	 */
	public static boolean isInteger(String string){return string.matches("-?\\d+(\\.\\d+)?");}
}