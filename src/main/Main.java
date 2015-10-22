package main;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import genericframes.launcher.ProjectSelectFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;



/**
 * Main Class launches the launcher frame and ask user for a project directory
 */
public class Main {
	
	
	public static void main(String[] args){
		
		try {
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
					"/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib");
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ProjectSelectFrame().setVisible(true);
			}
		});
	}
	

}
