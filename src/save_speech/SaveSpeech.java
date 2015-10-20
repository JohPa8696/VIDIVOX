package save_speech;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

public class SaveSpeech extends SwingWorker<Void, Void>{
	/**
	 * Save the users' input text as synthetic speech with voice, rate and pitch as specified
	 */
	private String message;
	private String fileName;
	private String voice;
	private double rate;
	private int pitchStart;
	private int pitchEnd;
	
		
	public SaveSpeech (String message, String fileName, String voice, double rate, int pitchStart, int pitchEnd){
		this.message = message;
		this.fileName = fileName;
		this.voice=voice;
		this.rate=rate;
		this.pitchStart=pitchStart;
		this.pitchEnd=pitchEnd;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// command used in bash terminal
		// create a temporary scheme file
		String cmdSchemeFile = "echo -e \"(set! duffint_params '((start "+pitchStart+") (end "+pitchEnd+")))\n"
				+ "(Parameter.set 'Int_Method 'DuffInt)\n(Parameter.set 'Int_Target_Method Int_Targets_Default)\n"
				+ "(Parameter.set 'Duration_Stretch "+rate+")\">.tmp1.scm ";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmdSchemeFile);
		Process process = builder.start();
		process.waitFor();
		process.destroy();
		
		//Create textfile with the message
		String cmdTxtFile= "echo \""+message+"\" > .tmp.txt";
		ProcessBuilder buildTxt= new ProcessBuilder("/bin/bash","-c", cmdTxtFile);
		Process processTxt= buildTxt.start();
		processTxt.waitFor();
		processTxt.destroy();
		
		//Convert the text to a wav file with properties in the scheme file
		String cmdWavFile= "text2wave -o .tmp.wav .tmp.txt -eval .tmp1.scm";
		ProcessBuilder buildWav= new ProcessBuilder("/bin/bash","-c",cmdWavFile);
		Process processWav= buildWav.start();
		String line;
		processWav.waitFor();
		processWav.destroy();
		
		//Convert the wave file into mp3 file
		String cmdText2Wave = "ffmpeg -i .tmp.wav -f mp3 " + fileName;
		ProcessBuilder builderText2Wave = new ProcessBuilder("/bin/bash", "-c", cmdText2Wave);
		Process processText2Wave = builderText2Wave.start();
		processText2Wave.waitFor();
		processText2Wave.destroy();

		// command used in bash terminal
		// deletes the temporary file
		String cmdDeleteTmps = "rm -f .tmp.wav .tmp1.scm .tmp.txt";

		// builds the command and runs it
		// deletes temporary file
		ProcessBuilder builderDeleteTmps = new ProcessBuilder("/bin/bash", "-c", cmdDeleteTmps);
		Process processDeleteTxt = builderDeleteTmps.start();
		processDeleteTxt.waitFor();
		processDeleteTxt.destroy();
		
		
		return null;
	}

}
