package evs.setup;

public class Pass {
	private static String user = "root";
	private static String pass = "Mahavir@108";
	private static String urldb = "jdbc:mysql://localhost:3306/evs";
	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Pass.user = user;
	}

	public static String getUrldb() {
		return urldb;
	}

	public static void setUrldb(String urldb) {
		Pass.urldb = urldb;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		Pass.pass = pass;
	}
	
}
