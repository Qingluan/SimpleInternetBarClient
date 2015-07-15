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
	public static int KEEP = 2;
	public static int ERR = 3;
	public static int OFF = 0;
	private Thread timer;
	private CoffeeBarDB db;
	private int price = 4;
	public ImageIcon iNGIcon;
	private boolean if_break = false;
	private double hour;
	public Host(int id) {
		// TODO Auto-generated constructor stub
		super(String.valueOf(id));
//		iNGIcon = new ImageIcon("res/ICON/ING.png");
		db =  new CoffeeBarDB();
		this.id = id;
		this.setBounds(new Rectangle(100, 100));
		this.addActionListener(this);
		state = OFF;
	}
	
	@SuppressWarnings("deprecation")
	public void setOff() {
		this.setText(String.valueOf(id));
		state = OFF;
		if(timer.isAlive()){
			if_break = true;
		}
		try {
			double reamin = this.db.remainTime(id);
			db.SighOut(id);
			JOptionPane.showConfirmDialog(this, "You need to pay : "+ (this.hour - reamin) * price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.updateUI();
		this.timer = null;
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		L.l(this.state);
//		state = (state +1 )%2;
		if (state == ING){
			if (JOptionPane.showConfirmDialog(this, "Keep this host ?")==0){
				this.state = KEEP;
				this.setText("Keeping");
				this.setOff();
				
				this.updateUI();
				return;
			}
			
			if(JOptionPane.showConfirmDialog(this, "if off this host?") == 0){
				setOff();
			}
//				setOff();
//			}
		}else if(state == OFF){
			if (JOptionPane.showConfirmDialog(this, "if this host error?") == 0){
				this.setText("error");
				this.state = ERR;
				this.updateUI();
				return;
			}
			int lon = -1; 
			lon = Integer.valueOf( JOptionPane.showInputDialog("the amout of  hour You want to Play:"));
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
			state = ING;
			
		}else if (state == ERR){
			if (JOptionPane.showConfirmDialog(this, "fixed host ?") == 0){
				this.setText(String.valueOf(id));
				this.state = OFF;
				this.updateUI();
				return;
			}
		}
		this.updateUI();
		
	}
	
	
	public void StartTimer(int hour){
//		this.setIcon(iNGIcon);
		this.hour = hour;
		timer = new Thread(new TimerRun(this,hour));
		timer.start();
		this.state = ING;
//		this.setText(String.valueOf(id));
		
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
				host.updateUI();
				L.l("test");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				last -= 1;
				
			}
			
			if_break = false;
		}
		
	}
	
}
