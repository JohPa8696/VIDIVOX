package background_tasks;

import java.util.ArrayList;

import javax.swing.SwingWorker;

public class FilesRemover extends SwingWorker<Object, Integer>{
	/**
	 * FilesRemover remove all intermidiate and one-time use files 
	 */
	
	ArrayList<String> filesToRemove= new ArrayList<String>();
	
	/**
	 * Constructor
	 * @param files
	 */
	public FilesRemover(ArrayList<String> files){
		this.filesToRemove=files;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		for(int i=0; i<filesToRemove.size(); i++){
			String cmdRemove= "rm -f "+filesToRemove.get(i);
			ProcessBuilder buildRemover= new ProcessBuilder("/bin/bash", "-c", cmdRemove);
			Process processRemove= buildRemover.start();
			processRemove.waitFor();
			processRemove.destroy();
		}
		return null;
	}
	

}
