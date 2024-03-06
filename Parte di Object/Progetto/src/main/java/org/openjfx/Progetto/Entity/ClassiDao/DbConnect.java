package org.openjfx.Progetto.Entity.ClassiDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect{
	private static String url = "jdbc:postgresql://localhost:5432/Progetto";
	static String password ="Lanutella99";
	static String userName ="postgres";
	
	public static Connection getConnection() {
		Connection con;
		try {
			con = DriverManager.getConnection(url, userName ,password);
			return con;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		DbConnect.url = url;
	}
}

