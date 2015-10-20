package generic_frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import mainview.MediaPlayer;
import add_text.AddText;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class BrowseFileFrame extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JTextField videoField;
	private JFrame thisFrame = this;
	private JFileChooser fileChooser = null;
	private JLabel videoFilelbl = new JLabel("Video");
	private JButton browseVideoFile = new JButton("Browse");
	private JButton confirm = new JButton("Confirm");
	private MessageFrame mf = null;
	private JButton cancel = new JButton("Cancel");
	private MediaPlayer mediaPlayer = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowseFileFrame window = new BrowseFileFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public BrowseFileFrame() {
		setTitle("Open Video File");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,580, 400, 150);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255,255,255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);


		// video file field
		videoField = new JTextField();
		videoField.setBounds(60, 30, 230, 35);
		contentPane.add(videoField);

		// field for selecting video file
		videoFilelbl.setBounds(10, 40, 50, 15);
		contentPane.add(videoFilelbl);

		// Browse button let user select file
		browseVideoFile.addActionListener(this);
		browseVideoFile.setBounds(300, 32, 90, 30);
		contentPane.add(browseVideoFile);

		// Confirm users choice
		confirm.addActionListener(this);
		confirm.setBounds(60, 100, 117, 25);
		contentPane.add(confirm);

		cancel.addActionListener(this);
		cancel.setBounds(230, 100, 117, 25);
		contentPane.add(cancel);
	}

	public void addCurrentVideo(String videoFile) {
		videoField.setText(videoFile);
	}

	public void addMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== confirm){
			File videoFile = new File(videoField.getText());
			if (thisFrame.getTitle().equals("Open Video File")) {
				if (!videoFile.exists() || videoFile.isDirectory()) {
					if (videoField.getText().equals("")) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 1",
								"Please fill in blank fields");
					} else if (videoFile.isDirectory()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 3a",
								videoFile.getName() + " is a directory");
					} else {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 2a",
								videoFile.getName() + " does not exist");
					}
					mf.setVisible(true);
					return;
				} else {
					mediaPlayer.setVideoTitle(videoField.getText());
					mediaPlayer.playVideo();
					mediaPlayer.enableButtons();
				}
			} 
			thisFrame.dispose();
		}else if (e.getSource()==  cancel){
			thisFrame.dispose();
		}else{
			if (fileChooser == null) {
				fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Media File", "avi", "mp4", "mkv");
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser
						.setDialogTitle("choothis.mediaPlayer=mediaPlayer;sertitle");
				fileChooser
						.setFileFilter((javax.swing.filechooser.FileFilter) filter);
				fileChooser.setAcceptAllFileFilterUsed(false);

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	
					mediaPlayer.setVideoTitle(fileChooser.getSelectedFile()
							.toString());
					mediaPlayer.playVideo();
					mediaPlayer.enableButtons();
				}
				fileChooser = null;
			}
		}
		
	}
}
