package gui;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * JPanel with GUI for showing notifications and alarms.
 *
 */
public class NotificationPane extends JPanel {

	protected static JLabel notLabel;
	protected static JLabel alarmLabel;
	protected static JTextArea notArea;
	protected static JTextArea alarmArea;
	
	public NotificationPane() {
		
		notLabel = new JLabel("Varsler");
		alarmLabel = new JLabel("Alarmer");
		notArea = new JTextArea();
		alarmArea = new JTextArea();
		
	}
	
	public void setup() throws SQLException {
		ResultSet notifications = MainFrame.db.getNotifications(MainFrame.loggedInAs);
		int rows = 0;
		if (notifications.last()){
			rows = notifications.getRow();
			notifications.beforeFirst();
		}
		notArea = new JTextArea(rows, 1);
		while (notifications.next()) {
			notArea.insert(notifications.getString(1), notifications.getRow());
		}
		
		setLayout(new GridLayout(2, 1));
		add(notLabel);
		add(notArea);
	}
}
