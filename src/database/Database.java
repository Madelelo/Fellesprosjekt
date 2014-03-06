package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	/**
	 * Handels connection to database server.
	 * 
	 * hei
	 */

	private static Connection connection = null;
	private static String connectionURL = "jdbc:mysql://localhost:3306/fellesprosjekt";
	private static Statement stat = null;
	private static String user = "root";
	private static String pw = "fellesprosjekt";
	private static String database = "fellesprosjekt";
	
	/**
	 * Connects to the database.
	 * 
	 * @throws Exception
	 */
	public Database() {
		try { //Logger inn i databasen
			connection = DriverManager.getConnection(connectionURL, user, pw);
			stat = connection.createStatement();
		} catch(Exception e) {
			System.out.println("Oppkobling mot database feilet: " + e.getMessage());
		}
	}
	
	/**
	 * Closes the connection to the database.
	 * 
	 * @throws SQLException
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * SQL queries that don't change the database.
	 * 
	 * @param sql
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet readQuery(String sql) {
		Statement s;
		try {
			s = connection.createStatement();
			ResultSet rs;
			
			rs = s.executeQuery(sql);
			return rs;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * SQL queries that changes the database. Eg. CREATE TABLE, INSERT, UPDATE, DELETE.
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void updateQuery(String sql) {
		Statement s;
		try {
			s = connection.createStatement();
			s.executeUpdate(sql);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
