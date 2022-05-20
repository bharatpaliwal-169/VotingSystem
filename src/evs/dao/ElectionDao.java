package evs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;
import java.util.*;
import evs.bean.*;
import evs.setup.Pass;
public class ElectionDao {
	//DB setup
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
	
	
	//CASTE VOTE
	public static int casteVote(VoteBean vb) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into ballot values(?,?,?)");
			ps.setString(1,vb.getE_id());
			ps.setString(2,vb.getC_id());
			ps.setString(3,vb.getV_id());
			i=ps.executeUpdate();
			JOptionPane.showMessageDialog(null, " Your Vote is casted Successfully \n");
		}
		catch(SQLIntegrityConstraintViolationException sic) {
			JOptionPane.showMessageDialog(null,"Hey!, you can vote only once.");
			
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		
		return i;
	}
	
	// validate if user have a valid voter id
	public static int checkVoter(String v_id) {
		int flag = 0;
		ArrayList<String> l = new ArrayList<String>();
		try{
			ps=connect.prepareStatement("select * from voter_list where voter_id=?");
			ps.setString(1,v_id);
			rs=ps.executeQuery();
			while(rs.next()) {
				l.add(rs.getString(2));
			}
			System.out.println(l.size());
			if(l.size() <= 0) flag = 0;
			else flag =  1;			
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return flag;
	}
	
	//Evaluate the Result of Election
	public static int Result(String e_id) {
		int i=0;
		try{
			// one liner but a deep thought line
			ps=connect.prepareStatement("select count(c_id),c_id from ballot where e_id=?"
					+ " group by c_id order by count(c_id) desc limit 1 ");
			ps.setString(1,e_id);
			rs = ps.executeQuery();
			String electionResult = "";
			while(rs.next()) {
				electionResult += (rs.getString(2)  + " is winner!");
				i = publish(e_id,rs.getString(2),rs.getInt(1));
			}
			System.out.println(electionResult);
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	// Print the results
	public static int publish(String e_id,String c_id, int votes) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into publish_results values(?,?,?) ");
			ps.setString(1,e_id);
			ps.setString(2,c_id);
			ps.setInt(3,votes);
			i = ps.executeUpdate();
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	// print result -continued
	public static void publishResults() {
		String s = "";
		try {
			ps=connect.prepareStatement("select * from publish_results");
			rs=ps.executeQuery();
			while(rs.next())
			{
				s += rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3) + "\n" ;
			}
			JOptionPane.showMessageDialog(null,s);
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
	}
	
	//create
	public static int createElection(ElectionBean eb) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into electionlist values(?,?,?)");
			ps.setString(1,eb.getElection_id());
			ps.setString(2,eb.getElection_name());
			ps.setInt(3,eb.getElection_status());
			i=ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Election created Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		
		return i;
	}
	//update
	public static int updateElection(ElectionBean eb) {
		int i=0;
		try{
			ps=connect.prepareStatement("update electionlist set election_name=?,election_status=? where election_id=?");
			ps.setString(1,eb.getElection_name());
			ps.setInt(2,eb.getElection_status());
			ps.setString(3,eb.getElection_id());
			i=ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Election updated Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	//delete
	public static int deleteElection(String del_id) {
		int i=0;
		try{
			ps=connect.prepareStatement("delete from electionlist where election_id=?");
			ps.setString(1,del_id);
			i=ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Election deleted Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	//status update start/stop election
	public static int getStatus(String id) {
		int i=0;
		try {
			ps = connect.prepareStatement("select election_status from electionlist where election_id=?");
			ps.setString(1,id);
			rs = ps.executeQuery();
			while(rs.next()) {
				i = rs.getInt(1);
			}
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	//start stop election
	public static int statusUpdate(String id) {
		int j=0;
		int prevState = getStatus(id);
		int newState = (prevState == 1) ? 0 : 1;
		try{
			ps=connect.prepareStatement("update electionlist set "
					+ "election_status=" + newState + " where election_id=?");
			ps.setString(1,id);
			j=ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Election Status Updated Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return j;
	}
	
	//read all --admin only
	public static ArrayList<ElectionBean> viewAllElections() {
		ArrayList<ElectionBean> el =new ArrayList<ElectionBean>();
		try {
			ps=connect.prepareStatement("select * from electionlist");
			rs=ps.executeQuery();
			while(rs.next())
			{
				ElectionBean eb=new ElectionBean(rs.getString(1),rs.getString(2),rs.getInt(3));
				el.add(eb);
			}
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return el;
	}
	
	
	//read all active elections --all users
	public static ArrayList<ElectionBean> viewAllActiveElections() {
		ArrayList<ElectionBean> el =new ArrayList<ElectionBean>();
		try {
			ps=connect.prepareStatement("select * from electionlist where election_status = 1");
			rs=ps.executeQuery();
			while(rs.next())
			{
				ElectionBean eb=new ElectionBean(rs.getString(1),rs.getString(2),rs.getInt(3));
				el.add(eb);
			}
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return el;
	}
	
	

	
}
