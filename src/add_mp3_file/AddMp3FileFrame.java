package add_mp3_file;

import generic_frames.MessageFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.JCheckBox;

import mainview.MediaPlayer;
import save_speech.SaveSpeechFrame;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

public class AddMp3FileFrame extends JFrame {

	private JPanel contentPane;
	private JTextField mp3FileText;

	private JFrame thisFrame = this;
	private JFileChooser chooser = null;
	private EmbeddedMediaPlayer video = null;
	private JLabel statuslbl;
	private JTextField newFileName;
	private JTextField saveToText;
	private JCheckBox playVideoCheck;
	private JTextField videoFileText;
	private MediaPlayer mediaPlayer = null;
	private MessageFrame mf = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMp3FileFrame window = new AddMp3FileFrame();
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
	public AddMp3FileFrame() {
		setTitle("Add an mp3 file");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(981, 100, 420, 830);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255,255,255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File videoFile = new File(videoFileText.getText());
				File mp3File = new File(mp3FileText.getText());
				File newFile = new File(saveToText.getText()
						+ System.getProperty("file.separator")
						+ newFileName.getText() + ".avi");
				File checkFileName = new File(saveToText.getText()
						+ System.getProperty("file.separator")
						+ newFileName.getText());
				File directory = new File(saveToText.getText());
				String pattern = "^[a-zA-Z0-9_]*$";
				
				//checks that all fields are correct
				if (!videoFile.exists() || videoFile.isDirectory()
						|| !mp3File.exists() || mp3File.isDirectory()
						|| newFile.exists() || !directory.exists()
						|| !checkFileName.getName().matches(pattern)) {

					if (videoFileText.getText().equals("")
							|| mp3FileText.getText().equals("")
							|| newFileName.getText().equals("")
							|| saveToText.getText().equals("")) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 1",
								"Please fill in blank fields");
					} else if (!videoFile.exists()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 2a", videoFile
								.getName() + " does not exist");
					} else if (videoFile.isDirectory()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 3a", videoFile
								.getName() + " is a directory");
					} else if (mp3File.isDirectory()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 3b", mp3File
								.getName() + " is a directory");
					} else if (!mp3File.exists()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 2b", mp3File
								.getName() + " does not exist");
					} else if (!directory.exists()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 6", directory
								.toString() + " does not exist");
					} else if (newFile.exists()) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 5a", newFile
								.getName() + " already exists");
					} else if (!checkFileName.getName().matches(pattern)) {
						if (mf != null) {
							mf.dispose();
						}
						mf = new MessageFrame("Error", "ERROR 5b", newFileName
								.getText() + " is an invalid name");
					}
					mf.setVisible(true);
					return;
				} else {
					AddMp3File amf = new AddMp3File(mp3FileText.getText(),
							videoFileText.getText(), newFile.getAbsolutePath(),
							video, statuslbl, playVideoCheck.isSelected(),
							mediaPlayer);
					amf.execute();
				}
				thisFrame.setVisible(false);
				thisFrame.dispose();
			}
		});
		confirm.setBounds(66, 745, 117, 25);
		contentPane.add(confirm);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
				thisFrame.dispose();
				;
			}
		});
		cancel.setBounds(267, 745, 117, 25);
		contentPane.add(cancel);

		// textField where user enters name of file to be created
		newFileName = new JTextField();
		newFileName.setBounds(100, 610, 215, 35);
		contentPane.add(newFileName);
		newFileName.setColumns(10);

		// label to tell user where to save the new file
		saveToText = new JTextField();
		saveToText.setBounds(100, 655, 215, 35);
		contentPane.add(saveToText);
		saveToText.setColumns(10);
		try {
			saveToText.setText(new java.io.File(".").getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// label to tell user where to enter the destination of the new file
		JLabel saveToLabel = new JLabel("Save to:");
		saveToLabel.setBounds(10, 666, 80, 25);
		contentPane.add(saveToLabel);

		// opens a JFileChooser to allow the user to find a directory
		JButton dirBrowse = new JButton("Browse");
		dirBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				directoryChooser();
			}
		});
		dirBrowse.setBounds(330, 661, 80, 25);
		contentPane.add(dirBrowse);

		// checkbox which allows user to play the video after the processing
		playVideoCheck = new JCheckBox("Play video when finished");
		playVideoCheck.setBounds(100, 700, 215, 23);
		contentPane.add(playVideoCheck);
		
		JPanel filesPanel = new JPanel();
		filesPanel.setBackground(new Color(255, 255, 255));
		filesPanel.setBounds(0, 0, 433, 150);
		contentPane.add(filesPanel);
		filesPanel.setLayout(null);
		
		// opens a JFilechooser to allows user to search for a video file
		JButton vidBrowse = new JButton("Browse");
		vidBrowse.setBounds(330, 15, 80, 25);
		filesPanel.add(vidBrowse);
		
				// text field where user enters mp3 file
				mp3FileText = new JTextField();
				mp3FileText.setBounds(100, 80, 215, 33);
				filesPanel.add(mp3FileText);
				mp3FileText.setColumns(10);
				
						// textfield which alllows the user to enter the video file name
						videoFileText = new JTextField();
						videoFileText.setBounds(100, 10, 215, 30);
						filesPanel.add(videoFileText);
						videoFileText.setColumns(10);
						
								// label to tell user where to enter the video file name
								JLabel videoFileLabel = new JLabel("Video File:");
								videoFileLabel.setBounds(10, 20, 80, 25);
								filesPanel.add(videoFileLabel);
								
										// opens JFileChooser to allow user to search for mp3 file
										JButton auBrowse = new JButton("Browse");
										auBrowse.setBounds(330, 85, 80, 25);
										filesPanel.add(auBrowse);
										
												// label to tell user where to enter the name of the mp3 file
												JLabel mp3FileLabel = new JLabel("mp3 file:");
												mp3FileLabel.setBounds(10, 90, 80, 25);
												filesPanel.add(mp3FileLabel);
														
														JLabel vidLen = new JLabel("");
														vidLen.setHorizontalAlignment(SwingConstants.CENTER);
														vidLen.setBounds(150, 50, 120, 25);
														filesPanel.add(vidLen);
														
														JLabel auLen = new JLabel("");
														auLen.setFont(new Font("Tahoma", Font.PLAIN, 13));
														auLen.setHorizontalAlignment(SwingConstants.CENTER);
														auLen.setBounds(150, 122, 120, 25);
														filesPanel.add(auLen);
														
																// label to tell user where to enter the name of new file
																JLabel newFileLabel = new JLabel("Name of file:");
																newFileLabel.setBounds(10, 620, 80, 25);
																contentPane.add(newFileLabel);
																
																JPanel audioPanel = new JPanel();
																audioPanel.setBackground(new Color(255, 255, 255));
																audioPanel.setBounds(0, 175, 220, 250);
																contentPane.add(audioPanel);
																audioPanel.setLayout(null);
																
																JLabel videolbl = new JLabel("Video");
																videolbl.setHorizontalAlignment(SwingConstants.CENTER);
																videolbl.setBounds(70, 0, 80, 25);
																audioPanel.add(videolbl);
																
																JLabel lblFPS = new JLabel("FPS");
																lblFPS.setHorizontalAlignment(SwingConstants.CENTER);
																lblFPS.setBounds(0, 38, 80, 25);
																audioPanel.add(lblFPS);
																
																JLabel lblWidth = new JLabel("Width");
																lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
																lblWidth.setBounds(0, 76, 80, 25);
																audioPanel.add(lblWidth);
																
																JLabel lblHieght = new JLabel("Height");
																lblHieght.setHorizontalAlignment(SwingConstants.CENTER);
																lblHieght.setBounds(0, 114, 80, 25);
																audioPanel.add(lblHieght);
																
																JLabel lblNegate = new JLabel("Negate");
																lblNegate.setHorizontalAlignment(SwingConstants.CENTER);
																lblNegate.setBounds(0, 152, 80, 25);
																audioPanel.add(lblNegate);
																
																JSpinner fps = new JSpinner();
																fps.setModel(new SpinnerNumberModel(30, 15, 50, 5));
																fps.setBounds(140, 40, 50, 25);
																audioPanel.add(fps);
																
																JSpinner width = new JSpinner();
																width.setModel(new SpinnerNumberModel(250, 150, 400, 10));
																width.setBounds(140, 77, 50, 25);
																audioPanel.add(width);
																
																JSpinner height = new JSpinner();
																height.setModel(new SpinnerNumberModel(150, 100, 240, 10));
																height.setBounds(140, 115, 50, 25);
																audioPanel.add(height);
																
																JCheckBox negate = new JCheckBox("");
																negate.setBackground(new Color(255, 255, 255));
																negate.setBounds(150, 152, 25, 25);
																audioPanel.add(negate);
																
																JPanel videoPanel = new JPanel();
																videoPanel.setBackground(new Color(255, 255, 255));
																videoPanel.setBounds(220, 175, 213, 250);
																contentPane.add(videoPanel);
																videoPanel.setLayout(null);
																
																JLabel audiolbl = new JLabel("Audio");
																audiolbl.setHorizontalAlignment(SwingConstants.CENTER);
																audiolbl.setBounds(65, 0, 80, 25);
																videoPanel.add(audiolbl);
																
																JLabel lblBitrate = new JLabel("Bitrate");
																lblBitrate.setHorizontalAlignment(SwingConstants.CENTER);
																lblBitrate.setBounds(0, 38, 80, 25);
																videoPanel.add(lblBitrate);
																
																JLabel lblTempo = new JLabel("Tempo");
																lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
																lblTempo.setBounds(0, 76, 80, 25);
																videoPanel.add(lblTempo);
																
																JLabel lblVolume = new JLabel("Volume");
																lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
																lblVolume.setBounds(0, 119, 80, 25);
																videoPanel.add(lblVolume);
																
																JLabel lblEcho = new JLabel("Echo");
																lblEcho.setHorizontalAlignment(SwingConstants.CENTER);
																lblEcho.setBounds(0, 157, 80, 25);
																videoPanel.add(lblEcho);
																
																JSpinner birate = new JSpinner();
																birate.setBounds(128, 39, 50, 25);
																videoPanel.add(birate);
																
																JSpinner tempo = new JSpinner();
																tempo.setBounds(128, 77, 50, 25);
																videoPanel.add(tempo);
																
																JSpinner volume = new JSpinner();
																volume.setForeground(new Color(255, 255, 255));
																volume.setBounds(128, 119, 50, 25);
																videoPanel.add(volume);
																
																JCheckBox echo = new JCheckBox("");
																echo.setBackground(Color.WHITE);
																echo.setBounds(138, 157, 25, 25);
																videoPanel.add(echo);
																
																JComboBox fileExt = new JComboBox();
																fileExt.setModel(new DefaultComboBoxModel(new String[] {".avi", ".mp4", ".mkp"}));
																fileExt.setBounds(330, 616, 80, 25);
																contentPane.add(fileExt);
																
																JLabel timePointlbl = new JLabel("Time point");
																timePointlbl.setBounds(10, 440, 80, 25);
																contentPane.add(timePointlbl);
																
																JLabel lblMinute = new JLabel("Minute");
																lblMinute.setBounds(120, 440, 80, 25);
																contentPane.add(lblMinute);
																
																JSpinner minute = new JSpinner();
																minute.setModel(new SpinnerNumberModel(0, 0, 30, 1));
																minute.setBounds(170, 440, 50, 25);
																contentPane.add(minute);
																
																JSpinner second = new JSpinner();
																second.setModel(new SpinnerNumberModel(0, 0, 59, 1));
																second.setBounds(311, 440, 50, 25);
																contentPane.add(second);
																
																JLabel lblSecond = new JLabel("Second");
																lblSecond.setBounds(250, 440, 80, 25);
																contentPane.add(lblSecond);
																
																JPanel midPanel = new JPanel();
																midPanel.setBackground(new Color(255, 255, 255));
																midPanel.setBounds(0, 151, 433, 25);
																contentPane.add(midPanel);
																midPanel.setLayout(new BorderLayout(0, 0));
																
																JLabel lblNewLabel_2 = new JLabel("Effects");
																lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
																lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
																midPanel.add(lblNewLabel_2, BorderLayout.NORTH);
										auBrowse.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												fileChooser("mp3");
											}
										});
		vidBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser("video");
			}
		});
	}

	public void addVideo(EmbeddedMediaPlayer video) {
		this.video = video;
	}

	public void addStatuslbl(JLabel statuslbl) {
		this.statuslbl = statuslbl;
	}

	public void addCurrentVideo(String videoFile) {
		videoFileText.setText(videoFile);
	}

	public void addMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	// Creating file chooser, when the browse button is clicked open a browse and let use choose files 
	public void fileChooser(String location) {
		if (chooser == null) {
			chooser = new JFileChooser();
			FileNameExtensionFilter filter;
			if (location.equals("mp3")) {
				filter = new FileNameExtensionFilter("MP3 File", "mp3");
			} else {
				filter = new FileNameExtensionFilter("MP3 File", "mp4", "avi");
			}
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("choosertitle");
			chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				if (location.equals("mp3")) {
					mp3FileText.setText(chooser.getSelectedFile().toString());
				} else if (location.equals("video")) {
					videoFileText.setText(chooser.getSelectedFile().toString());
				}
			}
			chooser = null;
		}
	}
	// Create a directory chooser, open a window and let user choose a directory
	public void directoryChooser() {
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Find Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				saveToText.setText(chooser.getSelectedFile().toString());
			}
			chooser = null;
		}
	}
}