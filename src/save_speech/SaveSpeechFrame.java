package save_speech;

import generic_frames.BrowseFileFrame;
import generic_frames.MessageFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveSpeechFrame extends JFrame implements ActionListener, WindowListener{
	/**
	 * SaveSpeechFrame let user enter a name for the mp3 file and also select the directory to save the mp3 file to.
	 */
	private static String fileName="";
	private static String folder="";
	private JPanel contentPane= new JPanel();;
	
	private JTextField nameOfFile= new JTextField();
	private JButton save = new JButton("Save");
	private JButton cancel = new JButton("Cancel");
	private JButton browse = new JButton("Browse");
	
	private JLabel nameLabel=new JLabel("File Name");
	private JLabel mp3 = new JLabel(".mp3");
	private JLabel directory = new JLabel("Save to");
	
	private String message;
	private String voice;
	private double rate;
	private int pitchStart;
	private int pitchEnd;
	
	
	private JFrame thisFrame = this;
	private JTextField saveTo= new JTextField();
	private MessageFrame mf = null;
	JFileChooser chooser = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveSpeechFrame window = new SaveSpeechFrame();
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
	public SaveSpeechFrame() {
		addWindowListener(this);
		setTitle("Save Synthetic Speech");
		setBounds(290, 725, 510, 180);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255,255,255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Name of File label
		nameLabel.setBounds(10, 40, 100, 25);
		contentPane.add(nameLabel);
		
		// text field where user names the new file
		nameOfFile.setBounds(110, 30, 296, 33);
		nameOfFile.setText(fileName);
		contentPane.add(nameOfFile);
		nameOfFile.setColumns(10);
		
		//mp3 label
		
		mp3.setBounds(410, 40, 70, 25);
		contentPane.add(mp3);
		
		//Directory label:
		directory.setBounds(10, 100, 100, 25);
		contentPane.add(directory);
		
		// directory textfield
		saveTo.setBounds(110, 90, 296, 33);
		saveTo.setText(folder);
		contentPane.add(saveTo);
		saveTo.setColumns(10);
		// defaults the save path to current directory
		try {
			if(folder.equals("")){
				saveTo.setText(new java.io.File(".").getCanonicalPath());
			}else{
				saveTo.setText(folder);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//Browse button let user browse for a directory to save the file
		browse.addActionListener(this);
		browse.setBounds(410, 95, 90, 25);
		contentPane.add(browse);
		
		//Save button, save the file to the selected directory
		save.addActionListener(this);
		save.setBounds(100, 140, 110, 25);
		contentPane.add(save);
		
		//cancel button
		cancel.addActionListener(this);
		cancel.setBounds(300, 140, 110, 25);
		contentPane.add(cancel);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==browse){
			if (chooser == null){
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Find Folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					saveTo.setText(chooser.getSelectedFile().toString());		
				}
				chooser = null;
			}	
		}else if(e.getSource()== save){
			saveFile();
		}else if(e.getSource()==cancel){
			nameOfFile.setText("");
			saveTo.setText("");
			thisFrame.dispose();
		}
		
	}
	//save to the file to selected folder
	public void saveFile(){
		// the files name
		String fileName = nameOfFile.getText();
		String folderName = saveTo.getText();
		String pattern = "^[a-zA-Z0-9_]*$";
		String seperator = System.getProperty("file.separator");
					
		//checks if file name is valid
		if (!fileName.matches(pattern)){
			if (mf != null){
				mf.dispose();
			}
			mf = new MessageFrame("Error", "Error:", "Invalid Name!");
			mf.setVisible(true);
			return;
		}else if (fileName.equals("")){
			if (mf != null){
				mf.dispose();
			}
			mf = new MessageFrame("Error", "Error:", "No Name!");
			mf.setVisible(true);
			return;
		}
		// makes sure the file is a .mp3 file
		fileName = fileName + ".mp3";
					
		if(!folderName.equals("")){
			fileName = folderName + seperator + fileName;
		}
		// used to check if the file exists
					
		File tmpFile = new File(fileName);
		File tmpDir = new File(folderName);
					
		// checks if file already exists
		if (tmpFile.exists() && !tmpFile.isDirectory()){
			if(mf != null){
				mf.dispose();
			}
			MessageFrame mf = new MessageFrame("Error", "ERROR 3:", "File Already Exists!");
			mf.setVisible(true);
			return;
		}else if (!tmpDir.exists()){
			if(mf != null){
				mf.dispose();
			}
			MessageFrame mf = new MessageFrame("Error", "ERROR 4", "Folder does not Exists!");
			mf.setVisible(true);
			return;
		}
					
		// Creates the wave file the user requests
		SaveSpeech ss = new SaveSpeech(message, fileName, voice, rate, pitchStart, pitchEnd);
		ss.execute();
		thisFrame.dispose();
	}
	
	public void setSyntheticSpeechAttributes(String message, String voice, double rate, int pitchStart, int pitchEnd){
		this.message = message;
		this.voice=voice;
		this.rate=rate;
		this.pitchStart=pitchStart;
		this.pitchEnd=pitchEnd;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		fileName= nameOfFile.getText();
		folder=saveTo.getText();
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
