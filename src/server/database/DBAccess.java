package server.database;

import java.sql.*;

/**
 * Gestisce l'accesso alla base di dati
 * 
 * @author Angelo, Simone, Antonio
 *
 */
public class DBAccess {
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DBMS = "jdbc:mysql";
	private static final String SERVER = "localhost";
	private static final String DATABASE = "CFP17_621498";
	private static final int PORT = 3306;
	private static final String USER_ID = "Angeletti_621498";
	private static final String PASSWORD = "CFP17_621498";

	private static Connection conn;

	public static boolean initConnection() throws DatabaseConnectionException {
		boolean flag = false;

		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;

		try {
			Object cl = Class.forName(DRIVER_CLASS_NAME).newInstance();

			if (cl == null)
				flag = false; // Caricamento Driver non avvenuto
			else
				flag = true;
		} catch (InstantiationException e) {
			System.err.println("InstantiationException");
		} catch (IllegalAccessException e1) {
			System.err.println("IllegalAccessException");
		} catch (ClassNotFoundException e2) {
			System.err.println("Class not found");
		}

		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);// apro
																					// il
																					// canale
																					// di
																					// comunicazione
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
		}
		return flag;
	}

	public static Connection getConnection() {
		return conn;
	}

	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Database Error");
			e.printStackTrace();
		}
	}

}
