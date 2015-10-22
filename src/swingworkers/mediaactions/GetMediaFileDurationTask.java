package swingworkers.mediaactions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 * GetMediaFileDurationTask get the duration of a video file (avi, mp4  or mkv) or and mp3 file using a bash command
 * and return the duration back to the GUI
 */
public class GetMediaFileDurationTask extends SwingWorker<Integer,Object> {
	
	private String videoFile;
	private String duration;
	private JLabel durationLabel=null;
	String line=null;
	
	/**
	 * Custom constructor
	 * @param videoFile
	 * @param label
	 */
	public GetMediaFileDurationTask(String videoFile, JLabel label){
		this.videoFile= videoFile;
		this.durationLabel=label;
	}
	
	@Override
	protected Integer doInBackground(){
		
		//BUild process extract duration from a media file using ffmpeg in bash
		try{
		String cmd= "$(ffmpeg -i "+videoFile+" 2>&1 | grep Duration | awk '{print $2}' | tr -d ,)";
		
		ProcessBuilder builder= new ProcessBuilder("/bin/bash","-c", cmd);
		Process process= builder.start();
		//Get the output from the error stream
		InputStream out = process.getErrorStream();
		BufferedReader stdout = new BufferedReader(new InputStreamReader(out));
		line=stdout.readLine();
		duration= line.substring(14, 19);
		process.waitFor();
		process.destroy();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void done(){
		durationLabel.setText(duration);
	}

}
