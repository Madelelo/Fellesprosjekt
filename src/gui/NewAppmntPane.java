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
	protected static JButton findRoomBtn;
	
	public NewAppmntPane() {
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		duration = new JTextField();
		location = new JTextField();
		location.setEditable(false);
		description = new JTextField();
		newAppmntBtn = new JButton("Create appointment");
		findRoomBtn = new JButton("Find room");
	}
	
	public void setup() {
		setLayout(new GridLayout(8, 2, 5, 5));
		
		add(new JLabel("Date (YYYY-MM-DD):"));
		add(date);
		add(new JLabel("Starttime (HH:MM:SS):"));
		add(starttime);
		add(new JLabel("Endtime (HH:MM:SS):"));
		add(endtime);
		add(new JLabel("[OR] Duration (H:M):"));
		add(duration);
		add(new JLabel("Location:"));
		add(location);
		add(new JLabel(""));
		add(findRoomBtn);
		add(new JLabel("Description:"));
		add(description);
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
