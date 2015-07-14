import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.ScrollPaneLayout;

import Tools.L;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Time;


public class CoffeeBarWin  {

	private JFrame frame;
	private JPanel panel;
	public static int hostNum;
	public JPanel Hostpanel;
	private JScrollPane panelTop; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoffeeBarWin window = new CoffeeBarWin();
					
					window.frame.setBounds(new Rectangle(370, 100, 600, 600));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CoffeeBarWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		

		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnCreateHost = new JButton("create Host");
		btnCreateHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				EventQueue.invokeLater(new Runnable() {
//					public void run() {
//						try {
//							createWin window = new createWin();
//							window.frame.setBounds(200, 300, 500, 60);
//							window.frame.setResizable(false);
//							window.frame.setVisible(true);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
//				
				CoffeeBarWin.hostNum =Integer.valueOf( JOptionPane.showInputDialog("Host num :"));
				L.l(CoffeeBarWin.hostNum);
				
				Hostpanel = new JPanel(new GridLayout(4, CoffeeBarWin.hostNum/2));
//				LayoutManager layoutManager = frame.getLayout();
				
				
				
				
				frame.add(Hostpanel);
				frame.setVisible(true);
				
				/**
				 *  create Host ..
				 */
				addHosts();
				
			}
		});
		menuBar.add(btnCreateHost);
		
	}
	
	public void addHosts(){
		CoffeeBarDB db = new CoffeeBarDB();
		try {
			db.CreateClient(hostNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ; i < hostNum; i++){
			Hostpanel.add(new Host(i));
		}
		Hostpanel.updateUI();
	}

	

}
