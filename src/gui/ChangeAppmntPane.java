package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeAppmntPane extends JPanel {
	
	protected static JTextField date;
	protected static JTextField starttime;
	protected static JTextField endtime;
	protected static JTextField location;
	protected static JTextField description;
	protected static JTextField invite;
	protected static JButton showAllBtn;
	protected static JButton inviteBtn;
	protected static JButton saveAppmntBtn;
	
	public ChangeAppmntPane() {
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		location = new JTextField();
		description = new JTextField();
		invite = new JTextField();
		showAllBtn = new JButton("Vis alle");
		inviteBtn = new JButton("Inviter til avtale");
		saveAppmntBtn = new JButton("Lagre avtale");
		
		setLayout(new GridLayout(8, 2));
		
		add(new JLabel("Dato (YYYYMMDD):"));
		add(date);
		add(new JLabel("Starttidspunkt (HHMMSS):"));
		add(starttime);
		add(new JLabel("Slutttidspunkt (HHMMSS):"));
		add(endtime);
		add(new JLabel("Sted:"));
		add(location);
		add(new JLabel("Beskrivelse:"));
		add(description);
		add(new JLabel("Inviter ansatt:"));
		add(invite);
		add(showAllBtn);
		add(inviteBtn);
		add(saveAppmntBtn);
	}
}
