package swingworkers.savesyntheticspeech;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import swingworkers.filesrehandlers.FilesRemover;


/**
 * Save the users' input text as synthetic speech with voice attributes such as voice, rate and pitch as specified.
 * The user can also select a directory to save the file to.
 */
public class SaveSpeech extends SwingWorker<Object, Integer>{

	private ArrayList<String> filesToRemove= new ArrayList<String>();
	private String message;
	private String fileName;
	private JProgressBar progressBar=null;
	private JLabel statuslbl=null;
	private String voice;
	private double rate;
	private int pitchStart;
	private int pitchEnd;
	private int n=0;
		
	/**
	 * Constructor
	 * @param message
	 * @param fileName
	 * @param statuslbl
	 * @param progressBar
	 * @param voice
	 * @param rate
	 * @param pitchStart
	 * @param pitchEnd
	 */
	public SaveSpeech (String message, String fileName,JLabel statuslbl, JProgressBar progressBar, String voice, double rate, int pitchStart, int pitchEnd){
		this.message = message;
		this.fileName = fileName;
		this.statuslbl=statuslbl;
		this.progressBar= progressBar;
		this.voice=voice;
		this.rate=rate;
		this.pitchStart=pitchStart;
		this.pitchEnd=pitchEnd;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// command used in bash terminal
		// create a temporary scheme file
		String cmdSchemeFile = "echo -e \"(voice_"+this.voice+")\n"+
				"(set! duffint_params '((start "+pitchStart+") (end "+pitchEnd+")))\n"
				+ "(Parameter.set 'Int_Method 'DuffInt)\n(Parameter.set 'Int_Target_Method Int_Targets_Default)\n"
				+ "(Parameter.set 'Duration_Stretch "+rate+")\">.tmp1.scm ";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmdSchemeFile);
		Process process = builder.start();
		//add .tmp1.scm to remove list
		filesToRemove.add(".tmp1.scm");
		process.waitFor();
		process.destroy();
		
		//Create textfile with the message
		String cmdTxtFile= "echo \""+message+"\" > .tmp.txt";
		ProcessBuilder buildTxt= new ProcessBuilder("/bin/bash","-c", cmdTxtFile);
		Process processTxt= buildTxt.start();
		filesToRemove.add(".tmp.txt");
		processTxt.waitFor();
		processTxt.destroy();
		
		//Convert the text to a wav file with properties in the scheme file
		String cmdWavFile= "text2wave -o .tmp.wav .tmp.txt -eval .tmp1.scm";
		ProcessBuilder buildWav= new ProcessBuilder("/bin/bash","-c",cmdWavFile);
		Process processWav= buildWav.start();
		filesToRemove.add(".tmp.wav");
		processWav.waitFor();
		processWav.destroy();
		
		//Convert the wave file into mp3 file
		String cmdMp3File = "ffmpeg -i .tmp.wav -f mp3 " + fileName;
		ProcessBuilder buildMp3 = new ProcessBuilder("/bin/bash", "-c", cmdMp3File);
		Process processMp3 = buildMp3.start();
		InputStream out= processMp3.getErrorStream();
		BufferedReader stdout= new BufferedReader(new InputStreamReader(out));
		while(stdout.readLine() !=null){
			n+=10;
			publish();
		}
		
		processMp3.waitFor();
		processMp3.destroy();

		// command used in bash terminal
		// deletes the temporary file
		FilesRemover fr= new FilesRemover(filesToRemove);
		fr.execute();
	
		return null;
	}
	
	@Override
	protected void process(List<Integer> chunks){
		String[] s= fileName.split("/");
		this.statuslbl.setText("Creating "+s[s.length-1]+", please wait...");
		progressBar.setValue(n);
	}
	@Override
	protected void done(){
		String[] s= fileName.split("/");
		this.statuslbl.setText("Sucessfully created "+s[s.length-1]+"!");
		progressBar.setValue(500);
		
	}
}
