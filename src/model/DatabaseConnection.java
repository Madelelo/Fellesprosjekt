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
	 * Returns a ResultSet with all the employees
	 * 
	 * @return ResultSet
	 */
	public ResultSet getEmployees() {
		String qry = "SELECT * FROM employee";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all the employees invited to an appointment.
	 * 
	 * @param appmntID
	 * @return ResultSet
	 */
	public ResultSet getInvitedEmployees(int appmntID) {
		
		
		
		return null;
	}
	
	/**
	 * Returns a ResultSet with all the employees NOT invited to an appointment.
	 * 
	 * @param appmntID
	 * @return ResultSet
	 */
	public ResultSet getUninvitedEmployees(int appmntID) {
		
		String qry = "SELECT username FROM employee E "
		+"WHERE E.email NOT IN ("
		+"SELECT E.email FROM employee E, invited_to A "
		+"WHERE E.email = A.email)";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet (with appointments) with all unanswered invitations.
	 * 
	 * @param e
	 * @return ResultSet
	 */
	public ResultSet getInvitations(Employee e) {
		
		String qry = "SELECT date, starttime FROM invited_to i, appointment a "
				+ "WHERE i.email = '" + e.getEmail() + "' "
				+ "AND i.appointmentID = a.appointmentID "
				+ "AND i.hasanswered = " + 0 + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Returns a ResultSet with all the notifications for the logged in user.
	 * 
	 * @param e
	 * @return
	 */
	public ResultSet getNotifications(Employee e) {
		String qry = "SELECT message FROM notification N, invited_to A, employee E "
				+ "WHERE N.appointmentID = A.appointmentID"
				+ " AND A.email = '" + e.getEmail() + "';";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all the alarms for the logged in user.
	 * 
	 * @param e
	 * @return ResultSet
	 */
	public ResultSet getAlarms(Employee e) {
		String qry = "SELECT alarmtime FROM notification N, invited_to A, employee E "
				+ "WHERE N.appointmentID = A.appointmentID"
				+ "WHERE A.email = '" + e.getEmail() + "';";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all appointments made by specific employee.
	 * 
	 * @param e
	 * @return ResultSet
	 */
	public ResultSet getAppointmentsBy(Employee e) {
		String qry = "SELECT appointmentID, date, starttime FROM appointment a "
				+ "WHERE a.owner = '" + e.getEmail() + "';";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with the appointment with the given appointmentID.
	 * 
	 * @param appmntID
	 * @return ResultSet
	 */
	public ResultSet getAppointment(int appmntID) {
		String qry = "SELECT * FROM appointment a WHERE a.appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Inserts new appointment into the database.
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
	 * Deletes the appointment from the database,
	 * 
	 * @param appmntID
	 * @return boolean
	 */
	public boolean deleteAppointment(int appmntID) {
		
		String qry = "DELETE FROM appointment WHERE appointmentID = " + appmntID + ";";
		
		db.updateQuery(qry);
		
		return true;
	}

	/**
	 * Invites an employee to an given appointment. 
	 * 
	 * @param email
	 * @param appmntkey
	 * @param participates
	 * @return boolean
	 */
	public boolean inviteTo(String email, int appmntID) {

		String qry = "INSERT INTO invited_to VALUES ('"
				+ appmntID + "', '" + email + "', '0', '0');";

		db.updateQuery(qry);

		return true;
	}
	
	/**
	 * Updates the status of the invitation so that the employee is participating.
	 * 
	 * @param email
	 * @param appmntID
	 * @return boolean
	 */
	public boolean confirmInvitation(String email, int appmntID) {
		
		String qry = "UPDATE invited_to SET hasanswered=1, isparticipating=1 WHERE appointment = " + appmntID + ";" ;
		
		db.updateQuery(qry);
		
		return true;
	}
	/**
	 * Updates the status of the invitation so that the employee has declined.
	 * 
	 * @param email
	 * @param appmntID
	 * @return boolean
	 */
	public boolean declineInvitation(String email, int appmntID) {
		
		String qry = "UPDATE invited_to SET hasanswered=0, isparticipating=0 WHERE appointment = " + appmntID + ";" ;
		
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
