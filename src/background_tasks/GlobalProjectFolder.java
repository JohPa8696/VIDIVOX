package background_tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class GlobalProjectFolder extends SwingWorker<Object,Integer>{
	/**
	 * Global ProjectFolder create the project folder in the home directory.
	 */

	@Override
	protected Object doInBackground() throws Exception {
		//Create the folder Throwable_dpha010
		String cmdFolder= "mkdir ~/Throwable_dpha010";
		ProcessBuilder builder= new ProcessBuilder("/bin/bash","-c", cmdFolder);
		Process process=builder.start();
		process.waitFor();
		process.destroy();
		return null;
	}
	@Override
	protected void done(){
		JOptionPane.showMessageDialog(null, "The folder \"Throwable_dpha010\" has been created in your home directory");
	}

}
