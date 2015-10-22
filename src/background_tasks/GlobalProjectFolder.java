package background_tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mainview.MediaPlayer;

public class GlobalProjectFolder extends SwingWorker<Object,Integer>{
	/**
	 * Global ProjectFolder create the project folder in the home directory.
	 */
	private String directoryToCreate;
	
	public GlobalProjectFolder(String path){
		this.directoryToCreate=path;
	}

	@Override
	protected Object doInBackground() throws Exception {
		//Create the folder Throwable_dpha010
		String cmdFolder= "mkdir "+directoryToCreate;
		ProcessBuilder builder= new ProcessBuilder("/bin/bash","-c", cmdFolder);
		Process process=builder.start();
		process.waitFor();
		process.destroy();
		return null;
	}
	@Override
	protected void done(){
		JOptionPane.showMessageDialog(null, "The folder "+directoryToCreate+" has been created!");
		
	}

}
