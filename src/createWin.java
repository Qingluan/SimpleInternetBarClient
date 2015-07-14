import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class createWin  implements ActionListener{

	public JFrame frame;
	private JTextField textField;
	private  int count = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createWin window = new createWin();
					window.frame.setBounds(200, 300, 500, 60);
					window.frame.setResizable(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public int getHostCount(){
		return this.count;
	}

	/**
	 * Create the application.
	 */
	public createWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		splitPane.setLeftComponent(textField);
		textField.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(this);
		splitPane.setRightComponent(btnCreate);
	}
	
	public void destroy(){
		this.frame.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String res = textField.getText().trim();
		destroy();
		count = Integer.valueOf(res);
		CoffeeBarWin.hostNum = count;
		
		

	}

}
