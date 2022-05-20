package evs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import evs.bean.*;
import evs.setup.Pass;


public class CandidateDao {
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
	
	public static int addCandidate(CandidateBean cb) {
		int i=0;
		try{
			ps=connect.prepareStatement("insert into candidates values(?,?)");
			ps.setString(1,cb.getCandidate_id());
			ps.setString(2,cb.getCandidate_name());
			i=ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "candidate added Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;	
	}
	//update
	public static int updateCandidate(CandidateBean cb) {
		int i=0;
		try{
			ps=connect.prepareStatement("update candidates set c_name=? where c_id=?");
			ps.setString(1,cb.getCandidate_name());
			ps.setString(2,cb.getCandidate_id());
			i=ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Candidate info updated Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
	//delete
	public static int deleteCandidate(String del_id) {
		int i=0;
		try{
			ps=connect.prepareStatement("delete from candidates where c_id=?");
			ps.setString(1,del_id);
			i=ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Candidate removed Successfully \n");
		}
		catch(SQLException sql){
			System.out.println(sql);
		}
		return i;
	}
	
//	/adding participants to election
	public static int addParticipants(String e_id,String c_id) {
		int i=0;
		try {
			ps = connect.prepareStatement("insert into participants values(?,?)");
			ps.setString(1,e_id);
			ps.setString(2,c_id);
			i = ps.executeUpdate();
			JOptionPane.showMessageDialog(null,"Participant added to the list\n");
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return i;
	}
	
	// List of candiates participating in an election
	public static int listOfParticipants(String e_id) {
		int i=0;
		try {
			ps = connect.prepareStatement("select * from participants where e_id=?");
			ps.setString(1,e_id);
			rs = ps.executeQuery();
			String str = "";
			while(rs.next()) {
				str += rs.getString(2) + " \n" ;
			}
			
			JOptionPane.showMessageDialog(null,str);
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return i;
	}
	//check if paticipant is taking part in given election
	public static boolean checkParticipant(String e_id,String c_id) {
		boolean i=false;
		try {
			ps = connect.prepareStatement(" select * from participants where e_id=? AND c_id=?");
			ps.setString(1,e_id);
			ps.setString(2,c_id);
			rs = ps.executeQuery();
			String str = "";
			while(rs.next()) {
				str += rs.getString(2) + " \n" ;
			}
			
			if(str.length() <=0) i = false;
			else i=true;
			
			System.out.println(str);
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return i;
	}
	
		
}
