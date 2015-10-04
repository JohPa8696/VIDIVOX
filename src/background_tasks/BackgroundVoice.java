package background_tasks;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import mainview.MediaPlayer;

/**
 * Creates a voice that runs in the background which can be canceled
 * 
 * @author Vincent Nio
 * 
 */
public class BackgroundVoice extends SwingWorker<Object, Integer> {
	// Instance variables
	private String message; // message to be said
	private int pid; // task id
	private MediaPlayer mediaPlayer=null;

	/**
	 * default constructor
	 */
	public BackgroundVoice() {
	}

	/**
	 * Contructor
	 * 
	 * @param message message that the user provides
	 */
	public BackgroundVoice(String message, MediaPlayer mediaPlayer) {
		this.message = message;
		this.mediaPlayer=mediaPlayer;
	}

	/**
	 * runs the voice in the background
	 */
	@Override
	protected Object doInBackground() throws Exception {
		// command string used in bash terminal
		String cmd = message + " | festival --tts";

		// builds the command and runs it
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();

		// creates a reader to read the output
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(
				new InputStreamReader(stdout));

		// gets task id of the process
		Field f = process.getClass().getDeclaredField("pid");
		f.setAccessible(true);
		// pid is private in UNIXProcess
		int pid = f.getInt(process);
		this.pid = pid;

		// is needed so the function doesn't end early
		while (stdoutBuffered.readLine() != null) {
		}
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
		}
		this.mediaPlayer.getSpeakButton().setText("");
		this.mediaPlayer.getSpeakButton().setIcon(mediaPlayer.getSpeakIcon());
	}

}