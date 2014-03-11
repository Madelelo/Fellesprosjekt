package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class NewAppmntPane extends JPanel {

	protected static JTextField date;
	protected static JTextField starttime;
	protected static JTextField endtime;
	protected static JTextField location;
	protected static JTextField description;
	protected static JButton newAppmntBtn;
	protected static JButton inviteBtn;
	
	public NewAppmntPane() {
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		location = new JTextField();
		description = new JTextField();
		newAppmntBtn = new JButton("Opprett avtale");
		inviteBtn = new JButton("Inviter ansatte");
	}
	
	public void setup() {
		setLayout(new GridLayout(6, 1));
		
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
		add(newAppmntBtn);
	}

}
