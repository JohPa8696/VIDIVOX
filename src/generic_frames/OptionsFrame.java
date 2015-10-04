package generic_frames;

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

import mainview.MediaPlayer;

public class OptionsFrame extends JFrame implements ActionListener {

	private JPanel contentPane= new JPanel();
	private JLabel sizelbl = new JLabel("Size");
	private JButton doubleScreenbtn = new JButton("Double");
	private JButton fullScreenbtn = new JButton("Full");
	private JLabel speedlbl = new JLabel("Speed");
	private JButton halfRatebtn = new JButton("0.5");
	private JButton normalRatebtn = new JButton("1");
	private JButton doubleRatebtn = new JButton("2");
	private JButton donebtn = new JButton("Done");
	private MediaPlayer mediaPlayer=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionsFrame window = new OptionsFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public OptionsFrame() {
		//contentPane
		contentPane.setBackground(new Color(250, 250, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		
		//size label
		sizelbl.setFont(new Font("Time New Roman", Font.BOLD, 13));
		sizelbl.setHorizontalAlignment(SwingConstants.CENTER);
		sizelbl.setForeground(new Color(0, 0, 0));
		sizelbl.setBackground(new Color(255, 255, 255));
		sizelbl.setBounds(10, 10, 50, 20);
		contentPane.add(sizelbl);
		
		// Double the screen size button
		doubleScreenbtn.setBackground(new Color(200, 200, 200));
		doubleScreenbtn.setBounds(100, 8, 80, 30);
		contentPane.add(doubleScreenbtn);
		doubleScreenbtn.addActionListener(this);
		
		// Set window size to full screen
		fullScreenbtn.setBackground(new Color(200, 200, 200));
		fullScreenbtn.setBounds(220, 8, 80, 30);
		contentPane.add(fullScreenbtn);
		fullScreenbtn.addActionListener(this);
		
		// Speed label
		speedlbl.setFont(new Font("Time New Roman", Font.BOLD, 13));
		speedlbl.setHorizontalAlignment(SwingConstants.CENTER);
		speedlbl.setForeground(new Color(0, 0, 0));
		speedlbl.setBackground(new Color(255, 255, 255));
		speedlbl.setBounds(10, 50, 50, 20);
		contentPane.add(speedlbl);
		
		// Play the video at half rate
		halfRatebtn.setBackground(new Color(200, 200, 200));
		halfRatebtn.setBounds(100, 48, 55, 30);
		contentPane.add(halfRatebtn);
		halfRatebtn.addActionListener(this);
		
		//Play the video at normal rate
		normalRatebtn.setBackground(new Color(200, 200, 200));
		normalRatebtn.setBounds(172, 48, 55, 30);
		contentPane.add(normalRatebtn);
		normalRatebtn.addActionListener(this);
		
		// Play the video at 2 time the normal rate
		doubleRatebtn.setBackground(new Color(200, 200, 200));
		doubleRatebtn.setBounds(245, 48, 55, 30);
		contentPane.add(doubleRatebtn);
		doubleRatebtn.addActionListener(this);
		
		// Done button
		donebtn.setBackground(new Color(200, 200, 200));
		donebtn.setBounds(220, 85, 80, 30);
		contentPane.add(donebtn);
		donebtn.addActionListener(this);
		
		//Set up the Frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(220, 730, 320, 120);
		setUndecorated(true);
		setOpacity(0.8F);
		setContentPane(contentPane);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== doubleScreenbtn){
			
		}else if(e.getSource()== fullScreenbtn){
			
		}else if(e.getSource()== halfRatebtn){
			mediaPlayer.getVideo().setRate((float)0.5);
		}else if(e.getSource()== normalRatebtn){
			mediaPlayer.getVideo().setRate(1);
		}else if(e.getSource()== doubleRatebtn){
			mediaPlayer.getVideo().setRate(2);
		}else{
			this.dispose();
		}
		
	}
	
	//Set mediaplayer
	public void setMediaPlayer(MediaPlayer mediaPlayer){
		this.mediaPlayer= mediaPlayer;
	}
	
}
