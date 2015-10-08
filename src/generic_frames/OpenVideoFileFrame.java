package generic_frames;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JFrame;
import javax.swing.JPanel;
public class OpenVideoFileFrame extends JFrame implements ActionListener{

	private JPanel contentPane= new JPanel();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenVideoFileFrame window = new OpenVideoFileFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Constructor
	 */
	public OpenVideoFileFrame(){
		
		//Set up the Frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,580, 400, 150);
		
		setContentPane(contentPane);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
