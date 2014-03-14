package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

/**
 * 
 * JPanel with GUI for changing appointments.
 *
 */
public class ChangeAppmntPane extends JPanel {
	
	protected static JTextField date;
	protected static JTextField starttime;
	protected static JTextField endtime;
	protected static JTextField duration;
	protected static JTextField location;
	protected static JTextField description;
	protected static JButton chooseAppmntBtn;
	protected static JButton inviteBtn;
	protected static JButton removeBtn;
	protected static JButton saveAppmntBtn;
	protected static JButton deleteAppmntBtn;
	protected static JPanel topPane;
	protected static JPanel midPane;
	protected static JPanel bottomPane;
	protected static JList<String> invitedList;
	protected static JList<String> notInvitedList;
	protected static JList<String> appmntList;
	private int currentAppmntID;
	
	public ChangeAppmntPane() {
		topPane = new JPanel();
		midPane = new JPanel();
		bottomPane = new JPanel();
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		duration = new JTextField();
		location = new JTextField();
		description = new JTextField();
		chooseAppmntBtn = new JButton("Pick appointment");
		inviteBtn = new JButton("Invite to appointment");
		removeBtn = new JButton("Remove from appointment");
		saveAppmntBtn = new JButton("Save appointment");
		deleteAppmntBtn = new JButton("Delete appointment");
	}
	
	public void setup() throws SQLException {
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getAppointmentsBy(MainFrame.loggedInAs);
		if (appmnts != null) {
			while(appmnts.next()) {
				String avtale = "Appointment: " + appmnts.getString(2) + ", " + appmnts.getString(3) + ", ID: " + appmnts.getString(1);
				listModel.addElement(avtale);
			}
		}
		appmntList = new JList<String>(listModel);
		appmntList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appmntList.setLayoutOrientation(JList.VERTICAL);
		
		setLayout(new BorderLayout(5, 5));
		topPane.setLayout(new GridLayout(1, 2, 5, 5));
		midPane.setLayout(new GridLayout(7, 2, 5, 5));
		bottomPane.setLayout(new GridLayout(2, 3, 5, 5));
		
		topPane.add(appmntList);
		topPane.add(chooseAppmntBtn);
		
		midPane.add(new JLabel("Date (YYYY-MM-DD):"));
		midPane.add(date);
		midPane.add(new JLabel("Starttime (HH:MM:SS):"));
		midPane.add(starttime);
		midPane.add(new JLabel("Endtime (HH:MM:SS):"));
		midPane.add(endtime);
		midPane.add(new JLabel("[OR] Duration (H.M):"));
		midPane.add(duration);
		midPane.add(new JLabel("Location:"));
		midPane.add(location);
		midPane.add(new JLabel("Duration:"));
		midPane.add(description);
		midPane.add(deleteAppmntBtn);
		midPane.add(saveAppmntBtn);
		
		add(topPane, BorderLayout.NORTH);
		add(midPane, BorderLayout.CENTER);
	}
	
	public void putValues(ResultSet appmnt) throws SQLException {
		appmnt.next();
		currentAppmntID = appmnt.getInt(1);
		remove(bottomPane);
		bottomPane = new JPanel(new GridLayout(2,3));
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet invited = MainFrame.db.getInvitedEmployees(currentAppmntID);
		if (invited != null) {	
			while(invited.next()) {
				String ansatt = invited.getString(1);
				if(!invited.getBoolean(2)) {ansatt += " (Unanswered)";}
				else if(invited.getBoolean(2) && invited.getBoolean(3)) {ansatt += " (Accepted)";}
				else if((invited.getBoolean(2)) && !(invited.getBoolean(3))) {ansatt += " (Declined)";}
				listModel.addElement(ansatt);
			}
		}
		invitedList = new JList<String>(listModel);
		invitedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invitedList.setLayoutOrientation(JList.VERTICAL);
		
		listModel = new DefaultListModel<String>();
		ResultSet notInvited = MainFrame.db.getUninvitedEmployees(currentAppmntID);
		if (notInvited != null) {
			while(notInvited.next()) {
				String ansatt = notInvited.getString(1);
				listModel.addElement(ansatt);
			}
		}
		notInvitedList = new JList<String>(listModel);
		notInvitedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notInvitedList.setLayoutOrientation(JList.VERTICAL);
		notInvitedList.setBackground(Color.LIGHT_GRAY);
		
		date.setText(appmnt.getDate(2).toString());
		starttime.setText(appmnt.getTime(3).toString());
		endtime.setText(appmnt.getTime(4).toString());
		duration.setText(Double.toString(appmnt.getDouble(5)));
		description.setText(appmnt.getString(6).toString());
		location.setText(appmnt.getString(7).toString());
		
		bottomPane.add(new JLabel("Invited employees:"));
		bottomPane.add(invitedList);
		bottomPane.add(removeBtn);
		bottomPane.add(new JLabel("Uninvited employees:"));
		bottomPane.add(notInvitedList);
		bottomPane.add(inviteBtn);
		add(bottomPane, BorderLayout.SOUTH);
	}
	
	public void clearValues() {
		date.setText("");
		starttime.setText("");
		endtime.setText("");
		duration.setText("");
		description.setText("");
		location.setText("");
		remove(bottomPane);
	}
	
	public void refresh() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getAppointmentsBy(MainFrame.loggedInAs);
		if(appmnts != null) {
			while(appmnts.next()) {
				String avtale = "Appointment: " + appmnts.getString(2) + ", " + appmnts.getString(3) + ", ID: " + appmnts.getString(1);
				listModel.addElement(avtale);
			}
		}
		appmntList.setModel(listModel);
	}
	
	public void refreshInviteList() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet invited = MainFrame.db.getInvitedEmployees(currentAppmntID);
		if(invited != null) {
			while(invited.next()) {
				String ansatt = invited.getString(1);
				if(!invited.getBoolean(2)) {ansatt += " (Unanswered)";}
				else if(invited.getBoolean(2) && invited.getBoolean(3)) {ansatt += " (Accepted)";}
				else if((invited.getBoolean(2)) && !(invited.getBoolean(3))) {ansatt += " (Declined)";}
				listModel.addElement(ansatt);
			}	
		}
		invitedList.setModel(listModel);
		
		listModel = new DefaultListModel<String>();
		ResultSet notInvited = MainFrame.db.getUninvitedEmployees(currentAppmntID);
		if(notInvited != null) {
			while(notInvited.next()) {
				String ansatt = notInvited.getString(1);
				listModel.addElement(ansatt);
			}
		}
		notInvitedList.setModel(listModel);
	}
	
	public int getCurrenAppmntID() {
		return currentAppmntID;
	}
}
