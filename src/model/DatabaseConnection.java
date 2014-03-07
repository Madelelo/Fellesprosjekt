package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class DatabaseConnection {

	Database db;
	
	public DatabaseConnection() {
		db = new Database();
	}
	
	public boolean createAppointment(Appointment appmnt, Employee empl) {

		String qry = "INSERT INTO appointment (date, starttime, endtime, duration, location, description) VALUES ('" + appmnt.getDate() + "', '" + appmnt.getStarttime()
				+ "', '" + appmnt.getEndtime() + "', '" + appmnt.getDuration() + "', '" + appmnt.getLocation() + "', '" + appmnt.getDescription()
				+ "');";
		
		ArrayList<Integer> keys = db.insertAndGetKeysQuery(qry);
		
		hasAppointment(empl.getEmail(), keys.get(0), true, true);
		
		return true;
	}
	
	public boolean hasAppointment(String email, int appmntkey, boolean owner, boolean participates) {
		
		String qry = "INSERT INTO appointment_has_employee VALUES ('" + appmntkey + "', '" + email + "', '"
				+ owner + "', '" + participates + "');";
		
		db.updateQuery(qry);
		return true;
	}
	
	/**
	 * Validates the attempted login, and creates an employee object.
	 * 
	 * @param username
	 * @param pw
	 * @return Employee
	 */
	public Employee validateLogin(String username, String pw) {
		String qry = "SELECT username, password, email FROM employee WHERE username='" + username + "' AND password='" + pw + "';";
		ResultSet result = db.readQuery(qry);
		Employee loggedInAs = new Employee();
		
		try {
			if(result.next()) {
				String email = result.getString("email");
				loggedInAs = new Employee(email, username, pw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return loggedInAs;
	}
	
	/**
	 * Checks if the database is connected.
	 * 
	 * @return boolean
	 */
	public boolean isConnected() {
		boolean connected = false;
		try {
			if(db.getConnection().isValid(3)) {
				connected = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connected;
	}
	
	public boolean exists(Appointment appmnt) {
		return false;
	}

}
