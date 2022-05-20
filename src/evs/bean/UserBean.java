package evs.bean;

public class UserBean {
	private String userName;
	private String password;
	private String userId;
	private int userType;
	
	public UserBean(String username,String password,String id,int type) {
		this.userName = username;
		this.password = password;
		this.userId = id;
		this.userType = type;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getUserId() {
		return userId;
	}
	public int getUserType() {
		return userType;
	}
	
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", userName=" + userName + ", password=" + 
				password + ", userType=" + userType + "]\n";
	}

	
}
