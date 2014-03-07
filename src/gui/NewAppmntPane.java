package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewAppmntPane extends JPanel {

	protected static JTextField starttime;
	protected static JTextField endtime;
	protected static JTextField location;
	protected static JTextField description;
	protected static JButton newAppmntBtn;
	
	public NewAppmntPane() {
		starttime = new JTextField();
		endtime = new JTextField();
		location = new JTextField();
		description = new JTextField();
		newAppmntBtn = new JButton("Opprett avtale");
		
		setLayout(new GridLayout(6, 2));
		
		add(new JLabel("Starttidspunkt:"));
		add(starttime);
		add(new JLabel("Slutttidspunkt:"));
		add(endtime);
		add(new JLabel("Sted:"));
		add(location);
		add(new JLabel("Beskrivelse:"));
		add(description);
		add(newAppmntBtn);
	}

}
