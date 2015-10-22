package mediaplayer;

import genericframes.helperframes.*;
import swingworkers.festival.BackgroundVoice;
import swingworkers.mediaactions.*;
import swingworkers.merger.MergeAudioAndVideo;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import utilities.errorhandlers.InvalidFilesCheck;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;

import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * MediaPlayer is our main window that contains video and audio manipulating tools.
 * User can use the tools on the UI to manipulate input videos with absolute flexibility.
 */
public class MediaPlayer extends JFrame implements ActionListener,ChangeListener {
	
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
	private final ImageIcon tickIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/tick48.png"));
	private final ImageIcon addIcon = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/Add.png"));
	
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
	private final JButton create = new JButton("Hide");
	private final JButton option = new JButton("Options");
	private final JButton mp3Browse = new JButton("Browse");
	private final JButton videoBrowse = new JButton("Browse");
	private final JButton dirBrowse = new JButton("Browse");
	private final JButton confirm = new JButton("Start Merging");
	private final JButton playMp3Files= new JButton("Play");
	private final JButton deleteMp3Files= new JButton("Delete");
	private final JButton addToTable = new JButton("");
	private final JButton enableEffects= new JButton("Disable");
	
	//Sliders
	private final JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	
	//Spinners
	private final JSpinner startPitchSpnr = new JSpinner();
	private final JSpinner endPitchSpnr = new JSpinner();
	private JSpinner rateSpnr = new JSpinner( new SpinnerNumberModel(new Double(1.5),new Double(0.1),new Double(5.0),new Double(0.1)));
	private JSpinner volumeSpnr = new JSpinner(new SpinnerNumberModel(new Double(1.0),new Double(0.1),new Double(2.0),new Double(0.1)));
	private JSpinner tempoSpnr = new JSpinner(new SpinnerNumberModel(new Double(1.0),new Double(0.5),new Double(2.0),new Double(0.5)));
	private JSpinner transposeSpnr = new JSpinner(new SpinnerNumberModel(new Integer(0),new Integer(0),new Integer(270),new Integer(90)));
	private JSpinner fpsSpnr = new JSpinner(new SpinnerNumberModel(new Integer(30),new Integer(5),new Integer(60),new Integer(5)));

	
	//Labels
	private final JLabel videoLength= new JLabel();
	private final JLabel statuslbl = new JLabel();
	private final JLabel volumelbl = new JLabel();
	private final JLabel voicelbl = new JLabel("Voice");
	private final JLabel ratelbl= new JLabel("Rate");
	private final JLabel pitchlbl= new JLabel("Pitch");
	private final JLabel time = new JLabel();
	private final JLabel slash= new JLabel("/");
	private final JLabel mergeTitle = new JLabel("Merge Audio and Video");
	private final JLabel videoFilelbl = new JLabel("Video File:");
	private final JLabel mp3Filelbl = new JLabel("mp3 File:");
	private final JLabel videoDurationlbl = new JLabel("Duration:");
	private final JLabel mp3Durationlbl = new JLabel("Duration:");
	private final JLabel audioEffectsLabel = new JLabel("Audio");
	private final JLabel videoEffectsLabel = new JLabel("Video");
	private JLabel videoDuration = new JLabel("00:00");
	private JLabel mp3Duration = new JLabel("00:00");
	private final JLabel lblVolume = new JLabel("Volume");
	private final JLabel lblTempo = new JLabel("Tempo");
	private final JLabel lblEcho = new JLabel("Echo");
	private final JLabel lblFps = new JLabel("FPS");
	private final JLabel lblTranspose = new JLabel("Transpose");
	private final JLabel lblStrip = new JLabel("Strip");
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
	
	//J Progress bar
	private JProgressBar progressBar = new JProgressBar();
	//File Chooser and Directory chooser
	private JFileChooser chooser = null;
	
	//comboboxes
	private JComboBox<String> voiceOptions = new JComboBox<String>();
	private final JComboBox<String> extensions = new JComboBox<String>();
	
	//Checkboxes
	private JCheckBox echoCheckBox = new JCheckBox("");
	private JCheckBox negateCheckBox = new JCheckBox("");
	private JCheckBox stripCheckBox = new JCheckBox("");
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
	private MessageFrame mf = null;

	//video player components
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	private final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
	
	//ArrayList
	private ArrayList<String> mp3FilesPaths= new ArrayList<String>();
	
	//Strings
	private final String[] pitchRange={"50 hz", "60 hz", "70 hz", "80 hz", "90 hz", "100 hz", "110 hz", "120 hz", "130 hz", "140 hz", 
							"150 hz", "160 hz", "170 hz", "180 hz", "190 hz", "200 hz", "210 hz", "220 hz", "230 hz", "240 hz", "250 hz"};
	private String videoTitle = null;
	private String projectPath=null;
	//Table with scrollPane
	private JScrollPane scroll;
	private DefaultTableModel model= new DefaultTableModel();
	private Object[] collumns= {"Mp3 files", "Duration","Start time"};
	
	
	/**
	 * Create the frame.
	 */
	public MediaPlayer(String path) {
		setProjectPath(path);
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
		time.setBounds(360, 10, 70, 20);
		time.setFont(new Font("Time New Roman", Font.PLAIN, 15));
		time.setText("00:00");
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setForeground(Color.BLACK);
		controls.add(time);
		
		//Slash
		slash.setBounds(425, 10, 10, 20);
		controls.add(slash);
		//video length label
		videoLength.setBounds(425, 10, 70, 20);
		videoLength.setFont(new Font("Time New Roman", Font.PLAIN, 15));
		videoLength.setText("00:00");
		videoLength.setHorizontalAlignment(SwingConstants.CENTER);
		videoLength.setForeground(Color.BLACK);
		controls.add(videoLength);

		
		// pick a video file to play
		openVideo.setBounds(40, 40, 65, 45);
		openVideo.setText("Open");
		openVideo.setFont(new Font("Time New Roman", Font.BOLD,16));
		openVideo.setForeground(Color.BLUE);
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
		volumeSlider.setBounds(650, 30, 220, 65);
		volumeSlider.setPaintTicks(true);
		volumeSlider.addChangeListener(this);
		controls.add(volumeSlider);
	
		
		//Option button
		option.setBackground(new Color(250, 250, 250));
		option.setBounds(117, 40, 70, 45);
		option.setToolTipText("Options: Change the Speed of the Video");
		controls.add(option);
		option.addActionListener(this);

		// Speech panel
		speech.setBackground(new Color(255, 255, 255));
		speech.setBounds(0, 700, 880, 140);
		contentPane.add(speech);
		speech.setLayout(null);
		
		
		//Voice label
		voicelbl.setFont(new Font("Time New Roman", Font.PLAIN, 16));
		voicelbl.setBounds(190, 15, 50, 25);
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
		text.setText("Enter text here");
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
		statuslbl.setBounds(240, 100, 400, 15);
		statuslbl.setFont(new Font("Time New Roman", Font.PLAIN,13));
		statuslbl.setForeground(new Color(0, 0, 0));
		statuslbl.setVisible(true);
		speech.add(statuslbl);
		
		// Create button opens new frame and let user create a video file
		create.setBackground(new Color(0, 0, 0));
		create.setBounds(780, 60, 65, 40);
		create.setToolTipText("Hide/Show the merge audio and video panel");
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
		voiceOptions.setModel(new DefaultComboBoxModel(new String[] {"Kal", "Auckland","Rab"}));
		voiceOptions.setBounds(245, 15, 130, 25);
		voiceOptions.setToolTipText("select voice for speech");
		speech.add(voiceOptions);
		create.addActionListener(this);
		
		//JProgressBar
		progressBar.setMaximum(500);
		progressBar.setBounds(0,120,880,15);
		progressBar.setForeground(new Color(255,255,255));
		speech.add(progressBar);
		
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
		videoBrowse.setToolTipText("Look for a video file");
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
		mp3Browse.setToolTipText("Look for a mp3 file");
		filesPanel.add(mp3Browse);
		mp3Browse.addActionListener(this);
		
		//add the mp3 file to the table button
		addToTable.setBounds(346, 150, 75, 25);
		addToTable.setIcon(addIcon);
		addToTable.setToolTipText("Add the mp3 file to the table below");
		filesPanel.add(addToTable);
		addToTable.addActionListener(this);
		addToTable.setEnabled(false);
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
		
	
		//volume of mp3 label
		lblVolume.setBounds(12, 40, 70, 16);
		audioEffects.add(lblVolume);
		
		//set up volume spinner
		volumeSpnr.setBounds(121, 37, 61, 22);
		volumeSpnr.setToolTipText("Scale the audio volume");
		audioEffects.add(volumeSpnr);
		
		//Tempo of mp3 / speed
		lblTempo.setBounds(12, 83, 70, 16);
		audioEffects.add(lblTempo);
		
		//set up tempo spinner
		tempoSpnr.setBounds(121, 80, 61, 22);
		tempoSpnr.setToolTipText("Scale the audio tempo");
		audioEffects.add(tempoSpnr);
		
		//Echo label
		lblEcho.setBounds(12, 128, 70, 16);
		audioEffects.add(lblEcho);
		
		// set up the echo checkbox
		echoCheckBox.setBounds(139, 124, 25, 25);
		echoCheckBox.setToolTipText("Echo effect for the audio");
		audioEffects.add(echoCheckBox);
		
		// set up the Video effects panel
		videoEffects.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		videoEffects.setBackground(new Color(255, 255, 255));
		videoEffects.setBounds(226, 225, 224, 225);
		videoEffects.setLayout(null);
		mergeAudio.add(videoEffects);
		
		// Video effect panel title
		videoEffectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		videoEffectsLabel.setBounds(77, 5, 70, 16);
		videoEffects.add(videoEffectsLabel);
		
		//Frame per second label 
		lblFps.setBounds(12, 41, 70, 16);
		videoEffects.add(lblFps);
		
		//set up frame per second spinner
		fpsSpnr.setBounds(128, 38, 61, 22);
		fpsSpnr.setToolTipText("Frame rate for the new video");
		videoEffects.add(fpsSpnr);
		
		//video transpose label
		lblTranspose.setBounds(12, 79, 80, 16);
		videoEffects.add(lblTranspose);
		
		//video transpose Spinner
		transposeSpnr.setBounds(128,78,61,22);
		transposeSpnr.setToolTipText("Rotate the video by specified degree");
		videoEffects.add(transposeSpnr);
		
		//video strip label
		lblStrip.setBounds(12, 120, 70, 16);
		videoEffects.add(lblStrip);
		
		//strip audio check box
		stripCheckBox.setBounds(150, 121, 25, 25);
		stripCheckBox.setToolTipText("Strip the audio from the original video");
		videoEffects.add(stripCheckBox);
		
		//negate video color label
		lblNegate.setBounds(12, 165, 70, 16);
		videoEffects.add(lblNegate);
		
		//Negate the color of the video checkbox
		negateCheckBox.setBounds(150, 161, 25, 25);
		negateCheckBox.setToolTipText("Change the color intensities to opposites");
		videoEffects.add(negateCheckBox);
		
		//set up effects label
		effectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		effectsLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		effectsLabel.setBounds(145, 195, 146, 27);
		mergeAudio.add(effectsLabel);
		
		//Set up enable effects button
		enableEffects.setBounds(10,200,90,25);
		enableEffects.setToolTipText("Enable/Disable the effects tools");
		enableEffects.addActionListener(this);
		mergeAudio.add(enableEffects);
		
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
		//Set the current project path
		saveDirectory.setText(projectPath);
		
		//Browse for directory button
		dirBrowse.setBounds(330, 211, 75, 25);
		dirBrowse.setToolTipText("Look for a directory");
		confirmPanel.add(dirBrowse);
		dirBrowse.addActionListener(this);
		
		//file extensions combo box
		extensions.setModel(new DefaultComboBoxModel(new String[] {".avi"}));
		extensions.setToolTipText("Choose video file extension");
		extensions.setBounds(330, 167, 75, 22);
		confirmPanel.add(extensions);
		
		//play the video when finished?
		playVideoCheckBox.setBounds(94, 243, 240, 25);
		playVideoCheckBox.setToolTipText("Play the video right when its created");
		confirmPanel.add(playVideoCheckBox);
		
		//set up confirm button
		confirm.setBounds(120, 290, 200, 45);
		confirm.setIcon(tickIcon);
		confirm.setToolTipText("Start merging the mp3 files and the video file");
		confirmPanel.add(confirm);
		confirm.addActionListener(this);
	
		//table of time points
		model.setColumnIdentifiers(collumns);
		table.setModel(model);
		table.setToolTipText("Mp3 files getting prepared for merge operation");
		scroll=new JScrollPane(table);
		scroll.setBounds(25, 10, 400, 110);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		confirmPanel.add(scroll);
		
		//Play the selected mp3 file button
		playMp3Files.setBounds(25, 125, 200, 25);
		playMp3Files.setToolTipText("Play selected mp3 files from table");
		playMp3Files.addActionListener(this);
		confirmPanel.add(playMp3Files);
		
		//Delete mp3 file from table mp3 file button
		deleteMp3Files.setBounds(225, 125, 200, 25);
		deleteMp3Files.setToolTipText("Delete selected mp3 files from table");
		deleteMp3Files.addActionListener(this);
		confirmPanel.add(deleteMp3Files);
		
		// set Frame
		setIconImage(Toolkit.getDefaultToolkit().getImage(MediaPlayer.class.getResource("/javagui/resources/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(400, 100, 1330, 830);
		setResizable(false);
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
		timeCalculator();
		
		if(!mp3Filetf.getText().equals("")){
			addToTable.setEnabled(true);
		}
		//Set video length
		if( videoFiletf.getText().equals(videoTitle)){
			videoLength.setText(videoDuration.getText());
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
			videoFileChooser(openVideo);
		} else if (e.getSource()==option){	   // Option frame, choose Rate and mode
			if(op!=null){
				op.setLocation(getX()+185,getY()+603);
				op.setVisible(true);
			}else{
				op= new OptionsFrame(getX()+185,getY()+603);
				op.setMediaPlayer(this);
			}
		} else if (e.getSource() == play) { 	// Play the video
			if (videoTitle == null) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame(getX()+558,getY()+478,"Error", "ERROR 4",
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
				String[] pitch= spinnerTranslate();
				String voice=(String)voiceOptions.getSelectedItem();
				if (voice.equals("Kal")){
					voice="kal_diphone";
				}else if(voice.equals("Rab")){
					voice="rab_diphone";
				}else{
					voice="akl_nz_jdt_diphone";
				}
				bg = new BackgroundVoice(text.getText(),this,voice, 
						(double)rateSpnr.getValue(), Integer.parseInt(pitch[0]),Integer.parseInt(pitch[1]));
				bg.execute();
				speak.setIcon(null);
				speak.setText("Cancel");
			}
		} else if (e.getSource() == save) {			// Save the synthetic speech in an mp3 file
			if (wordCount() > 30) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame(getX()+558,getY()+478,"Error", "ERROR 7", "Number of words: "
						+ wordCount() + ". Maximum is 30.");
				mf.setVisible(true);
				return;
			} else if (text.getText().equals("")) {
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame(getX()+558,getY()+478,"Error", "ERROR 1",
						"Text field must not be empty!");
				mf.setVisible(true);
				return;
			} else if (ssf != null) {
				ssf.dispose();
			}
			ssf = new SaveSpeechFrame(getX(),getY(),projectPath);
			ssf.setVisible(true);
			String[] pitch= spinnerTranslate();
			String voice=(String)voiceOptions.getSelectedItem();
			if (voice.equals("Kal")){
				voice="kal_diphone";
			}else if(voice.equals("Rab")){
				voice="rab_diphone";
			}else{
				voice="akl_nz_jdt_diphone";
			}
			ssf.setSyntheticSpeechAttributes(text.getText(),statuslbl,progressBar,voice, 
					(double)rateSpnr.getValue(), Integer.parseInt(pitch[0]),Integer.parseInt(pitch[1]));
		} else if (e.getSource() == create) {		// Open Create window with tools
			
			if(create.getText().equals("Hide")){
				mergeAudio.setVisible(false);
				this.setBounds(getX(), getY()-28, 880, 830);
				create.setText("Show");
			}else{
				mergeAudio.setVisible(true);
				this.setBounds(getX(), getY()-28, 1330, 830);
				create.setText("Hide");
			}
		} else if(e.getSource() == videoBrowse){
			videoFileChooser(videoBrowse);
		}else if(e.getSource() == mp3Browse){
			audioFileChooser();
		}else if(e.getSource() == dirBrowse){
			directoryChooser();
		}else if(e.getSource()== addToTable){
			File file= new File(mp3Filetf.getText());
			mp3FilesPaths.add(mp3Filetf.getText());
			Object[] row = new Object[3];
			row[0]=file.getName();
			row[1]=mp3Duration.getText();
			row[2]="00:00";
			model.addRow(row);
		}else if(e.getSource()== enableEffects){
			if(enableEffects.getText().equals("Enable")){
				enableEffects();
				enableEffects.setText("Disable");
			}else{
				disableEffects();
				enableEffects.setText("Enable");
			}
		}else if(e.getSource() == confirm){
			//Get the index of selected rows
			int[] selectedRows=table.getSelectedRows();
			ArrayList<String> mp3Files= new ArrayList<String>();
			ArrayList<String> startTimes= new ArrayList<String>();
			for(int i: selectedRows){
				//Get the name of mp3 files from selected row
				mp3Files.add(mp3FilesPaths.get(i));
				startTimes.add((String)table.getValueAt(i, 2));
			}
			//Check if user have selected at least 1 video file
			if(mp3Files.isEmpty()){
				if (mf != null) {
					mf.dispose();
				}
				mf = new MessageFrame(getX()+558,getY()+478,"Error", "ERROR 4",
						"No mp3 file have been selected!");
				mf.setVisible(true);
				return;
			}
			//Check if the inputs are valid
			InvalidFilesCheck ifc1= new InvalidFilesCheck(videoFiletf.getText(),saveDirectory.getText()
					+ System.getProperty("file.separator")+ newFileName.getText() + ".avi", saveDirectory.getText());
			ifc1.setCoordinates(getX()+558,getY()+478);
			InvalidFilesCheck ifc2= new InvalidFilesCheck(mp3Files); 
			ifc2.setCoordinates(getX()+558,getY()+478);
			InvalidFilesCheck ifc3= new InvalidFilesCheck(startTimes, videoDuration.getText());
			ifc3.setCoordinates(getX()+558,getY()+478);
			if(!ifc1.ErrorChecking() || !ifc2.mp3FilesCheck() || !ifc3.startTimesCheck()){
			}else{	
				ArrayList<Object> effects=null;
				if(enableEffects.getText().equals("Disable")){
					effects= getEffects();
				}else{
					effects=null;
				}
				MergeAudioAndVideo mav = new MergeAudioAndVideo(effects,mp3Files, startTimes,
					videoFiletf.getText(), saveDirectory.getText()
					+ System.getProperty("file.separator")+ newFileName.getText() + ".avi",
					video, statuslbl, playVideoCheckBox.isSelected(),
					this);
				mav.execute();
					
			}
			
		}else if(e.getSource()== playMp3Files){
			//Get the index of selected rows
			int[] selectedRows=table.getSelectedRows();
			for(int i: selectedRows){
				//play the mp3file at each selected row
				PlayMp3Background pmb= new PlayMp3Background(mp3FilesPaths.get(i));
				pmb.execute();
			}
		}else if(e.getSource()== deleteMp3Files){
			//Get the index of selected rows
			int[] selectedRows=table.getSelectedRows();
			int j=0;
			for(int i: selectedRows){
				mp3FilesPaths.remove(i-j);
				model.removeRow(i-j);
				j++;
			}
		}
	}

	/**
	 * Change volume of the video when Jslider is moved.
	 */
	@Override
	public void stateChanged(ChangeEvent c) {
		video.setVolume(volumeSlider.getValue());
		if (volumeSlider.getValue()==0){
			volumelbl.setIcon(muteIcon);
		}else{
			volumelbl.setIcon(volumeIcon);
		}
	}
	
	/**
	 * start the video
	 */
	public void playVideo() {
		video.playMedia(videoTitle);
		video.start();
	}
	/**
	 * Calculate the video's timer from second to minute : second
	 */
	public void timeCalculator(){
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
					time.setText( "0" + minute + ":0" + second);
				} else if (second < 10) {
					time.setText( minute + ":0" + second);
				} else {
					time.setText("0" + minute + ":" + second);
				}
			}
		} else {
			second = 0;
			minute = 0;
			time.setText("00:00");
		}
	}
	
	
	// set the minute and second label to 0
	public void setTime() {
		this.second = 0;
		this.minute = 0;
	}
	
	/**
	 * Count the number of work in a string
	 * @return
	 */
	public int wordCount() {
		String speechText = text.getText().trim();
		if (speechText.isEmpty())
			return 0;
		return speechText.split("\\s+").length; // separate string around spaces
	}
	
	/**
	 * Enable control buttons when a video is selected
	 */
	public void enableButtons(){
		play.setEnabled(true);
		forward.setEnabled(true);
		backward.setEnabled(true);
	}
	
	/**
	 * Enable effects panels
	 */
	public void enableEffects(){
		volumeSpnr.setEnabled(true);
		tempoSpnr.setEnabled(true);
		echoCheckBox.setEnabled(true);
		fpsSpnr.setEnabled(true);
		transposeSpnr.setEnabled(true);
		stripCheckBox.setEnabled(true);
		negateCheckBox.setEnabled(true);
	}
	
	/**
	 * Disable effects panels
	 */
	public void disableEffects(){
		volumeSpnr.setEnabled(false);
		tempoSpnr.setEnabled(false);
		echoCheckBox.setEnabled(false);
		fpsSpnr.setEnabled(false);
		transposeSpnr.setEnabled(false);
		stripCheckBox.setEnabled(false);
		negateCheckBox.setEnabled(false);
	}
	
	/**
	 * Get all effects from the effect panel
	 * @return
	 */
	public ArrayList<Object> getEffects(){
		ArrayList<Object> effects= new ArrayList<Object>();
		effects.add(volumeSpnr.getValue());
		effects.add(tempoSpnr.getValue());
		effects.add(echoCheckBox.isSelected());
		effects.add(fpsSpnr.getValue());
		int transposeDeg=(Integer)transposeSpnr.getValue();
		if(transposeDeg==0){
			effects.add(new Integer(4));
		}else if(transposeDeg==90){
			effects.add(new Integer(1));
		}else if(transposeDeg==180){
			effects.add(new Integer(5));
		}else{
			effects.add(new Integer(0));
		}
		effects.add(stripCheckBox.isSelected());
		effects.add(negateCheckBox.isSelected());
		return effects;
		
	}
	
	/**
	 * Convert the spinners values from non-numeric to numeric.
	 * @return
	 */
	private String[] spinnerTranslate(){
		String[] pitch= new String[2];
		String start= (String) startPitchSpnr.getValue();
		pitch[0]= start.substring(0, 3).trim();
		String end= (String) endPitchSpnr.getValue();
		pitch[1]= end.substring(0, 3).trim();
		return pitch;
	}
	
	/**
	 * Initialise filechooser when User click the open button or browse buttons
	 */
	public void videoFileChooser( JButton clickedButton){
		if (chooser == null) {
			chooser = new JFileChooser();
			FileNameExtensionFilter filter;
			filter = new FileNameExtensionFilter("Video File", "avi", "mp4", "mkv");
			chooser.setDialogTitle("Choose a Video File");
			chooser.setCurrentDirectory(new java.io.File(projectPath));
			chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
			chooser.setAcceptAllFileFilterUsed(false);
			
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String videoFile=chooser.getSelectedFile().toString();
				GetMediaFileDurationTask durationTask;
				if(clickedButton.equals(openVideo)){
					setVideoTitle(videoFile);
					playVideo();
					enableButtons();
					addCurrentVideo(videoTitle);
					durationTask= new GetMediaFileDurationTask(videoFile,videoDuration);
					durationTask.execute();
				}else if(clickedButton.equals(videoBrowse)){
					 videoFiletf.setText(chooser.getSelectedFile().toString());
					 durationTask= new GetMediaFileDurationTask(videoFile,videoDuration);
					 durationTask.execute();
				}
				durationTask=null;
			}
			chooser = null;
		}
	}
	
	/**
	 * Audio file chooser
	 */
	public void audioFileChooser(){
		if (chooser == null) {
			chooser = new JFileChooser();
			FileNameExtensionFilter filter= new FileNameExtensionFilter("Audio File", "mp3");
			chooser.setDialogTitle("Choose an Audito File");
			chooser.setCurrentDirectory(new java.io.File(projectPath));
			chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String audioFile=chooser.getSelectedFile().toString();
				GetMediaFileDurationTask durationTask= new GetMediaFileDurationTask(audioFile,mp3Duration);
				durationTask.execute();
				mp3Filetf.setText(chooser.getSelectedFile().toString());
				durationTask=null;
			}
			chooser=null;
		}
		
	}
	/**
	 * Initialise directory chooser when User click the browse buttons
	 */
	public void directoryChooser() {
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File(projectPath));
			chooser.setDialogTitle("Find Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				saveDirectory.setText(chooser.getSelectedFile().toString());
			}
			chooser = null;
		}
	}
	
	/*
	 * Getters and setters
	 */
	public void setProjectPath(String path){ this.projectPath=path;}
	public String getProjectPath(){ return this.projectPath;}
	public void setVideoTitle(String title) {this.videoTitle = title;}
	public String getVideoTitle() {return this.videoTitle;}
	public String getTextMessage() {return text.getText();}
	public EmbeddedMediaPlayer getVideo() {return this.video;}
	public JLabel getStatuslbl() {return this.statuslbl;}
	public JButton getSpeakButton(){return this.speak;}
	public ImageIcon getSpeakIcon(){return this.speakIcon;}
	public JLabel getVideoDurationLabel(){ return this.videoLength;}
	public JProgressBar getProgressBar(){return this.progressBar;}
	//set the video file textfield with the name of the current playing file
	public void addCurrentVideo(String videoFile) {videoFiletf.setText(videoFile);}
}