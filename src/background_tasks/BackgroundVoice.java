package background_tasks;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import mainview.MediaPlayer;

/**
 * Creates a festival voice with voice, rate and pitch features specified by user. The voice can also be canceled
 * if use click the cancel button.
 */
public class BackgroundVoice extends SwingWorker<Object, Integer> {
	// Instance variables
	private ArrayList<String> filesToRemove= new ArrayList<String>();
	private String message; // message to be said
	private int pid; // task id
	private MediaPlayer mediaPlayer=null;
	private double rate;
	private int pitchStart;
	private int pitchEnd;
	private String voice;

	/**
	 * default constructor
	 */
	public BackgroundVoice() {
	}

	/**
	 * Custom constructor take inputs as attributes of the festival voice : the message, voice, rate, pitch at the start and end. 
	 * @param message
	 * @param mediaPlayer
	 * @param voice
	 * @param rate
	 * @param start
	 * @param end
	 */
	public BackgroundVoice(String message, MediaPlayer mediaPlayer, String voice, double rate, int start,int end) {
		this.message = message;
		this.mediaPlayer=mediaPlayer;
		this.rate= rate;
		this.voice=voice;
		this.pitchStart= start;
		this.pitchEnd= end;
	}

	/**
	 * runs the voice in the background
	 */
	@Override
	protected Object doInBackground() throws Exception {
		// Check if the scheme file exists if not create one;
		String cmd = "if [ ! -f \\\".tmp.scm\\\" ] ; then $(touch .tmp.scm) ; fi";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		process.waitFor();
		process.destroy();
		
		//Fill the scheme file with festival commands 
		String cmd1 = "echo -e \"(voice_"+this.voice+")\n"
				+ "(set! duffint_params '((start "+pitchStart+") (end "+pitchEnd+")))\n"
				+ "(Parameter.set 'Int_Method 'DuffInt)\n(Parameter.set 'Int_Target_Method Int_Targets_Default)\n"
				+ "(Parameter.set 'Duration_Stretch "+rate+")\n"
				+ "(SayText \\\""+message+"\\\")\">.tmp.scm ";
		
		ProcessBuilder builder1= new ProcessBuilder("/bin/bash","-c", cmd1);
		Process process1= builder1.start();
		process1.waitFor();
		process1.destroy();
		//Add the .tmp.scm file to the remove list
		filesToRemove.add(".tmp.scm");
		//Execute the scm file
		String cmd2= "festival -b '.tmp.scm'";
		ProcessBuilder builder2= new ProcessBuilder("/bin/bash","-c", cmd2);
		Process process2= builder2.start();
		
		// gets task id of the process
		Field f = process2.getClass().getDeclaredField("pid");
		f.setAccessible(true);
		// pid is private in UNIXProcess
		this.pid= f.getInt(process2);	
		process2.waitFor();
		process2.destroy();
		
	
		return null;
	}

	@Override
	protected void done() {
		try {
			this.get();
		} catch (CancellationException e1) { // if canceled will terminate the
												// voice
			try {
				// builds process to get the aplay pid
				ProcessBuilder builder2 = new ProcessBuilder("/bin/bash", "-c",
						"pstree -lp | grep " + pid);
				Process process2 = builder2.start();
				InputStream stdout2 = process2.getInputStream();

				// reads the output
				BufferedReader stdoutBuffered2 = new BufferedReader(
						new InputStreamReader(stdout2));
				process2.waitFor();
				Thread.sleep(200);

				// gets the aplay pid from the output string
				String line;
				line = stdoutBuffered2.readLine();
				line = line.substring(line.indexOf("{"));
				line = line.replaceAll("\\D+", "");
				int pidOfAplay = Integer.parseInt(line);

				// builds process to terminate the voice
				ProcessBuilder builder3 = new ProcessBuilder("/bin/bash", "-c",
						"kill " + pidOfAplay);
				builder3.start();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
		} catch (ExecutionException e1) {
		}

		//Destroy the temporary scheme file
		FilesRemover fr= new FilesRemover(filesToRemove);
		fr.execute();
		
		this.mediaPlayer.getSpeakButton().setText("");
		this.mediaPlayer.getSpeakButton().setIcon(mediaPlayer.getSpeakIcon());
	}

}