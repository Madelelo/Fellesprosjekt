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

	/**
	 * Returns a list of all the employees
	 * 
	 * @return
	 */
	public ResultSet getEmployees() {
		String qry = "SELECT * FROM employee";

		return db.readQuery(qry);
	}
	
	public ResultSet getInvitedEmployees(int appmntID) {
		return null;
	}
	
	public ResultSet getUninvitedEmployees(int appmntID) {
		return null;
	}

	/**
	 * Returns all notifications for the logged in user
	 * 
	 * @param e
	 * @return
	 */
	public ResultSet getAlarms(Employee e) {
		String qry = "SELECT message FROM notification N, appointment_has_employee A, employee E "
				+ "WHERE N.appointment_appointmentID = A.appoinment_appointmentID"
				+ " AND A.employee = '" + e.getEmail() + "';";

		return db.readQuery(qry);
	}

	public ResultSet getAppointmentsBy(Employee e) {
		String qry = "SELECT appointmentID, date, starttime FROM appointment a "
				+ "WHERE a.owner = '" + e.getEmail() + "';";

		return db.readQuery(qry);
	}
	
	public ResultSet getAppointment(int appmntID) {
		String qry = "SELECT * FROM appointment a WHERE a.appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Puts the appointment in the database, and calls function to put the
	 * relation in the database.
	 * 
	 * @param appmnt
	 * @param empl
	 * @return boolean
	 */
	public boolean createAppointment(Appointment appmnt, Employee e) {

		String qry = "INSERT INTO appointment (date, starttime, endtime, duration, description, meetingroom, owner) VALUES ('"
				+ appmnt.getDate()
				+ "', '"
				+ appmnt.getStarttime()
				+ "', '"
				+ appmnt.getEndtime()
				+ "', '"
				+ appmnt.getDuration()
				+ "', '"
				+ appmnt.getDescription()
				+ "', '"
				+ appmnt.getLocation()
				+ "', '" + e.getEmail() + "');";

		db.updateQuery(qry);

		return true;
	}

	/**
	 * Puts the relation between employee and appointment in the database.
	 * 
	 * @param email
	 * @param appmntkey
	 * @param owner
	 * @param participates
	 * @return boolean
	 */
	public boolean hasAppointment(String email, int appmntkey, int participates) {

		String qry = "INSERT INTO appointment_has_employee VALUES ('"
				+ appmntkey + "', '" + email + "', '" + participates + "');";

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
		String qry = "SELECT username, password, email FROM employee WHERE username='"
				+ username + "' AND password='" + pw + "';";
		ResultSet result = db.readQuery(qry);
		Employee loggedInAs = new Employee();

		try {
			if (result.next()) {
				String email = result.getString("email");
				loggedInAs.setEmail(email);
				loggedInAs.setUsername(username);
				loggedInAs.setPassword(pw);
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
			if (db.getConnection().isValid(1)) {
				connected = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connected;
	}

}
