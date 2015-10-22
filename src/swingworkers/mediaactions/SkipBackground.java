package swingworkers.mediaactions;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.SwingWorker;

/**
 * Skipbackground take the skip the video forward or backward by 3 seconds whenever the fast forward or backwards is triggered
 */

public class SkipBackground extends SwingWorker<Object,Integer>{

	private EmbeddedMediaPlayer video;
	private boolean skipForward;
	
	private int i=1;
	/**
	 * default constructor
	 */
	public SkipBackground() {
	}
	/**
	 * Defined Constructor
	 * @param b
	 * @param video
	 */
	public SkipBackground(boolean b,EmbeddedMediaPlayer video) {
		this.skipForward=b;
		this.video=video;
	}

	@Override
	protected Object doInBackground() throws Exception {
		while(!this.isCancelled()){
				publish(i);
				if(video.getTime()<3000 && !skipForward){
					return null;
				}
			try{
			Thread.sleep(500);
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return null;
	}
	
	@Override
	protected void process(List<Integer> chunks){
		//fast forward or backward by 3 seconds
		if(skipForward){
			video.skip(3000);
		}else{
			video.skip(-3000);
		}
	}
	
	@Override
	protected void done(){
		try{
			this.get();
		}catch (CancellationException c){
			c.getStackTrace();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
