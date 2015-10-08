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
		setBounds(981, 100, 450, 830);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255,255,255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// text field where user enters mp3 file
		mp3FileText = new JTextField();
		mp3FileText.setBounds(114, 99, 215, 33);
		contentPane.add(mp3FileText);
		mp3FileText.setColumns(10);

		// title of the frame
		JLabel Title = new JLabel("Add mp3 File");
		Title.setFont(new Font("Dialog", Font.BOLD, 25));
		Title.setBounds(12, 12, 328, 33);
		contentPane.add(Title);

		// label to tell user where to enter the name of the mp3 file
		JLabel mp3FileLabel = new JLabel("mp3 file:");
		mp3FileLabel.setBounds(12, 103, 111, 25);
		contentPane.add(mp3FileLabel);

		// opens JFileChooser to allow user to search for mp3 file
		JButton browseMp3File = new JButton("Browse");
		browseMp3File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser("mp3");
			}
		});
		browseMp3File.setBounds(341, 103, 87, 25);
		contentPane.add(browseMp3File);

		JButton confirm_btn = new JButton("Confirm");
		confirm_btn.addActionListener(new ActionListener() {
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
		confirm_btn.setBounds(57, 266, 117, 25);
		contentPane.add(confirm_btn);

		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
				thisFrame.dispose();
				;
			}
		});
		cancel_btn.setBounds(281, 266, 117, 25);
		contentPane.add(cancel_btn);

		// textField where user enters name of file to be created
		newFileName = new JTextField();
		newFileName.setBounds(114, 140, 215, 33);
		contentPane.add(newFileName);
		newFileName.setColumns(10);

		// label to tell user where to enter the name of new file
		JLabel newFileLabel = new JLabel("Name of file:");
		newFileLabel.setBounds(12, 149, 111, 15);
		contentPane.add(newFileLabel);

		// label to tell user where to save the new file
		saveToText = new JTextField();
		saveToText.setBounds(114, 181, 215, 33);
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
		saveToLabel.setBounds(12, 190, 70, 15);
		contentPane.add(saveToLabel);

		// opens a JFileChooser to allow the user to find a directory
		JButton browseDirectory = new JButton("Browse");
		browseDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				directoryChooser();
			}
		});
		browseDirectory.setBounds(341, 185, 87, 25);
		contentPane.add(browseDirectory);

		// checkbox which allows user to play the video after the processing
		playVideoCheck = new JCheckBox("Play video when finished");
		playVideoCheck.setBounds(114, 222, 215, 23);
		contentPane.add(playVideoCheck);

		// label to tell user where to enter the video file name
		JLabel videoFileLabel = new JLabel("Video File:");
		videoFileLabel.setBounds(12, 76, 87, 15);
		contentPane.add(videoFileLabel);

		// textfield which alllows the user to enter the video file name
		videoFileText = new JTextField();
		videoFileText.setBounds(114, 57, 215, 34);
		contentPane.add(videoFileText);
		videoFileText.setColumns(10);
		
		// opens a JFilechooser to allows user to search for a video file
		JButton browseVid_btn = new JButton("Browse");
		browseVid_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser("video");
			}
		});
		browseVid_btn.setBounds(341, 61, 87, 25);
		contentPane.add(browseVid_btn);
		
		// tells user that the file will have a .avi extension by default
		JLabel lblNewLabel = new JLabel(".avi");
		lblNewLabel.setBounds(351, 149, 70, 15);
		contentPane.add(lblNewLabel);
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
