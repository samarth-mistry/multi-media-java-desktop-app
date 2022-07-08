package pack1;
import java.sql.Connection;
import java.sql.DriverManager;

public class xampp_connector {
	private static Connection conn;
	public static Connection getConnection() {
		try {
			
			String url= String.format("jdbc:mysql://localhost:3306/desktop10");
			Class.forName("com.mysql.jdbc.Driver");			
			conn= DriverManager.getConnection(url,"root","");
			System.out.println("Connection Established");				       
		}catch(Exception e) {
			System.out.print("Connection Abort");
			e.printStackTrace();			
		}
		return conn;
	}
	public static void main(String[] args) {
		getConnection();	
	}
}
