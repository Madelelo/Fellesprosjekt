package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPane extends JPanel {
	
	protected static JButton newAppmntBtn;
	protected static JButton changeAppmntBtn;
	protected static JButton weekBtn;
	protected static JButton logoutBtn;
	protected static JButton alarmBtn;
	
	public MenuPane() {
		newAppmntBtn = new JButton("Ny avtale");
		changeAppmntBtn = new JButton("Endre avtale");
		weekBtn = new JButton("Vis ukekalender");
		logoutBtn = new JButton("Logg ut");
		alarmBtn = new JButton("Se varsler");
		
		setLayout(new GridLayout(5,1));
		add(newAppmntBtn);
		add(changeAppmntBtn);
		add(weekBtn);
		add(logoutBtn);
		add(alarmBtn);
	}

}
