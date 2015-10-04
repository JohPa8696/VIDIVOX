package mainview;

import generic_frames.BrowseFileFrame;
import generic_frames.MessageFrame;
import generic_frames.OptionsFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import add_mp3_file.AddMp3FileFrame;
import background_tasks.BackgroundVoice;
import background_tasks.SkipBackground;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import save_speech.SaveSpeechFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;

public class MediaPlayer extends JFrame implements ActionListener,
		ChangeListener {

	// Icons were taken from Icongal.com
	private final ImageIcon playIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/play.png"));
	private final ImageIcon forwardIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/forward.png"));
	private final ImageIcon backwardIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/backward.png"));
	private final ImageIcon volumeIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/volume.png"));
	private final ImageIcon stopIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/stop.png"));
	private final ImageIcon saveIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/save2.png"));
	private final ImageIcon speakIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/speak.png"));
	private final ImageIcon fileIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/openfile.png"));

	private JTextField text = new JTextField();
	private final JLabel volumelbl = new JLabel();
	private final JPanel contentPane = new JPanel();;
	private final JPanel screen = new JPanel();
	private final JPanel controls = new JPanel();
	private final JPanel speech = new JPanel();

	private final JButton pickVideoFile = new JButton("");
	private final JButton play = new JButton("");
	private final JButton forward = new JButton("");
	private final JButton backward = new JButton("");
	private final JButton volume = new JButton("");
	private final JButton speak = new JButton("");
	private final JButton cancel = new JButton("Cancel");
	private final JButton save = new JButton("");
	private final JButton openFile = new JButton("");
	private final JButton addCommentary = new JButton("Add");
	private JButton create = new JButton("Create");
	private JButton option = new JButton("Options");
	private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	private JLabel statuslbl = new JLabel();
	private JLabel time = new JLabel();

	private Timer timer = new Timer(200, this);
	private int minute = 0;
	private int second = 0;

	private OptionsFrame op= null;
	private SaveSpeechFrame ssf = null;
	private BackgroundVoice bg = null;
	private SkipBackground sg = null;
	private AddMp3FileFrame amff = null;
	private BrowseFileFrame bf1 = null;
	private BrowseFileFrame bf2 = null;
	private MessageFrame mf = null;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	private final EmbeddedMediaPlayer video = mediaPlayerComponent
			.getMediaPlayer();

	private String videoTitle = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
				new MediaPlayer();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MediaPlayer() {

		// Setting contentPane;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		// video screen
		screen.setBackground(SystemColor.menu);
		screen.setBounds(0, 0, 880, 600);
		contentPane.add(screen);
		screen.setLayout(new BorderLayout(0, 0));
		screen.setVisible(true);
		mediaPlayerComponent.setBackground(Color.DARK_GRAY);
		screen.add(mediaPlayerComponent);

		// Control panel with control buttons
		controls.setBackground(new Color(255, 255, 255));
		controls.setBounds(0, 600, 880, 100);
		contentPane.add(controls);
		controls.setLayout(null);
		
		// timer
		time.setBounds(395, 5, 80, 20);
		time.setFont(new Font("Time New Roman", Font.PLAIN, 15));
		time.setText("00:00:00");
		time.setForeground(Color.BLACK);
		controls.add(time);
		

		// pick a video file to play
		pickVideoFile.setBounds(40, 40, 65, 40);
		pickVideoFile.setText("Open");
		pickVideoFile.setToolTipText("Open a Video File");
		pickVideoFile.addActionListener(this);
		controls.add(pickVideoFile);

		
		//Play button
		play.setBounds(390, 30, 80, 55);
		play.setIcon(playIcon);
		controls.add(play);
		play.addActionListener(this);
		

		// Forward button
		forward.setIcon(forwardIcon);
		forward.setBounds(490, 30, 80, 55);
		controls.add(forward);
		forward.addActionListener(this);
		

		// Backward button
		backward.setIcon(backwardIcon);
		backward.setBounds(290, 30, 80, 55);
		controls.add(backward);
		backward.addActionListener(this);

		// volume label
		volumelbl.setBounds(600, 30, 48, 60);
		volumelbl.setIcon(volumeIcon);
		controls.add(volume);
		volumelbl.setVisible(true);
		controls.add(volumelbl);
		

		// volume slider
		slider.setBounds(650, 30, 220, 65);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		controls.add(slider);
		
		//Option button
		option.setBackground(new Color(250, 250, 250));
		option.setBounds(117, 40, 65, 40);
		option.setToolTipText("Resize the Screen and Change the Speed of the Video");
		controls.add(option);
		option.addActionListener(this);

		// Speech panel
		speech.setBackground(new Color(255, 255, 255));
		speech.setBounds(0, 700, 880, 80);
		contentPane.add(speech);
		speech.setLayout(null);

		// input field
		text = new JTextField();
		text.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		text.setBounds(200, 10, 560, 30);
		speech.add(text);
		text.setColumns(10);

		// Speak button
		speak.setBounds(40, 5, 65, 40);
		speak.setBackground(new Color(0, 0, 0));
		speak.setToolTipText("Speak the text in the text box");
		speak.setIcon(speakIcon);
		speech.add(speak);
		speak.addActionListener(this);

		// Cancel button
		cancel.setBounds(530, 40, 65, 40);
		cancel.setForeground(SystemColor.textHighlight);
		cancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		cancel.setToolTipText("Cancel the speech");
		//speech.add(cancel);
		cancel.addActionListener(this);

		// save text button
		save.setBounds(117, 5, 65, 40);
		save.setToolTipText("Save the synthetic speech");
		save.setIcon(saveIcon);
		speech.add(save);
		save.addActionListener(this);

		/*Open file button
		openFile.setBounds(445, 40, 65, 40);
		openFile.setToolTipText("Select an audio file and a video file and merge them");
		openFile.setIcon(fileIcon);
		speech.add(openFile);
		openFile.addActionListener(this); */

		// Add comment button
		addCommentary.setBounds(190, 40, 65, 40);
		addCommentary.setFont(new Font("Tahoma", Font.BOLD, 10));
		addCommentary.setToolTipText("Add text to the video");
		//speech.add(addCommentary);
		addCommentary.addActionListener(this);
		statuslbl.setBackground(new Color(0, 0, 0));

		// status of file being created
		statuslbl.setBounds(240, 45, 400, 35);
		statuslbl.setFont(new Font("Time New Roman", Font.BOLD,
				15));
		//statuslbl.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaqqqqqqqqqqqqqq");
		statuslbl.setForeground(new Color(0, 0, 0));
		statuslbl.setVisible(true);
		speech.add(statuslbl);
		
		// Create button opens new frame and let user create a video file
		create.setBackground(new Color(0, 0, 0));
		create.setBounds(780, 5, 65, 40);
		create.setToolTipText("Open the Creating Video Tools Window");
		speech.add(create);
		create.addActionListener(this);

		// set Frame
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MediaPlayer.class.getResource("/javagui/resources/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 780);
		setContentPane(contentPane);
		setVisible(true);

		// initiate timer
		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Only show minute and second
		if (((int) video.getTime() != -1)) {
			if (second < 60) {
				second = (int) video.getTime() / 1000 - minute * 60;
			}
			if (second >= 60) {
				second = 0;
				minute = minute + 1;
			}
			if (second < 10 || minute < 10) {
				if (second < 10 && minute < 10) {
					time.setText("00:" + "0" + minute + ":0" + second);
				} else if (second < 10) {
					time.setText("00:" + minute + ":0" + second);
				} else {
					time.setText("00:" + "0" + minute + ":" + second);
				}
			}
		} else {
			second = 0;
			minute = 0;
			time.setText("00:00:00");
		}

		// If there is video playing set the play button with the stop icon or
		// otherwise.
		if (video.getTime() == video.getLength() || !video.isPlaying()) {
			play.setIcon(playIcon);
		} else if (video.isPlaying()) {
			play.setIcon(stopIcon);
		}
		if (e.getSource() == pickVideoFile) {
			if (bf1 != null) {
				bf1.dispose();
			}
			bf1 = new BrowseFileFrame("Open Video File", "Select a video file");
			bf1.addMediaPlayer(this);
			bf1.addCurrentVideo(videoTitle);
			bf1.setVisible(true);

		} else if (e.getSource()==option){
			if(op!=null){
				op.setVisible(true);
			}else{
				op= new OptionsFrame();
				op.setMediaPlayer(this);
			}
		
		
		
		} else if (e.getSource() == play) {
			if (videoTitle == null) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 4",
						"No file has been selected");
				mf.setVisible(true);
			}
			if (sg != null) {
				sg.cancel(true);
				sg = null;
			}
			if (video.isPlaying() == false) {
				play.setIcon(stopIcon);
				video.play();
			} else {
				play.setIcon(playIcon);
				video.pause();
			}
		} else if (e.getSource() == forward) {
			if (sg != null) {
				sg.cancel(true);
				sg = null;
			}
			sg = new SkipBackground(true, video);
			sg.execute();

		} else if (e.getSource() == backward) {
			if (sg != null) {
				sg.cancel(true);
				sg = null;
			}
			sg = new SkipBackground(false, video);
			sg.execute();

		} else if (e.getSource() == speak) {
			if(bg!=null&&!bg.isDone()){
				bg.cancel(true);
				bg=null;
				speak.setText("");
				speak.setIcon(speakIcon);
				return;
			}
			
			if(!text.getText().trim().equals("")){
				bg=null;
				bg = new BackgroundVoice("echo " + text.getText(),this);
				bg.execute();
				speak.setIcon(null);
				speak.setText("Cancel");
			}
		} else if (e.getSource() == save) {
			if (wordCount() > 30) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR", "Word count is: "
						+ wordCount() + ". Must be less than 30 words.");
				mf.setVisible(true);
				return;
			} else if (text.getText().equals("")) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR",
						"Text field must not be empty!");
				mf.setVisible(true);
				return;
			} else if (ssf != null) {
				ssf.dispose();
			}
			ssf = new SaveSpeechFrame();
			ssf.setVisible(true);
			ssf.setSpeech(text.getText());
			System.out.println(wordCount());
		} else if (e.getSource() == create) {
			if (amff != null) {
				amff.dispose();
			}
			amff = new AddMp3FileFrame();
			amff.addVideo(video);
			amff.addStatuslbl(statuslbl);
			amff.setVisible(true);
			amff.addMediaPlayer(this);
			amff.addCurrentVideo(videoTitle);
		} else if (e.getSource() == addCommentary) {
			if (text.getText().equals("")) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR",
						"Text field must not be empty!");
				mf.setVisible(true);
				return;
			} else if (bf2 != null) {
				bf2.dispose();
			}
			bf2 = new BrowseFileFrame("Add Text to Video",
					"Fill in blank fields");
			bf2.addMediaPlayer(this);
			bf2.addCurrentVideo(videoTitle);
			bf2.setVisible(true);
		}
	}

	// Volume control
	@Override
	public void stateChanged(ChangeEvent arg0) {
		video.setVolume(slider.getValue());
	}

	// set the video title
	public void setVideoTitle(String title) {
		this.videoTitle = title;
	}

	// get the video title
	public String getVideoTitle() {
		return this.videoTitle;
	}

	// Get the message from the text field
	public String getTextMessage() {
		return text.getText();
	}

	// Get the video that is currently playing
	public EmbeddedMediaPlayer getVideo() {
		return this.video;
	}

	// Get Jlabel status
	public JLabel getStatuslbl() {
		return this.statuslbl;
	}

	// play the Video
	public void playVideo() {
		video.playMedia(videoTitle);
		video.start();
	}

	// set the minute and second label to 0
	public void setTime() {
		this.second = 0;
		this.minute = 0;
	}
	// get the speak button
	public JButton getSpeakButton(){
		return this.speak;
	}
	// get the speak Icon
		public ImageIcon getSpeakIcon(){
			return this.speakIcon;
		}
	// counts number of words in the text field
	public int wordCount() {
		String speechText = text.getText().trim();
		if (speechText.isEmpty())
			return 0;
		return speechText.split("\\s+").length; // separate string around spaces
	}
}