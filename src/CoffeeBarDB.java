import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Tools.L;


public class CoffeeBarDB {
	Statement  my_state;
	public static int IS_EXIST_DATABASE = 0;
	public CoffeeBarDB() {
		// TODO Auto-generated constructor stub
		
		
		try{
			
			String userString = "root";
			String passwd = "";
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CoffeeBar",userString,passwd);
			
			this.my_state =  con.createStatement();
			
			java.sql.DatabaseMetaData meta = con.getMetaData();
//			resultSet.first();
			ResultSet rs2 = meta.getTables(null, null,"%", null);
			

			while(rs2.next()){
				L.l(rs2.getString(3));
				IS_EXIST_DATABASE += 1;
			}
			if (IS_EXIST_DATABASE == 0 ){
				my_state.execute("CREATE TABLE IF NOT EXISTS " +
						"Bar(id INT PRIMARY KEY AUTO_INCREMENT," +
						"State INT default 0 ,"+
						"user varchar(30) default \"xxx\"," +
						"start varchar(25) default \"--\" ," +
						"hour int default 0 , "+
						"end varchar(25) default \"--\"  ); ");
				
			}
			
			
			
		}catch ( Exception e){
			e.printStackTrace();
		}
	}
	
	public int CreateClient(int num) throws Exception{
		for(int i = 0;i < num ; i ++){
			my_state.executeUpdate("insert into Bar values ();");
		}
		return num;
	}
	
	public boolean SighIn(int id ,int time_length) throws Exception{
		long now = System.currentTimeMillis();
		long end = now + 3600000 * time_length;
		String sqlString="update bar set state = 1 , start =\""+
				String.valueOf(now)+"\" ,end=\""+
				String.valueOf(end)+
				"\" ,hour="+String.valueOf(time_length)+
				"   where id="+String.valueOf(id)+" ;";
		my_state.execute(sqlString);
		L.l("user:xxx ||"+sqlString);
		return false;
		
	}
	
	public boolean SighIn(String identity,int id ,int time_length) throws Exception{
		long now = System.currentTimeMillis();
		long end = now + 3600000 * time_length;
		String sqlString="update bar set " +
				"user=\""+identity+"\","+
				" state = 1 , start =\""+
				String.valueOf(now)+"\" ,end=\""+
				String.valueOf(end)+
				"\" ,hour="+String.valueOf(time_length)+
				"   where id="+String.valueOf(id)+" ;";
		if (my_state.execute(sqlString)){
			return true;
		}
		L.l("user:"+identity+" ||"+sqlString);
		
		return false;
		
	}
	
	
	
	public boolean queryClient (int user_id ) throws Exception{
		ResultSet set = my_state.executeQuery("select state from Bar where id="+user_id+" ;");
		while(set.next()){
			if(set.getInt("state") != 0 ){
				return false;
			}
		}
		return true;
		
		
	}
	
	public boolean SighOut(String user_id) throws Exception{
		String sqlString="update bar set " +
				" state = 0 ,"+
				" start = \"--\","+
				" end = \"--\","+
				" user = \"xxx\","+
				" hour = 0"+
				"   where user=\""+user_id+"\" ;";
		if (my_state.execute(sqlString)){
			return true;
		}
		
		return false;
	}
	public boolean SighOut(int id) throws Exception{
		String sqlString="update bar set " +
				" state = 0 ,"+
				" start = \"--\","+
				" end = \"--\","+
				" user = \"xxx\","+
				" hour = 0"+
				"   where id="+String.valueOf(id)+" ;";
		if (my_state.execute(sqlString)){
			return true;
		}
		
		return false;
	}
	
	public double remainTime(int id) throws Exception{
		String sql = "select end from bar where id=" +String.valueOf(id)+";";
		ResultSet set = my_state.executeQuery(sql);
		L.l(sql);
		while(set.next()){
			long end =  Long.valueOf(set.getString("end"));
			long now = end - System.currentTimeMillis();
			return (double)(now/3600000);
		}
		return 0;
	}
	
	public void close() {
		try {
			my_state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
