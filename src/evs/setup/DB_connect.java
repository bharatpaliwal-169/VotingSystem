package evs.setup; 
import java.sql.*;

public class DB_connect {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); //register and load the DB Driver
		
		Connection connect = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/evs","root",Pass.getPass());
		System.out.println("Connection Success");
		
		Statement st = connect.createStatement(); 		
		st.execute("create table users(UserName varchar(20),Password varchar(20),User_Id varchar(20)"
				+ ",UserType int)");
		st.executeUpdate("insert into users value('admin','admin@evs.com','special-1234',3)");
		System.out.println("DB setup done");
	}

}
