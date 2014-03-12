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
		newAppmntBtn = new JButton("Ny avtale");
		changeAppmntBtn = new JButton("Endre avtale");
		invitationBtn = new JButton("Invitasjoner");
		weekBtn = new JButton("Vis ukekalender");
		alarmBtn = new JButton("Se varsler");
		logoutBtn = new JButton("Logg ut");
		
		
		setLayout(new GridLayout(6,1));
		add(newAppmntBtn);
		add(changeAppmntBtn);
		add(invitationBtn);
		add(weekBtn);
		add(alarmBtn);
		add(logoutBtn);
	}
}
