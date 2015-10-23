package genericframes.launcher;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import swingworkers.filesrehandlers.GlobalProjectFolder;
import mediaplayer.MediaPlayer;

/**
 * ProjectSelectFrame  is  the launcher which asks user for a project directory.
 * User can create non-exist directory or select an exist one.
 */
public class ProjectSelectFrame extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	private JTextField directory;
	private JLabel messagelbl;
	private JLabel welcomelbl;
	private JLabel vidivoxlbl;
	private JButton browse;
	private JFileChooser chooser;
	private JButton confirm;
	private JLabel gifLabel;
	private String projectPath;
	
	//.gif file was extracted and scaled from sintel.mkv from open online source.
	private final Icon intro = new ImageIcon(
			MediaPlayer.class.getResource("/javagui/resources/intro.gif"));
	
	
	/**
	 * Constructor
	 */
	public ProjectSelectFrame(){
		//Set up Jframe
		setTitle("VIDIVOX");
		setBounds(600, 450, 450, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		//ContentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255,255,255));
		contentPane.setBackground(new Color(50,50,50));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Welcome Label
		welcomelbl= new JLabel();
		welcomelbl.setText("Welcome To");
		welcomelbl.setFont(new Font("Arial", Font.BOLD, 24));
		welcomelbl.setForeground(new Color(255,255,255));
		welcomelbl.setBounds(30, 10, 180, 40);
		contentPane.add(welcomelbl);
		
		//VidiVox label
		vidivoxlbl= new JLabel("ViDiVOX");
		vidivoxlbl.setFont(new Font("Arial", Font.BOLD, 36));
		vidivoxlbl.setForeground(Color.BLUE);
		vidivoxlbl.setBounds(210, 10 , 170 ,40);
		contentPane.add(vidivoxlbl);
		
		//Get the home directory of user
		String homeDirectory= System.getProperty("user.home");
		
		//Directory
		directory= new JTextField();
		directory.setText(homeDirectory+"/Throwable_dpha010");
		directory.setBounds(20,80, 300, 30);
		contentPane.add(directory);
		
		//Message label
		messagelbl=new JLabel();
		messagelbl.setBounds(10,50,400, 30);
		messagelbl.setForeground(new Color(100,100,100));
		messagelbl.setText("Select or create a project directory to start: ");
		contentPane.add(messagelbl);
		
		//Browse button
		browse=new JButton();
		browse.setText("Browse");
		browse.setBounds(340, 80, 90, 30);
		browse.setBackground(new Color(160,160,160));
		contentPane.add(browse);
		browse.addActionListener(this);
		
		//Confirm button
		confirm = new JButton("Confirm");
		confirm.setBounds(180, 120 , 90 ,30 );
		confirm.setToolTipText("Create the direcotory if not exist and set it as project directory");
		confirm.setBackground(new Color(160,160,160));
		contentPane.add(confirm);
		confirm.addActionListener(this);
		
		//GifLabel
		gifLabel= new JLabel(intro);
		gifLabel.setBounds(0, 0, 450, 250 );
		contentPane.add(gifLabel);
		
	}
	/**
	 * Action listeners perform tasks when user clicked buttons on the window 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == browse){
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
				chooser.setDialogTitle("Find Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					directory.setText(chooser.getSelectedFile().toString());
				}
				chooser = null;
			}
		}else if (e.getSource() == confirm){
			
			//Check if directory exists, if not ask user permission to create that directory
			File file= new File(directory.getText());
			if(!file.isDirectory()){
				if (JOptionPane.showConfirmDialog(this,"The directory does not exist yet! Do you want to create it?", "Directory not Found",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					GlobalProjectFolder folder= new GlobalProjectFolder(directory.getText());
					folder.execute();
				} else {
					return;
				}
			}
			//Set the project directory and close the launcher frame
			projectPath= directory.getText();
			this.dispose();
			
			//set look and feel feature for the GUI
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
			// Open media player when the project folder is ready
			MediaPlayer mediaPlayer= new MediaPlayer(projectPath);
			mediaPlayer.setVisible(true);
		}
	}
	
	/**
	 * Get the project folder path
	 */
	public String getDirectoryPath(){
		return this.projectPath;
	}
	
}
