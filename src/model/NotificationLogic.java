package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationLogic {
	
	public static void sendUpdateNotifications(int appmntID, DatabaseConnection db) {
		ResultSet invited = db.getInvitedEmployees(appmntID);
		
		try {
			while(invited.next()) {
				if(!((invited.getBoolean(2)) && !(invited.getBoolean(3)))) {
					db.updateNotification(appmntID, invited.getString(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendDeleteNotifications(int appmntID, DatabaseConnection db) {
		ResultSet invited = db.getInvitedEmployees(appmntID);
		
		try {
			while(invited.next()) {
				if(!((invited.getBoolean(2)) && !(invited.getBoolean(3)))) {
					db.deleteNotification(appmntID, invited.getString(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
