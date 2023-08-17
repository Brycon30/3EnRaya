package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	public static String nombre = "";
	public static int idJugador = 0;
	
	public static Connection getConnection() {
		String conexionUrl = "jdbc:sqlserver://localhost:1433;"
				+ "databaseName=tresenraya2;"
				+ "user=adminTresEnRaya;"
				+ "password=admin123;"+
				"encrypt=true;trustServerCertificate=true";
		
		try {
			Connection con = DriverManager.getConnection(conexionUrl);
			return con;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
