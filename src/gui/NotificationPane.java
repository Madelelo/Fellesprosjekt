package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.weighty = 0.005;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel("Notifications:"), c);
		
		c.gridy = 1;
		c.gridwidth = 3;
		c.weighty = 0.03;
		c.fill = GridBagConstraints.BOTH;
		add(notiList, c);
		
		c.gridy = 2;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(deleteNotiBtn, c);
		
		c.gridy = 3;
		c.weighty = 0.005;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel("Alarms:"), c);
		
		c.gridy = 4;
		c.gridwidth = 3;
		c.weighty = 0.03;
		c.fill = GridBagConstraints.BOTH;
		add(alarmList, c);
		
		c.gridy = 5;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(newAlarmBtn, c);
		
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(deleteAlarmBtn, c);

	}

	public void refresh() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		ResultSet notifications = MainFrame.db.getNotifications(MainFrame.loggedInAs);
		if(notifications != null) {
			listModel = new DefaultListModel<String>();
			while (notifications.next()) {
				String noti = notifications.getString(2) + ", ID: " + notifications.getInt(1);
				listModel.addElement(noti);
			}
			notiList.setModel(listModel);
		}
		
		ResultSet alarms = MainFrame.db.getAlarms(MainFrame.loggedInAs);
		if(alarms != null) {
			listModel = new DefaultListModel<String>();
			while(alarms.next()) {
				String alarm = alarms.getString(2) + ", " + alarms.getDate(3).toString() + ", "
						+ alarms.getTime(4).toString() + ", ID: " + alarms.getInt(1);
				listModel.addElement(alarm);
			}
			alarmList.setModel(listModel);
		}
	}
}
