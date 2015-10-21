package generic_frames;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
/*
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import mainview.MediaPlayer;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
*/
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import mainview.MediaPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MessageFrame extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame = this;
	private JLabel errorLabel = new JLabel();
	private final ImageIcon ErrorIcon1 = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/Error1.png"));
	private final ImageIcon ErrorIcon2 = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/Error2.png"));
	/*
	private AudioPlayer audioPlayer= AudioPlayer.player;
	private AudioStream inputStream;
	private AudioData data;
	private AudioDataStream stream=null;
	private ContinuousAudioDataStream loop=null;
	*/
	//private URL url= MessageFrame.class.getClassLoader().getResource("notification.wav");
	//private AudioClip notification= Applet.newAudioClip(url);
	
	/*
	 * ERROR 1 :blank fields
	 * ERROR 2 :illegal file- file does not exist a) video file b) mp3 file
	 * ERROR 3 :illegal file- file is a directory a) video file b) mp3 file
	 * ERROR 4 :no file has been selected.
	 * ERROR 5 :try to create an exist file
	 * ERROR 6 :this is not a directory
	 */
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageFrame window = new MessageFrame("ERROR","ERROR 1","PLease fill in the blank fields");
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
	public MessageFrame() {
		setBounds(100, 100, 450, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255,255,255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//notification.play();
		
		JLabel title = new JLabel("Title");
		title.setFont(new Font("Dialog", Font.BOLD, 25));
		title.setBounds(12, 12, 250, 60);
		contentPane.add(title);
		
		JLabel message = new JLabel("Message");
		message.setBounds(12, 84, 426, 28);
		contentPane.add(message);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.dispose();
			}
		});
		btnNewButton.setBounds(166, 124, 117, 25);
		contentPane.add(btnNewButton);
	}
	/**
	 * Constructor which allows you to construct simple user prompts 
	 * 
	 * @param frameTitle	title of the frame
	 * @param messageTitle	title of the message
	 * @param messageText	the message to the user
	 */
	public MessageFrame(String frameTitle, String messageTitle, String messageText) {
		//notification();
		setBounds(658, 605, 320, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		thisFrame.setTitle(frameTitle);
		thisFrame.setUndecorated(true);
		thisFrame.setOpacity(0.6F);
		thisFrame.setBackground(new Color(255,255,255));
		contentPane.setBackground(new Color(255,255,255));
		contentPane.setLayout(null);
		
		// title of the message
		errorLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		errorLabel.setBounds(10, 5, 200, 60);
		errorLabel.setIcon(ErrorIcon1);
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(messageTitle);
		contentPane.add(errorLabel);
		//message to give to user
		JLabel message = new JLabel(messageText);
		message.setBounds(10, 50, 300, 30);
		contentPane.add(message);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//audioPlayer.stop(loop);
				thisFrame.dispose();
			}
		});
		okButton.setBounds(110, 90, 100, 25);
		contentPane.add(okButton);
	}
	
	public String getErrorTile(){
		return this.errorLabel.getText();
	}
	
	/*//Play the notification sound
	public void notification(){
		try{
			inputStream = new AudioStream(new FileInputStream(".notification1.wav"));
			data= inputStream.getData();
			stream=	new AudioDataStream(data); 
			//loop= new ContinuousAudioDataStream(data);
			audioPlayer.start(stream);
			//audioPlayer.start(loop);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	*/
}
