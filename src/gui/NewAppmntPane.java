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


/**
 * 
 * JPanel with GUI for making a new appointment.
 *
 */
public class NewAppmntPane extends JPanel {

	protected static JTextField date;
	protected static JTextField starttime;
	protected static JTextField endtime;
	protected static JTextField duration;
	protected static JTextField location;
	protected static JTextField description;
	protected static JButton newAppmntBtn;
	
	public NewAppmntPane() {
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		duration = new JTextField();
		location = new JTextField();
		description = new JTextField();
		newAppmntBtn = new JButton("Opprett avtale");
	}
	
	public void setup() {
		setLayout(new GridLayout(7, 2, 5, 5));
		
		add(new JLabel("Dato (YYYY-MM-DD):"));
		add(date);
		add(new JLabel("Starttidspunkt (HH:MM:SS):"));
		add(starttime);
		add(new JLabel("Slutttidspunkt (HH:MM:SS):"));
		add(endtime);
		add(new JLabel("[Eller] Lengde (H.M):"));
		add(duration);
		add(new JLabel("Beskrivelse:"));
		add(description);
		add(new JLabel("Sted:"));
		add(location);
		add(newAppmntBtn);
	}
	
	public void clearValues() {
		date.setText("");
		starttime.setText("");
		endtime.setText("");
		duration.setText("");
		location.setText("");
		description.setText("");
	}

}
