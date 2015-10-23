package genericframes.helperframes;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import mediaplayer.MediaPlayer;

/**
 * Options frame open a new window and let user choose the speed of the video
 */
public class OptionsFrame extends JFrame implements ActionListener {

	
	private JPanel contentPane= new JPanel();
	private JLabel speedlbl = new JLabel("Speed");
	private JButton halfRatebtn = new JButton("0.5");
	private JButton normalRatebtn = new JButton("1");
	private JButton doubleRatebtn = new JButton("2");
	private JButton donebtn = new JButton("Done");
	private MediaPlayer mediaPlayer=null;
	
	
	/**
	 * Constructor create the Option Frame
	 * @param x
	 * @param y
	 */
	public OptionsFrame(int x, int y) {
		//contentPane
		contentPane.setBackground(new Color(250, 250, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		
		// Speed label
		speedlbl.setFont(new Font("Time New Roman", Font.BOLD, 13));
		speedlbl.setHorizontalAlignment(SwingConstants.CENTER);
		speedlbl.setForeground(new Color(0, 0, 0));
		speedlbl.setBackground(new Color(255, 255, 255));
		speedlbl.setBounds(10, 10, 50, 20);
		contentPane.add(speedlbl);
		
		// Play the video at half rate
		halfRatebtn.setBackground(new Color(200, 200, 200));
		halfRatebtn.setBounds(100, 8, 55, 30);
		contentPane.add(halfRatebtn);
		halfRatebtn.addActionListener(this);
		
		//Play the video at normal rate
		normalRatebtn.setBackground(new Color(200, 200, 200));
		normalRatebtn.setBounds(172, 8, 55, 30);
		contentPane.add(normalRatebtn);
		normalRatebtn.addActionListener(this);
		
		// Play the video at 2 time the normal rate
		doubleRatebtn.setBackground(new Color(200, 200, 200));
		doubleRatebtn.setBounds(245, 8, 55, 30);
		contentPane.add(doubleRatebtn);
		doubleRatebtn.addActionListener(this);
		
		// Done button
		donebtn.setBackground(new Color(200, 200, 200));
		donebtn.setBounds(220, 45, 80, 30);
		contentPane.add(donebtn);
		donebtn.addActionListener(this);
		
		//Set up the Frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 320, 80);
		setUndecorated(true);
		setOpacity(0.8F);
		setContentPane(contentPane);
		setResizable(false);
		setVisible(true);
	}
	/**
	 * Set the rate of the video when user clicked button on the frame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== halfRatebtn){
			mediaPlayer.getVideo().setRate((float)0.5);
		}else if(e.getSource()== normalRatebtn){
			mediaPlayer.getVideo().setRate(1);
		}else if(e.getSource()== doubleRatebtn){
			mediaPlayer.getVideo().setRate(2);
		}else{
			this.dispose();
		}
		
	}
	
	/**
	 * set the local media player
	 * @param mediaPlayer
	 */
	public void setMediaPlayer(MediaPlayer mediaPlayer){
		this.mediaPlayer= mediaPlayer;
	}
	
}
