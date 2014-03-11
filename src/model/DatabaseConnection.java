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
	 * @return
	 */
	public ResultSet getEmployees() {
		String qry = "SELECT * FROM employee";

		return db.readQuery(qry);
	}

	/**
	 * Returns all notifications for the logged in user
	 * @param e
	 * @return
	 */
	public ResultSet getAlarms(Employee e) {
		String qry = "SELECT message FROM notification N, appointment_has_employee A, employee E "
				+ "WHERE N.appointment_appointmentID = A.appoinment_appointmentID"
				+ " AND A.employee = " + e.getEmail();

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
	public boolean createAppointment(Appointment appmnt, Employee empl) {

		String qry = "INSERT INTO appointment (date, starttime, endtime, duration, location, description) VALUES ('"
				+ appmnt.getDate()
				+ "', '"
				+ appmnt.getStarttime()
				+ "', '"
				+ appmnt.getEndtime()
				+ "', '"
				+ appmnt.getDuration()
				+ "', '"
				+ appmnt.getLocation()
				+ "', '"
				+ appmnt.getDescription()
				+ "');";

		ArrayList<Integer> keys = db.insertAndGetKeysQuery(qry);

		hasAppointment(empl.getEmail(), keys.get(0), 1, 1);

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
	public boolean hasAppointment(String email, int appmntkey, int owner,
			int participates) {

		String qry = "INSERT INTO appointment_has_employee VALUES ('"
				+ appmntkey + "', '" + email + "', '" + owner + "', '"
				+ participates + "');";

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
			if (db.getConnection().isValid(3)) {
				connected = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connected;
	}
}
