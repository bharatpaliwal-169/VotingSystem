package evs.dao;
import evs.setup.*;
import evs.bean.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class UserDao {
	//db connections
	public static Connection connect=getConnection();
	public static PreparedStatement ps;
	public static ResultSet rs;
	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection
					(Pass.getUrldb(),Pass.getUser(),Pass.getPass());
		}
		catch(ClassNotFoundException cnf){
			System.out.println(cnf);
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return connect;
	}
	
	
	//Sign-up
	public static int signupUser(UserBean ub) {
		int i=0; 
		try{
			ps=connect.prepareStatement("insert into users values(?,?,?,?)");
			ps.setString(1,ub.getUserName());
			ps.setString(2,ub.getPassword());
			ps.setString(3,ub.getUserId());
			ps.setInt(4, ub.getUserType());
			i=ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, ub.getUserName()+ " signed in Successfully \n"
					+ "Please Note your ID for Future Logins \n" + ub.getUserId());

			System.out.println(ub.getUserId());
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	// Login
	public static UserBean loginUser(String ID) {
		UserBean lub = null;
		try{
			ps=connect.prepareStatement("select * from users where User_Id=?");
			ps.setString(1,ID);
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1) != null) {
					lub = new UserBean(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4));					
				}
				else {
					JOptionPane.showMessageDialog(null, " You have not signed up");
					return null;
				}
			}
			JOptionPane.showMessageDialog(null, "Dear "+lub.getUserName()+" logged in Successfully");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return lub;
	}
	
	
	//voter
	public static int addToVoterList(String u_id,String v_id) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into voter_list values(?,?)");
			ps.setString(1,u_id);
			ps.setString(2,v_id);
			i = ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Voter added to list : Eligible to vote :)");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	public static int becomeVoter(UserBean ub) {
		int i=0;
		try{
			ps=connect.prepareStatement("select * from users where UserID=?");
			ps.setString(1,ub.getUserId());
			String voterId = ub.getUserId()+"canVote";
			i=addToVoterList(ub.getUserId(),voterId);
			System.out.println(voterId);
			
			JOptionPane.showMessageDialog(null, " Now you are eligible to vote :) \n"
					+ "Please Note your VoterID : \n" + voterId);
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	//add candidate
	public static int addCandidate(CandidateBean cb) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into candidates value(?,?)");
			ps.setString(1,cb.getCandidate_name());
			ps.setString(2,cb.getCandidate_id());
			i = ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Candidate" + cb.getCandidate_name() +"added \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	//remove candidate
	public static int removeCandidate(String id) {
		int i=0;
		try{
			ps=connect.prepareStatement("delete cascade candidates where c_id=?");
			ps.setString(1,id);
			i = ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Candidate removed !! \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	public static int deletevote() {
		int i=0;
		try {
			ps=connect.prepareStatement("delete from ballot where e_id = 51808");
			i = ps.executeUpdate();
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
}
