import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Tools.L;


public class Host extends JButton implements ActionListener{
	int id;
	public int state = 0;
	public static int ING = 1;
	public static int OFF = 0;
	private Thread timer;
	private CoffeeBarDB db;
	public ImageIcon iNGIcon;
	private boolean if_break = false;
	public Host(int id) {
		// TODO Auto-generated constructor stub
		super(String.valueOf(id));
//		iNGIcon = new ImageIcon("res/ICON/ING.png");
		db =  new CoffeeBarDB();
		this.id = id;
		this.setBounds(new Rectangle(100, 100));
		this.addActionListener(this);
		state = ING;
	}
	
	@SuppressWarnings("deprecation")
	public void setOff() {
		this.setText(String.valueOf(id));
		state = ING;
		if(timer.isAlive()){
			if_break = true;
		}
		try {
			db.SighOut(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		state = (state +1 )%2;
		if (state == ING){
			if(JOptionPane.showConfirmDialog(this, "if off this host?") == 0){
				setOff();
			}
//				setOff();
//			}
		}else{
			int lon = -1; 
			lon = Integer.valueOf( JOptionPane.showInputDialog("How long You want:"));
			if (lon <0){
				return;
			}
			/**
			 * user login 
			 */
			try {
				db.SighIn(id, lon);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			StartTimer(lon);
			
		}
		this.updateUI();
		
	}
	
	
	public void StartTimer(int hour){
//		this.setIcon(iNGIcon);
		timer = new Thread(new TimerRun(this,hour));
		timer.start();
		this.state = OFF;
		this.setText(String.valueOf(id));
		
	}
	
	class TimerRun implements Runnable {
		Host host;
		long last ;
		public TimerRun(Host host,int hour) {
			// TODO Auto-generated constructor stub
			this.host = host;
			last = hour * 60*60;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(last >= 0){
				if (if_break){
					break;
				}
				host.setText( String.valueOf(last ));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				last -= 1;
				
			}
		}
		
	}
	
}
