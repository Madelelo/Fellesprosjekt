package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * JPanel with GUI for the program menu.
 *
 */
public class MenuPane extends JPanel {
	
	protected static JButton newAppmntBtn;
	protected static JButton changeAppmntBtn;
	protected static JButton invitationBtn;
	protected static JButton weekBtn;
	protected static JButton alarmBtn;
	protected static JButton logoutBtn;
	
	
	public MenuPane() {
		newAppmntBtn = new JButton("New appointment");
		changeAppmntBtn = new JButton("Change appointment");
		invitationBtn = new JButton("Invitations");
		weekBtn = new JButton("Show week calendar");
		alarmBtn = new JButton("Notifications");
		logoutBtn = new JButton("Log out");
		
		
		setLayout(new GridLayout(6,1));
		add(newAppmntBtn);
		add(changeAppmntBtn);
		add(invitationBtn);
		add(weekBtn);
		add(alarmBtn);
		add(logoutBtn);
	}
}
