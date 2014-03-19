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
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getInvitedEmployees(int appmntID) {

		String qry = "SELECT e.email, i.hasanswered, i.isparticipating FROM employee e, invited_to i "
				+ "WHERE e.email = i.email "
				+ "AND i.appointmentID = " + appmntID +";";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all the external employees invited to an appointment.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getInvitedExternalEmployees(int appmntID) {
		
		String qry = "SELECT e.email FROM external_employee e "
				+ "WHERE e.appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Returns a ResultSet with all the employees NOT invited to an appointment.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getUninvitedEmployees(int appmntID) {

		String qry = "SELECT e.email FROM employee e "
				+ "WHERE e.email NOT IN ("
				+ "SELECT e.email FROM employee e, invited_to i "
				+ "WHERE e.email = i.email "
				+ "AND i.appointmentID = " + appmntID + ");";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns the number of employees invited to an given appointment.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getInvitedCount(int appmntID) {
		
		String qry = "SELECT COUNT(appointmentID) AS numberOfInvited FROM invited_to WHERE appointmentID = " + appmntID +";";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns the number of external employees invited to an given appointment.
	 * 
	 * @param appmntID
	 * @return
	 */
	public ResultSet getExternalInvitedCount(int appmntID) {
		
		String qry = "SELECT COUNT(appointmentID) AS numberOfInvited FROM external_employee WHERE appointmentID = " + appmntID +";";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all the booked rooms.
	 * 
	 * @return ResultSet
	 */
	public ResultSet getBookedRooms() {
		String qry = "SELECT name, capacity, appointmentID, date, starttime, endtime, duration "
				+ "FROM appointment a JOIN meetingroom m WHERE a.meetingroom = m.name";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all rooms.
	 * 
	 * @return ResultSet
	 */
	public ResultSet getRooms() {
		String qry = "SELECT * FROM meetingroom;";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with the room with the given name.
	 * 
	 * @param String name
	 * @return ResultSet
	 */
	public ResultSet getRoom(String name) {
		String qry = "SELECT name, capacity FROM meetingroom WHERE name = '" + name + "';";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with the room associated with the given appointmentID.
	 * 
	 * @param appmntID
	 * @return ResultSet
	 */
	public ResultSet getRoom(int appmntID) {
		String qry = "SELECT m.capacity FROM appointment a, meetingroom m "
				+ "WHERE a.meetingroom = m.name "
				+ "AND a.appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Returns a ResultSet (with appointments) with all unanswered invitations.
	 * 
	 * @param Employee e
	 * @return ResultSet
	 */
	public ResultSet getInvitations(Employee e) {

		String qry = "SELECT date, starttime, a.appointmentID FROM invited_to i, appointment a "
				+ "WHERE i.email = '"
				+ e.getEmail()
				+ "' "
				+ "AND i.appointmentID = a.appointmentID "
				+ "AND i.hasanswered = " + 0 + ";";

		return db.readQuery(qry);
	}
	
	/**
	 * Deletes a specific notification from the database.
	 * 
	 * @param int notiID
	 * @return boolean
	 */
	public boolean deleteNotification(int notiID) {
		
		String qry = "DELETE FROM notification WHERE notificationID = " + notiID + ";";
		
		db.updateQuery(qry);
		return true;
	}
	
	/**
	 * Creates a notification when a user is invited to an appointment.
	 * 
	 * @param int appmntID
	 * @param String email
	 * @return boolean
	 * @throws SQLException 
	 */
	public boolean invitationNotification(int appmntID, String email) throws SQLException {
		
		ResultSet appmnt = getAppointment(appmntID);
		appmnt.next();
		
		if(!appmnt.getString(8).equals(email)) {

			String msg = "You have been invited to an appointment " + appmnt.getDate(2).toString() + ", by "
					+ appmnt.getString(8);
			
			String qry = "INSERT INTO notification (message, appointmentID, email) "
					+ "VALUES ('" + msg + "', " + appmntID + ", '" + email +"');";
			
			db.updateQuery(qry);
		}
		return true;
	}
	
	/**
	 * Creates a notification when a user answers an invitation.
	 * 
	 * @param int appmntID
	 * @param String email
	 * @return boolean
	 */
	public boolean answerNotification(int appmntID, String email, String ans) throws SQLException {
		
		ResultSet appmnt = getAppointment(appmntID);
		appmnt.next();
		
		String msg = email + " has " + ans.toLowerCase() + "ed your invitation to appointment " + appmnt.getDate(2).toString();
		
		String qry = "INSERT INTO notification (message, appointmentID, email) "
				+ "VALUES ('" + msg + "', " + appmntID + ", '" + appmnt.getString(8) + "');";
		
		db.updateQuery(qry);
		return true;
	}

	/**
	 * Returns a ResultSet with all the notifications for the logged in user.
	 * 
	 * @param Employee e
	 * @return ResultSet
	 */
	public ResultSet getNotifications(Employee e) {
		String qry = "SELECT notificationID, message FROM notification n "
				+ "WHERE n.email = '" + e.getEmail() + "' "
				+ "AND n.alarmtime IS NULL;";

		return db.readQuery(qry);
	}
	
	/**
	 * Creates an alarm for a given appointment with a given time and message for the given user.
	 * 
	 * @param String msg
	 * @param String time
	 * @param int appmntID
	 * @param String email
	 * @return boolean
	 */
	public boolean createAlarm(String msg, String time, int appmntID, String email) {
		String qry = "INSERT INTO notification (message, alarmtime, appointmentID, email) "
				+ "VALUES ('" + msg +"', '" + time + "', " + appmntID + ", '" + email + "');";
		
		db.updateQuery(qry);
		return true;
	}

	/**
	 * Returns a ResultSet with all the alarms for the logged in user.
	 * 
	 * @param Employee e
	 * @return ResultSet
	 */
	public ResultSet getAlarms(Employee e) {
		String qry = "SELECT n.notificationID, n.message, a.date, n.alarmtime FROM notification n, appointment a "
				+ "WHERE n.email = '" + e.getEmail() + "' "
				+ "AND n.alarmtime != 'null' "
				+ "AND n.appointmentID = a.appointmentID;";

		return db.readQuery(qry);
	}

	/**
	 * Returns a ResultSet with all appointments made by specific employee.
	 * 
	 * @param Employee e
	 * @return ResultSet
	 */
	public ResultSet getAppointmentsBy(Employee e) {
		String qry = "SELECT appointmentID, date, starttime FROM appointment a "
				+ "WHERE a.owner = '" + e.getEmail() + "';";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with all appointments the user has accepted.
	 * 
	 * @param Employee e
	 * @return boolean
	 */
	public ResultSet getAcceptedAppointments(Employee e) {
		String qry = "SELECT a.appointmentID, a.date, a.starttime FROM appointment a, invited_to i "
				+ "WHERE a.appointmentID = i.appointmentID "
				+ "AND i.isparticipating = 1 "
				+ "AND i.email = '" + e.getEmail() + "';";
		
		return db.readQuery(qry);
	}

	/**
	 * Returns a ResultSet with the appointment with the given appointmentID.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getAppointment(int appmntID) {
		String qry = "SELECT * FROM appointment a WHERE a.appointmentID = "
				+ appmntID + ";";

		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with the date of the appointment.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getAppointmentDate(int appmntID) {
		String qry = "SELECT date FROM appointment WHERE appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}
	
	/**
	 * Returns a ResultSet with the starttime of the appointment.
	 * 
	 * @param int appmntID
	 * @return ResultSet
	 */
	public ResultSet getAppointmentStarttime(int appmntID) {
		String qry = "SELECT starttime FROM appointment WHERE appointmentID = " + appmntID + ";";
		
		return db.readQuery(qry);
	}

	/**
	 * Inserts new appointment into the database.
	 * 
	 * @param Appointment appmnt
	 * @param Employee e
	 * @return boolean
	 */
	public boolean createAppointment(Appointment appmnt, Employee e) {
		boolean success = true;
		String qry = "";
		if(!appmnt.getEndtime().isEmpty()) {
			qry = "INSERT INTO appointment (date, starttime, endtime, duration, description, meetingroom, owner) VALUES ('"
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
		} else {
			qry = "INSERT INTO appointment (date, starttime, endtime, duration, description, meetingroom, owner) VALUES ('"
					+ appmnt.getDate()
					+ "', '"
					+ appmnt.getStarttime()
					+ "', NULL, '"
					+ appmnt.getDuration()
					+ "', '"
					+ appmnt.getDescription()
					+ "', '"
					+ appmnt.getLocation()
					+ "', '" + e.getEmail() + "');";
		}
		
		try {
			int appmntID = db.insertAndGetKeysQuery(qry).get(0);
			
			inviteTo(e.getEmail(), appmntID);
			confirmInvitation(e.getEmail(), appmntID);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			success = false;
		}

		return success;
	}
	
	/**
	 * Updates an appointment in the database.
	 * 
	 * @param Appointment a
	 */
	public void updateAppointment(Appointment a) {
		
		String qry = "";
		if(!a.getEndtime().isEmpty()) {
			qry = "UPDATE appointment SET date = '" + a.getDate() + "', starttime = '" + a.getStarttime() + "', endtime = '"
					+ a.getEndtime() + "', duration = '" + a.getDuration() + "', description = '" + a.getDescription()
					+ "', meetingroom = '" + a.getLocation() + "' WHERE appointmentID = " + a.getAppointmentID() + ";";
		} else {
			qry = "UPDATE appointment SET date = '" + a.getDate() + "', starttime = '" + a.getStarttime() + "', endtime = NULL, duration = '"
					+ a.getDuration() + "', description = '" + a.getDescription()
					+ "', meetingroom = '" + a.getLocation() + "' WHERE appointmentID = " + a.getAppointmentID() + ";";
		}
		
		db.updateQuery(qry);
	}

	/**
	 * Deletes the appointment from the database,
	 * 
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean deleteAppointment(int appmntID) {

		String qry = "DELETE FROM appointment WHERE appointmentID = "
				+ appmntID + ";";

		db.updateQuery(qry);

		return true;
	}

	/**
	 * Invites an employee to an given appointment.
	 * 
	 * @param String email
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean inviteTo(String email, int appmntID) {

		String qry = "INSERT INTO invited_to VALUES ('" + appmntID + "', '"
				+ email + "', '0', '0');";

		db.updateQuery(qry);
		
		try {
			invitationNotification(appmntID, email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * Invites an external employee to an given appointment.
	 * 
	 * @param String email
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean inviteExternal(String email, int appmntID) {
		
		String qry = "INSERT INTO external_employee VALUES(" + appmntID + ", '" + email + "');";
		db.updateQuery(qry);
		
		return true;
	}
	
	public boolean removeExternal(String email, int appmntID) {
		
		String qry = "DELETE FROM external_employee "
				+ "WHERE appointmentID = " + appmntID
				+ " AND email = '" + email + "';";
		
		db.updateQuery(qry);
		
		return true;
	}

	/**
	 * Removes an employee from an given appointment.
	 * 
	 * @param String email
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean removeFrom(String email, int appmntID) {

		String qry = "DELETE FROM invited_to "
				+ "WHERE appointmentID = " + appmntID
				+ " AND email = '" + email + "';";

		db.updateQuery(qry);

		return true;
	}

	/**
	 * Updates the status of the invitation so that the employee is
	 * participating.
	 * 
	 * @param String email
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean confirmInvitation(String email, int appmntID) {

		String qry = "UPDATE invited_to SET hasanswered=1, isparticipating=1 WHERE appointmentID = "
				+ appmntID + " AND email = '" + email + "';";

		db.updateQuery(qry);

		return true;
	}

	/**
	 * Updates the status of the invitation so that the employee has declined.
	 * 
	 * @param String email
	 * @param int appmntID
	 * @return boolean
	 */
	public boolean declineInvitation(String email, int appmntID) {

		String qry = "UPDATE invited_to SET hasanswered=1, isparticipating=0 WHERE appointmentID = "
				+ appmntID + " AND email = '" + email + "';";

		db.updateQuery(qry);

		return true;
	}

	/**
	 * Validates the attempted login, and creates an employee object.
	 * 
	 * @param String username
	 * @param String pw
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
	
	public boolean hasCapacity(int appmntID) {
		boolean hasCapacity = false;
		
		ResultSet invitedCount = getInvitedCount(appmntID);
		ResultSet invitedExternalCount = getExternalInvitedCount(appmntID);
		ResultSet room = getRoom(appmntID);
		try {
			invitedCount.next();
			invitedExternalCount.next();
			room.next();
			
			int invited = invitedCount.getInt(1) + invitedExternalCount.getInt(1);
			
			if(invited < room.getInt(1)) {
				hasCapacity = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hasCapacity;
	}

}
