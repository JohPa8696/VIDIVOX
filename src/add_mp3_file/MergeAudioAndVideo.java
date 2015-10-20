package add_mp3_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import background_tasks.GetMediaFileDurationTask;
import mainview.MediaPlayer;

public class MergeAudioAndVideo extends SwingWorker<Object,Integer> {
	/**
	 * AddMp3
	 */
	private String audFile;
	private String vidFile;
	private String outputFile;
	private EmbeddedMediaPlayer video;
	private JLabel statuslbl;
	private boolean playVideo;
	private File outputName;
	private MediaPlayer mediaPlayer;
	private int n=0;
	
	/**
	 * constructor
	 * @param fileName
	 * @param video
	 */
	public MergeAudioAndVideo(String audFile, String vidFile ,String outputFile ,EmbeddedMediaPlayer video, JLabel statuslbl, boolean playVideo, MediaPlayer mediaPlayer){
		this.audFile = audFile;
		this.vidFile = vidFile;
		this.outputFile = outputFile;
		outputName = new File(outputFile);
		this.video = video;
		this.statuslbl= statuslbl;
		this.playVideo = playVideo;
		this.mediaPlayer = mediaPlayer;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		String cmd="ffmpeg -y -i "+vidFile+" -i "+audFile+" -filter_complex amix=inputs=2 "+outputFile;
		ProcessBuilder builder= new ProcessBuilder("/bin/bash", "-c",cmd);
		Process process=builder.start();
		//Read output from the terminal which is the copying process
		InputStream stdout = process.getErrorStream();
		BufferedReader stdoutBuffered = new BufferedReader( new InputStreamReader(stdout));
		//while loop exit only when there is no more input from the terminal meaning the file is created completely
		String line;
		while ((line=stdoutBuffered.readLine()) != null) {
			publish ();
		}
		process.waitFor();
		process.destroy();
		return null;
	}
	@Override 
	protected void process(List<Integer> chunks){
		
		this.statuslbl.setText("Creating "+outputName.getName()+", please wait...");
		n+=20;
		mediaPlayer.getProgressBar().setValue(n);
	}
	@Override
	protected void done(){
		this.statuslbl.setText("Successfully created "+outputName.getName()+"!");
		mediaPlayer.getProgressBar().setValue(500);
		GetMediaFileDurationTask durationTask= new GetMediaFileDurationTask(outputFile,mediaPlayer.getVideoDurationLabel());
		if(playVideo){
			video.playMedia(outputFile);
			mediaPlayer.setVideoTitle(outputFile);
			mediaPlayer.setTime();
			durationTask.execute();
			video.start();
			mediaPlayer.enableButtons();
		}
	}

}
