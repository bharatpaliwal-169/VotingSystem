package evs.main;
import evs.dao.*;
import evs.bean.*;
import javax.swing.JOptionPane;
import java.util.*;
public class Index {
	//entry point
	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null,"EVS - WeVote Application");
		int opt = Integer.parseInt(
				JOptionPane.showInputDialog
				("Select as Follows:\n 1-> Sign Up \n"
				+ " 2-> Login \n 3-> Exit"));
		
		switch(opt) {
			case 1:
				//signup
				String userName=JOptionPane.showInputDialog("Enter your name "
						+ "(Please Avoid spaces instead use '_' underscore when required)");
				String password=JOptionPane.showInputDialog("Enter your password (min_length : 4 characters)");
				Random rand = new Random();
				String userId = "";
				userId += (userName + rand.nextInt(99999999));

				//user - label
				int userType = 0;
				if(password.equals("eo@evs.com"))userType = 2;
				if(password.equals("admin@evs.com")) userType = 3;
				else userType = 1;
				
				//validate enteries
				if(userName.length() <= 0 || password.length() < 4) {
					JOptionPane.showMessageDialog(null, "Please give proper details : Terminate Program");
					return;
				}
				
				//action
				UserBean ub = new UserBean(userName,password,userId,userType);
				UserDao.signupUser(ub);
				
				//onSuccess
				Panel.afterSuccess(ub);
				break;
			case 2:
				
				//login
				String uId=JOptionPane.showInputDialog("Enter your userID:");
				UserBean loginUser = UserDao.loginUser(uId);
				
				if(loginUser != null) {
					Panel.afterSuccess(loginUser);
				}
				else {
					return;
				}
				break;
			case 3:
				//exit
				return;
			default:
				break;
		}
		JOptionPane.showMessageDialog(null ," You are now logging out : WeVote \n"
				+ "Keep voting :) ");
	}

}
