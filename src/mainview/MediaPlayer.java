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
import javax.swing.filechooser.FileNameExtensionFilter;

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
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;

public class MediaPlayer extends JFrame implements ActionListener,ChangeListener {
	/**
	 * MediaPlayer is tool to manipulate videos and synthetic speeches. MedidPlayer contains the main method
	 * which launches the application. 
	 */
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
			MediaPlayer.class.getResource("/javagui/resources/Save1.png"));
	private final ImageIcon speakIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/Speak2.png"));
	private final ImageIcon muteIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/Mute1.png"));
	
	//Panels
	private final JPanel contentPane = new JPanel();;
	private final JPanel screen = new JPanel();
	private final JPanel controls = new JPanel();
	private final JPanel speech = new JPanel();
	private final JPanel mergeAudio = new JPanel();
	private final JPanel filesPanel = new JPanel();
	private final JPanel audioEffects = new JPanel();
	private final JPanel videoEffects = new JPanel();
	private final JPanel confirmPanel = new JPanel();
	//Buttons
	private final JButton openVideo = new JButton("");
	private final JButton play = new JButton("");
	private final JButton forward = new JButton("");
	private final JButton backward = new JButton("");
	private final JButton volume = new JButton("");
	private final JButton speak = new JButton("");
	private final JButton save = new JButton("");
	private final JButton create = new JButton("Merge");
	private final JButton option = new JButton("Options");
	private final JButton mp3Browse = new JButton("Browse");
	private final JButton videoBrowse = new JButton("Browse");
	private final JButton dirBrowse = new JButton("Browse");
	private final JButton confirm = new JButton("Confirm");
	private final JButton cancel = new JButton("Cancel");
	
	//Sliders
	private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	
	//Spinners
	private final JSpinner startPitchSpnr = new JSpinner();
	private final JSpinner endPitchSpnr = new JSpinner();
	private JSpinner rateSpnr = new JSpinner( new SpinnerNumberModel(new Double(1.5),new Double(0.1),new Double(5.0),new Double(0.1)));
	private JSpinner bitrateSpnr = new JSpinner();
	private JSpinner volumeSpnr = new JSpinner();
	private JSpinner tempoSpnr = new JSpinner();
	private JSpinner fpsSpnr = new JSpinner();
	private JSpinner widthSpnr = new JSpinner();
	private JSpinner heightSpnr = new JSpinner();
	
	//Labels
	private final JLabel statuslbl = new JLabel();
	private final JLabel volumelbl = new JLabel();
	private final JLabel voicelbl = new JLabel("Voice");
	private final JLabel ratelbl= new JLabel("Rate");
	private final JLabel pitchlbl= new JLabel("Pitch");
	private final JLabel time = new JLabel();
	private final JLabel mergeTitle = new JLabel("Merge Audio and Video");
	private final JLabel videoFilelbl = new JLabel("Video File:");
	private final JLabel mp3Filelbl = new JLabel("mp3 File:");
	private final JLabel videoDurationlbl = new JLabel("Duration:");
	private final JLabel mp3Durationlbl = new JLabel("Duration:");
	private final JLabel audioEffectsLabel = new JLabel("Audio");
	private final JLabel videoEffectsLabel = new JLabel("Video");
	private JLabel videoDuration = new JLabel("00:00");
	private JLabel mp3Duration = new JLabel("00:00");
	private final JLabel lblBitrate = new JLabel("Bitrate");
	private final JLabel lblVolume = new JLabel("Volume");
	private final JLabel lblTempo = new JLabel("Tempo");
	private final JLabel lblEcho = new JLabel("Echo");
	private final JLabel lblFps = new JLabel("FPS");
	private final JLabel lblWidth = new JLabel("Width");
	private final JLabel lblHeight = new JLabel("Height");
	private final JLabel lblNegate = new JLabel("Negate");
	private final JLabel effectsLabel = new JLabel("Effects");
	private final JLabel lblName = new JLabel("Name :");
	private final JLabel lblSaveTo = new JLabel("Save to :");
	
	//TextFiedlds
	private final JTextField text = new JTextField();
	private final JTextField videoFiletf= new JTextField();;
	private final JTextField mp3Filetf= new JTextField();;
	private final JTextField newFileName = new JTextField();
	private final JTextField saveDirectory = new JTextField();
	
	//File Chooser and Directory chooser
	private JFileChooser chooser = null;
	
	//comboboxes
	private JComboBox<?> voiceOptions = new JComboBox();
	private final JComboBox extensions = new JComboBox();
	
	//Checkboxes
	private JCheckBox echoCheckBox = new JCheckBox("");
	private JCheckBox negateCheckBox = new JCheckBox("");
	private final JCheckBox playVideoCheckBox = new JCheckBox("Play the video when finished");
	
	//tables
	private final JTable table = new JTable();
	
	// timer, minute and second
	private final Timer timer = new Timer(200, this);
	private int minute = 0;
	private int second = 0;

	//External Frames
	private OptionsFrame op= null;
	private SaveSpeechFrame ssf = null;
	private BackgroundVoice bg = null;
	private SkipBackground sg = null;
	private AddMp3FileFrame amff = null;
	private MessageFrame mf = null;

	//video player components
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	private final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
	
	
	//Strings
	private final String[] pitchRange={"50 hz", "60 hz", "70 hz", "80 hz", "90 hz", "100 hz", "110 hz", "120 hz", "130 hz", "140 hz", 
							"150 hz", "160 hz", "170 hz", "180 hz", "190 hz", "200 hz", "210 hz", "220 hz", "230 hz", "240 hz", "250 hz"};
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
		controls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

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
		openVideo.setBounds(40, 40, 65, 40);
		openVideo.setText("Open");
		openVideo.setToolTipText("Open a Video File");
		openVideo.addActionListener(this);
		controls.add(openVideo);

		
		//Play button
		play.setBounds(390, 30, 80, 55);
		play.setIcon(playIcon);
		controls.add(play);
		play.addActionListener(this);
		play.setEnabled(false);
		

		// Forward button
		forward.setIcon(forwardIcon);
		forward.setBounds(490, 30, 80, 55);
		controls.add(forward);
		forward.addActionListener(this);
		forward.setEnabled(false);

		// Backward button
		backward.setIcon(backwardIcon);
		backward.setBounds(290, 30, 80, 55);
		controls.add(backward);
		backward.addActionListener(this);
		backward.setEnabled(false);
		
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
		option.setBounds(117, 40, 70, 40);
		option.setToolTipText("Resize the Screen and Change the Speed of the Video");
		controls.add(option);
		option.addActionListener(this);

		// Speech panel
		speech.setBackground(new Color(255, 255, 255));
		speech.setBounds(0, 700, 880, 140);
		contentPane.add(speech);
		speech.setLayout(null);
		
		
		//Voice label
		voicelbl.setFont(new Font("Time New Roman", Font.PLAIN, 16));
		voicelbl.setBounds(220, 15, 50, 25);
		speech.add(voicelbl);
		
		//Rate label
		ratelbl.setFont(new Font("Time New Roman", Font.PLAIN, 16));
		ratelbl.setBounds(400, 15, 50, 25);
		speech.add(ratelbl);
		
		//Pitch Label
		pitchlbl.setFont(new Font("Time New Roman", Font.PLAIN, 16));
		pitchlbl.setBounds(525,15,50,25);
		speech.add(pitchlbl);
		
		// input field
		text.setFont(new Font("Time New Roman", Font.PLAIN, 18));
		text.setBounds(200, 65, 560, 30);
		speech.add(text);
		text.setColumns(10);
		
		// Speak button
		speak.setBounds(40, 60, 65, 40);
		speak.setBackground(new Color(0, 0, 0));
		speak.setToolTipText("Speak the text in the text box");
		speak.setIcon(speakIcon);
		speech.add(speak);
		speak.addActionListener(this);
		speak.setEnabled(false);

		// save text button
		save.setBounds(117, 60, 65, 40);
		save.setToolTipText("Save the synthetic speech");
		save.setIcon(saveIcon);
		speech.add(save);
		save.addActionListener(this);
		save.setEnabled(false);

		// status of file being created
		statuslbl.setBounds(240, 100, 400, 35);
		statuslbl.setFont(new Font("Time New Roman", Font.BOLD,15));
		statuslbl.setForeground(new Color(0, 0, 0));
		statuslbl.setVisible(true);
		speech.add(statuslbl);
		
		// Create button opens new frame and let user create a video file
		create.setBackground(new Color(0, 0, 0));
		create.setBounds(780, 60, 65, 40);
		create.setToolTipText("Open the Creating Video Tools Window");
		speech.add(create);
		
		//rate spinner
		rateSpnr.setBounds(455, 15, 60, 25);
		rateSpnr.setToolTipText("Set rate for speech");
		speech.add(rateSpnr);
		
		// start pitch
		startPitchSpnr.setModel(new SpinnerListModel(pitchRange));
		startPitchSpnr.setBounds(580, 15, 80, 25);
		startPitchSpnr.setToolTipText("set start pitch of the speech");
		speech.add(startPitchSpnr);
		
		//end pitch
		endPitchSpnr.setModel(new SpinnerListModel(pitchRange));
		endPitchSpnr.setBounds(670, 15, 80, 25);
		endPitchSpnr.setToolTipText("set end pitch of the speech");
		speech.add(endPitchSpnr);
		
		// COmbo box for choosing voice
		voiceOptions.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Auckland", "kal_diphone"}));
		voiceOptions.setBounds(275, 15, 100, 25);
		voiceOptions.setToolTipText("select voice for speech");
		speech.add(voiceOptions);
		create.addActionListener(this);

		// set Frame
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MediaPlayer.class.getResource("/javagui/resources/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1330, 830);
		setContentPane(contentPane);
		
		// Merge Audio Panel
		mergeAudio.setBounds(880, 0, 450, 830);
		mergeAudio.setBackground(new Color(255, 255, 255));
		mergeAudio.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mergeAudio.setLayout(null);
		contentPane.add(mergeAudio);
		
		//File panels
		filesPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		filesPanel.setBackground(new Color(255, 255, 255));
		filesPanel.setBounds(0, 0, 450, 195);
		filesPanel.setLayout(null);
		mergeAudio.add(filesPanel);
		
		//Title on MergeAudio panel
		mergeTitle.setBounds(12, 0, 409, 24);
		mergeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mergeTitle.setFont(new Font("Dialog", Font.PLAIN, 16));
		filesPanel.add(mergeTitle);
		
		//videoFile lbl
		videoFilelbl.setBounds(12, 45, 90, 25);
		filesPanel.add(videoFilelbl);
		
		//Video text field
		videoFiletf.setBounds(98, 45, 224, 25);
		filesPanel.add(videoFiletf);
		videoFiletf.setColumns(10);
		
		//Video file browse button
		videoBrowse.setBounds(346, 45, 75, 25);
		filesPanel.add(videoBrowse);
		videoBrowse.addActionListener(this);
		
		//mp3 file label
		mp3Filelbl.setBounds(12, 120, 90, 25);
		filesPanel.add(mp3Filelbl);
		
		//mp3 file Text field
		mp3Filetf.setColumns(10);
		mp3Filetf.setBounds(98, 120, 224, 25);
		filesPanel.add(mp3Filetf);
		
		//mp3 file browse button
		mp3Browse.setBounds(346, 120, 75, 25);
		filesPanel.add(mp3Browse);
		mp3Browse.addActionListener(this);
		
		//Video duration label
		videoDuration.setHorizontalAlignment(SwingConstants.CENTER);
		videoDuration.setBounds(164, 82, 103, 25);
		filesPanel.add(videoDuration);
		
		//mp3 duration label
		mp3Duration.setHorizontalAlignment(SwingConstants.CENTER);
		mp3Duration.setBounds(164, 155, 103, 25);
		filesPanel.add(mp3Duration);
		
		//duration label
		videoDurationlbl.setBounds(12, 83, 90, 25);
		filesPanel.add(videoDurationlbl);
		
		//mp3 duration label
		mp3Durationlbl.setBounds(12, 155, 90, 25);
		filesPanel.add(mp3Durationlbl);
		
		// Audio Effects Panel
		audioEffects.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		audioEffects.setBackground(new Color(255, 255, 255));
		audioEffects.setBounds(0, 225, 224, 225);
		audioEffects.setLayout(null);
		mergeAudio.add(audioEffects);
		
		// Audio effect label
		audioEffectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		audioEffectsLabel.setBounds(68, 5, 70, 16);
		audioEffects.add(audioEffectsLabel);
		
		//Bitrate
		lblBitrate.setBounds(12, 40, 70, 16);
		audioEffects.add(lblBitrate);
		//set up bitrate spinner
		bitrateSpnr.setBounds(121, 37, 61, 22);
		audioEffects.add(bitrateSpnr);
		
		//volume of mp3 label
		lblVolume.setBounds(12, 80, 70, 16);
		audioEffects.add(lblVolume);
		
		//set up volume spinner
		volumeSpnr.setBounds(121, 77, 61, 22);
		audioEffects.add(volumeSpnr);
		
		//Tempo of mp3 / speed
		lblTempo.setBounds(12, 123, 70, 16);
		audioEffects.add(lblTempo);
		
		//set up tempo spinner
		tempoSpnr.setBounds(121, 120, 61, 22);
		audioEffects.add(tempoSpnr);
		
		//Echo label
		lblEcho.setBounds(12, 168, 70, 16);
		audioEffects.add(lblEcho);
		
		// set up the echo checkbox
		echoCheckBox.setBounds(139, 164, 25, 25);
		audioEffects.add(echoCheckBox);
		
		// set up the Video effects panel
		videoEffects.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		videoEffects.setBackground(new Color(255, 255, 255));
		videoEffects.setBounds(226, 225, 224, 225);
		videoEffects.setLayout(null);
		mergeAudio.add(videoEffects);
		
		// Video effect panel title
		videoEffectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		videoEffectsLabel.setBounds(77, 0, 70, 16);
		videoEffects.add(videoEffectsLabel);
		
		//Frame per second label 
		lblFps.setBounds(12, 41, 70, 16);
		videoEffects.add(lblFps);
		
		//video width label
		lblWidth.setBounds(12, 79, 70, 16);
		videoEffects.add(lblWidth);
		
		//video height label
		lblHeight.setBounds(12, 120, 70, 16);
		videoEffects.add(lblHeight);
		
		//negate video color label
		lblNegate.setBounds(12, 165, 70, 16);
		videoEffects.add(lblNegate);
		
		//set up frame per second spinner
		fpsSpnr.setBounds(128, 38, 61, 22);
		videoEffects.add(fpsSpnr);
		
		//set up video width spinner
		widthSpnr.setBounds(128, 76, 61, 22);
		videoEffects.add(widthSpnr);
		
		//set up video height spinner
		heightSpnr.setBounds(128, 117, 61, 22);
		videoEffects.add(heightSpnr);
		
		//Negate the color of the video checkbox
		negateCheckBox.setBounds(150, 161, 25, 25);
		videoEffects.add(negateCheckBox);
		
		//set up effects label
		effectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		effectsLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		effectsLabel.setBounds(145, 195, 146, 27);
		mergeAudio.add(effectsLabel);
		
		// Merge audio and video confirmation panel with time alignments list
		confirmPanel.setBackground(new Color(255, 255, 255));
		confirmPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		confirmPanel.setBounds(0, 451, 450, 380);
		confirmPanel.setLayout(null);
		mergeAudio.add(confirmPanel);
		
		
		// Name of new file label
		lblName.setToolTipText("Name of the new file");
		lblName.setBounds(12, 170, 70, 16);
		confirmPanel.add(lblName);
		
		// Save to a certain directory label
		lblSaveTo.setBounds(12, 215, 70, 16);
		confirmPanel.add(lblSaveTo);
		
		//Name of the new file text box
		newFileName.setColumns(10);
		newFileName.setBounds(94, 167, 224, 22);
		confirmPanel.add(newFileName);
		
		//Directory where the file will be saved
		saveDirectory.setColumns(10);
		saveDirectory.setBounds(94, 212, 224, 22);
		confirmPanel.add(saveDirectory);
		try {
			saveDirectory.setText(new java.io.File(".").getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Browse for directory button
		dirBrowse.setBounds(330, 211, 75, 25);
		confirmPanel.add(dirBrowse);
		dirBrowse.addActionListener(this);
		
		//file extensions combo box
		extensions.setModel(new DefaultComboBoxModel(new String[] {".avi", ".mp4", ".mkv"}));
		extensions.setBounds(330, 167, 75, 22);
		confirmPanel.add(extensions);
		
		//play the video when finished?
		playVideoCheckBox.setBounds(94, 243, 224, 25);
		confirmPanel.add(playVideoCheckBox);
		
		//set up confirm button
		confirm.setBounds(68, 294, 97, 25);
		confirmPanel.add(confirm);
		confirm.addActionListener(this);
		
		//set up cancel button
		cancel.setBounds(308, 294, 97, 25);
		confirmPanel.add(cancel);
		cancel.addActionListener(this);
		
		//table of time points
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Second", "Minute"
			}
		));
		table.setBounds(12, 13, 409, 141);
		confirmPanel.add(table);
		
		// set Frame
		setIconImage(Toolkit.getDefaultToolkit().getImage(MediaPlayer.class.getResource("/javagui/resources/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1330, 830);
		setContentPane(contentPane);
		setVisible(true);

		// initiate timer
		timer.start();

	}
	/**
	 * Perform tasks when users click buttons on the GUI 
	 */
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
		//If the textField is not empty enable speak and save buttons
		if(!text.getText().trim().equals("")){
			speak.setEnabled(true);
			save.setEnabled(true);
		}else{
			speak.setEnabled(false);
			save.setEnabled(false);
		}
		
		// Buttons action perform
		if (e.getSource() == openVideo) {  //Open a video file
			fileChooserInit("Video", openVideo);
		} else if (e.getSource()==option){	   // Option frame, choose Rate and mode
			if(op!=null){
				op.setVisible(true);
			}else{
				op= new OptionsFrame();
				op.setMediaPlayer(this);
			}
		} else if (e.getSource() == play) { 	// Play the video
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
		} else if (e.getSource() == forward) {	// Fast forward
			if (sg != null) {
				sg.cancel(true);
				sg = null;
			}
			sg = new SkipBackground(true, video);
			sg.execute();

		} else if (e.getSource() == backward) {	// backward
			if (sg != null) {
				sg.cancel(true);
				sg = null;
			}
			sg = new SkipBackground(false, video);
			sg.execute();
			
		} else if (e.getSource() == speak) {		// festival, speak
			if(bg!=null&&!bg.isDone()){
				bg.cancel(true);
				bg=null;
				speak.setText("");
				speak.setIcon(speakIcon);
				return;
			}
			
			if(!text.getText().trim().equals("")){
				bg=null;
				String start= (String) startPitchSpnr.getValue();
				String startHz= start.substring(0, 3).trim();
				String end= (String) endPitchSpnr.getValue();
				String endHz= end.substring(0, 3).trim();
				bg = new BackgroundVoice(text.getText(),this,(String)voiceOptions.getSelectedItem(), (double)rateSpnr.getValue(), Integer.parseInt(startHz),Integer.parseInt(endHz));
				bg.execute();
				speak.setIcon(null);
				speak.setText("Cancel");
			}
		} else if (e.getSource() == save) {			// Save the synthetic speech in an mp3 file
			if (wordCount() > 30) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 7", "Word count is: "
						+ wordCount() + ". Must be less than 30 words.");
				mf.setVisible(true);
				return;
			} else if (text.getText().equals("")) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame("Error", "ERROR 1",
						"Text field must not be empty!");
				mf.setVisible(true);
				return;
			} else if (ssf != null) {
				ssf.dispose();
			}
			ssf = new SaveSpeechFrame();
			ssf.setVisible(true);
			ssf.setSpeech(text.getText());
		} else if (e.getSource() == create) {		// Open Create window with tools
			if (amff != null) {
				amff.dispose();
			}
			amff = new AddMp3FileFrame();
			amff.addVideo(video);
			amff.addStatuslbl(statuslbl);
			amff.setVisible(true);
			amff.addMediaPlayer(this);
			amff.addCurrentVideo(videoTitle);
		} else if(e.getSource() == videoBrowse){
			fileChooserInit("Video",videoBrowse);
		}else if(e.getSource() == mp3Browse){
			fileChooserInit("mp3",mp3Browse);
		}else if(e.getSource() == dirBrowse){
			directoryChooser();
		}
	}

	/**
	 * Change volume of the video when Jslider is moved.
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		video.setVolume(slider.getValue());
		if (slider.getValue()==0){
			volumelbl.setIcon(muteIcon);
		}else{
			volumelbl.setIcon(volumeIcon);
		}
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
	
	//Enable control button
	public void enableButtons(){
		play.setEnabled(true);
		forward.setEnabled(true);
		backward.setEnabled(true);
	}
	//set the video file textfield with the name of the current playing file
	public void addCurrentVideo(String videoFile) {
		videoFiletf.setText(videoFile);
	}
	
	/**
	 * Initialise filechooser when User click the open button or browse buttons
	 */
	public void fileChooserInit(String fileType, JButton clickedButton){
		if (chooser == null) {
			chooser = new JFileChooser();
			FileNameExtensionFilter filter;
			if(fileType.equals("Video")){
				filter = new FileNameExtensionFilter("Video File", "avi", "mp4", "mkv");
				chooser.setDialogTitle("Choose a Video File");
			}else{
				filter = new FileNameExtensionFilter("Audio File", "mp3");
				chooser.setDialogTitle("Choose an Audito File");
			}
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				setVideoTitle(chooser.getSelectedFile().toString());
				if(clickedButton.equals(openVideo)){
					playVideo();
					enableButtons();
					addCurrentVideo(videoTitle);
				}else if(clickedButton.equals(videoBrowse)){
					videoFiletf.setText(chooser.getSelectedFile().toString());
				}else{
					mp3Filetf.setText(chooser.getSelectedFile().toString());
				}
			}
			chooser = null;
		}
	}
	/**
	 * Initialise directory chooser when User click the browse buttons
	 */
	public void directoryChooser() {
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Find Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				saveDirectory.setText(chooser.getSelectedFile().toString());
			}
			chooser = null;
		}
	}
}