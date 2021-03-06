package swingworkers.mediaactions;

import java.lang.reflect.Field;

import javax.swing.SwingWorker;



/**
 * Play selected mp3 files in the background
 */
public class PlayMp3Background extends SwingWorker<Object,Integer>{

	private String fileName;
	private int pid; 
	/**
	 * Constructor
	 * @param fileName
	 */
	public PlayMp3Background(String fileName){
		this.fileName=fileName;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		//Play the input file using ffplay;
		String cmdPlay= "mpg123 " +fileName;
		ProcessBuilder buildPlay= new ProcessBuilder("/bin/bash","-c",cmdPlay);
		Process processPlay= buildPlay.start();
		
		
		//get the pid of the bash session
		Field f = processPlay.getClass().getDeclaredField("pid");
		f.setAccessible(true);
		// pid is private in UNIXProcess
		this.pid= f.getInt(processPlay);
		//System.out.println(pid);
		processPlay.waitFor();
		processPlay.destroy();
		return null;
	}
	

}
