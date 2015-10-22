package swingworkers.filesrehandlers;


import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Global ProjectFolder create the project folder in the home directory.
 */
public class GlobalProjectFolder extends SwingWorker<Object,Integer>{
	
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
