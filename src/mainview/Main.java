package mainview;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import background_tasks.GlobalProjectFolder;
import generic_frames.ProjectSelectFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Main {
	/**
	 * Main Class launches the Media Player Window
	 */
	
	public static void main(String[] args){
		
		try {
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
					"/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib");
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//Create project frame
		ProjectSelectFrame pf= new ProjectSelectFrame();
		pf.setVisible(true);
	}
	

}
