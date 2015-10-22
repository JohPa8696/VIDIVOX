package add_mp3_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import background_tasks.FilesRemover;
import background_tasks.GetMediaFileDurationTask;
import mainview.MediaPlayer;

public class MergeAudioAndVideo extends SwingWorker<Object,Integer> {
	/**
	 * MergeAudioAndVideo merges audio files with video files at specific time points and with user chosen effects
	 */
	private ArrayList<String> mp3Files= null;
	private ArrayList<String> startTimes= null;
	private ArrayList<Integer> startTimesInSecond= new ArrayList<Integer>();
	private ArrayList<String> commands= new ArrayList<String>();
	private ArrayList<Object> effects= null;
	private ArrayList<String> intermidiateFiles= new ArrayList<String>();
	private String vidFile;
	private String outputFile;
	private EmbeddedMediaPlayer video;
	private JLabel statuslbl;
	private boolean playVideo;
	private File outputName;
	private MediaPlayer mediaPlayer;
	private int n=0;
	
	/**
	 * Constructor
	 * @param effects
	 * @param mp3Files
	 * @param startTimes
	 * @param vidFile
	 * @param outputFile
	 * @param video
	 * @param statuslbl
	 * @param playVideo
	 * @param mediaPlayer
	 */
	public MergeAudioAndVideo(ArrayList<Object> effects,ArrayList<String> mp3Files,ArrayList<String> startTimes,
			String vidFile ,String outputFile ,EmbeddedMediaPlayer video, JLabel statuslbl, boolean playVideo, MediaPlayer mediaPlayer){
		this.effects=effects;
		this.mp3Files = mp3Files;
		this.startTimes=startTimes;
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
		timeConvertor(); // Convert all start time to second
		String f= mediaPlayer.getProjectPath();
		if(effects!=null){
			if((Boolean)effects.get(5)){
				
				//If user want to strip the audio from the original video
				String cmdStripAudio= "ffmpeg -y -i "+vidFile+" -c copy -an "+f+"/tmp.avi";
				ProcessBuilder buildStrip= new ProcessBuilder("/bin/bash","-c",cmdStripAudio);
				Process processStrip=buildStrip.start();
				intermidiateFiles.add(f+"/tmp.avi");
				processStrip.waitFor();
				processStrip.destroy();
				vidFile=f+"/tmp.avi";
			}
		}
		String cmdOutputFile="ffmpeg -y -i "+vidFile;
		
		//Build the commands to create mp3 files with start time length. Commands using aevalsrc filter
		for(int i=0; i< mp3Files.size(); i++){
			commands.add("ffmpeg -y -i "+mp3Files.get(i)+
					" -filter_complex \"aevalsrc=0:d="+startTimesInSecond.get(i)+"[s1];[s1][0:a]concat=n=2:v=0:a=1[aout]\" -map [aout] "+ f+"/foo"+i+".mp3");
			cmdOutputFile=cmdOutputFile+" -i "+f+"/foo"+i+".mp3";
			intermidiateFiles.add(f+"/foo"+i+".mp3");
		}
		
		//Video effects
		if(effects!=null){
			if(vidFile.equals(f+"/tmp.avi")){
				if(!(Boolean)effects.get(6)){
					if((Integer)effects.get(4)==4){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else if((Integer)effects.get(4)==1 || (Integer)effects.get(4)==5){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -vf transpose=1 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else{
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -vf transpose=0 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}
					
				}else if ( (Boolean)effects.get(6)){
					if((Integer)effects.get(4)==4){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -vf negate -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else if((Integer)effects.get(4)==1 || (Integer)effects.get(4)==5){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -vf negate,transpose=1 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else{
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size())+" -vf negate,transpose=0 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}
				}
			}else{
				if(!(Boolean)effects.get(6)){
					if((Integer)effects.get(4)==4){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else if((Integer)effects.get(4)==1 || (Integer)effects.get(4)==5){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -vf transpose=1 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else{
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -vf transpose=0 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}
					
				}else if ( (Boolean)effects.get(6)){
					if((Integer)effects.get(4)==4){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -vf negate -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else if((Integer)effects.get(4)==1 || (Integer)effects.get(4)==5){
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -vf negate,transpose=1 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}else{
						cmdOutputFile= cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" -vf negate,transpose=0 -r "+(Integer)effects.get(3)+" "+f+"/tmp1.avi";
					}
				}
			}
			intermidiateFiles.add(f+"/tmp1.avi");
		}else{
			cmdOutputFile=cmdOutputFile+" -filter_complex amix=inputs="+(mp3Files.size()+1)+" "+outputFile;
		}
		//Execute the mp3 files commands
		commands.add(cmdOutputFile);
		for(String s: commands){
			ProcessBuilder buildMp3Files= new ProcessBuilder("/bin/bash","-c",s);
			Process processMp3Files=buildMp3Files.start();
			//Read input from error stream, see when the file is done.
			InputStream out= processMp3Files.getErrorStream();
			BufferedReader bf=new BufferedReader(new InputStreamReader(out));
			while((bf.readLine())!=null){
				publish();
			}
			processMp3Files.waitFor();
			processMp3Files.destroy();
		}
		
		if(effects!=null){
			int i=1;
			if((Integer)effects.get(4)==5){
				String cmd180Flip= "ffmpeg -y -i "+f+"/tmp1.avi -vf transpose=1 "+f+"/tmp2.avi";
				ProcessBuilder build180Flip= new ProcessBuilder("/bin/bash","-c", cmd180Flip);
				Process process180Flip= build180Flip.start();
				//Read input from error stream, see when the file is done.
				InputStream in= process180Flip.getErrorStream();
				BufferedReader b=new BufferedReader(new InputStreamReader(in));
				while((b.readLine())!=null){
					publish();
				}
				intermidiateFiles.add(f+"/tmp2.avi");
				i++;
				process180Flip.waitFor();
				process180Flip.destroy();
			}
			
			String cmdAudioEffects;
			if(!(Boolean)effects.get(2)){
				cmdAudioEffects= "ffmpeg -y -i "+f+"/tmp"+i+".avi"+" -af volume="+(Double)effects.get(0)+",atempo="+(Double)effects.get(1)+ " " +outputFile;
			}else{
				cmdAudioEffects= "ffmpeg -y -i "+f+"/tmp"+i+".avi"+" -af volume="+(Double)effects.get(0)+",atempo="+(Double)effects.get(1)+ ",aecho " +outputFile;
			}
			ProcessBuilder buildNewFile= new ProcessBuilder("/bin/bash","-c",cmdAudioEffects);
			Process processNewFile=buildNewFile.start();
			//Read input from error stream, see when the file is done.
			InputStream out= processNewFile.getErrorStream();
			BufferedReader bf=new BufferedReader(new InputStreamReader(out));
			while((bf.readLine())!=null){
				publish();
			}
			processNewFile.waitFor();
			processNewFile.destroy();
		}
		//Remove intermidiate files
		FilesRemover filesRemove= new FilesRemover(intermidiateFiles);
		filesRemove.execute();
		return null;
	}
	@Override 
	protected void process(List<Integer> chunks){
		
		this.statuslbl.setText("Creating "+outputName.getName()+", please wait...");
		if(!(n>=480)){
			n+=5;
		}
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
	
	/**
	 * Convert from minute:second time format to second
	 */
	public void timeConvertor(){
		for(String s: startTimes){
			int min=Integer.parseInt(s.substring(0,2));
			int sec=Integer.parseInt(s.substring(3,5));
			startTimesInSecond.add(min*60 +sec);
		}
	}
}
