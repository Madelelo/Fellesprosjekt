package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * JPanel with GUI for showing notifications and alarms.
 * 
 */
public class NotificationPane extends JPanel {

	protected static JList<String> notiList;
	protected static JList<String> alarmList;
	protected static JButton deleteNotiBtn;
	protected static JButton newAlarmBtn;
	protected static JButton deleteAlarmBtn;

	public NotificationPane() {
		notiList = new JList<String>();
		alarmList = new JList<String>();
		deleteNotiBtn = new JButton("Delete notification");
		newAlarmBtn = new JButton("Make new alarm");
		deleteAlarmBtn = new JButton("Delete alarm");
		
		
		setLayout(new GridLayout(2, 3));
		add(new JLabel("Notifications:"));
		add(notiList);
		add(deleteNotiBtn);
		add(new JLabel("Alarms:"));
		add(alarmList);
		add(newAlarmBtn);
		add(deleteAlarmBtn);

	}

	public void refresh() throws SQLException {
		ResultSet notifications = MainFrame.db.getNotifications(MainFrame.loggedInAs);

		if(notifications != null) {
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			while (notifications.next()) {
				String noti = notifications.getString(2) + ", ID: " + notifications.getInt(1);
				listModel.addElement(noti);
			}
			notiList.setModel(listModel);
		}
	}
}
