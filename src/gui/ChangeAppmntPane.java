package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	protected static JButton findRoomBtn;
	protected static JButton inviteExternalBtn;
	private static JPanel topPane;
	private static JPanel midPane;
	private static JPanel bottomPane;
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
		location.setEditable(false);
		description = new JTextField();
		invitedList = new JList<String>();
		notInvitedList = new JList<String>();
		appmntList = new JList<String>();
		chooseAppmntBtn = new JButton("Pick appointment");
		inviteBtn = new JButton("Invite to appointment");
		removeBtn = new JButton("Remove from appointment");
		saveAppmntBtn = new JButton("Save appointment");
		deleteAppmntBtn = new JButton("Delete appointment");
		findRoomBtn = new JButton("Change room");
		inviteExternalBtn = new JButton("Invite external employee");
		currentAppmntID = -1;
	}
	
	public void setup() throws SQLException {
		appmntList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appmntList.setLayoutOrientation(JList.VERTICAL);
		invitedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invitedList.setLayoutOrientation(JList.VERTICAL);
		notInvitedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notInvitedList.setLayoutOrientation(JList.VERTICAL);
		
		setLayout(new BorderLayout(5, 5));
		topPane.setLayout(new GridLayout(1, 2, 5, 5));
		midPane.setLayout(new GridLayout(8, 2, 5, 5));
		
		topPane.add(appmntList);
		topPane.add(chooseAppmntBtn);
		
		midPane.add(new JLabel("Date (YYYY-MM-DD):"));
		midPane.add(date);
		midPane.add(new JLabel("Starttime (HH:MM:SS):"));
		midPane.add(starttime);
		midPane.add(new JLabel("Endtime (HH:MM:SS):"));
		midPane.add(endtime);
		midPane.add(new JLabel("[OR] Duration (H:MM):"));
		midPane.add(duration);
		midPane.add(new JLabel("Location:"));
		midPane.add(location);
		midPane.add(new JLabel(""));
		midPane.add(findRoomBtn);
		midPane.add(new JLabel("Description:"));
		midPane.add(description);
		midPane.add(deleteAppmntBtn);
		midPane.add(saveAppmntBtn);
		
		add(topPane, BorderLayout.NORTH);
		add(midPane, BorderLayout.CENTER);
		
		refresh();
	}
	
	/**
	 * Puts the values from the appointment into the text fields.
	 * 
	 * @param ResultSet appmnt
	 * @throws SQLException
	 */
	public void putValues(ResultSet appmnt) throws SQLException {
		appmnt.next();
		currentAppmntID = appmnt.getInt(1);
		remove(bottomPane);
		bottomPane = new JPanel(new GridLayout(3, 3, 5, 5));
		bottomPane.setPreferredSize(new Dimension(500, 200));
		
		date.setText(appmnt.getDate(2).toString());
		starttime.setText(appmnt.getTime(3).toString());
		if(appmnt.getObject(4) != null) {
			endtime.setText(appmnt.getTime(4).toString());
		} else {
			endtime.setText("");
		}
		duration.setText(appmnt.getString(5));
		description.setText(appmnt.getString(6).toString());
		location.setText(appmnt.getString(7).toString());
		
		bottomPane.add(new JLabel("Invited employees:"));
		bottomPane.add(new JScrollPane(invitedList));
		bottomPane.add(removeBtn);
		bottomPane.add(new JLabel("Uninvited employees:"));
		bottomPane.add(new JScrollPane(notInvitedList));
		bottomPane.add(inviteBtn);
		bottomPane.add(inviteExternalBtn);
		bottomPane.add(new JLabel(""));
		bottomPane.add(new JLabel(""));
		add(bottomPane, BorderLayout.SOUTH);
		
		refreshInviteList();
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
		ResultSet invitedExternal = MainFrame.db.getInvitedExternalEmployees(currentAppmntID);
		if(invited != null) {
			while(invited.next()) {
				String ansatt = invited.getString(1);
				if(!invited.getBoolean(2)) {ansatt += " (Unanswered)";}
				else if(invited.getBoolean(2) && invited.getBoolean(3)) {ansatt += " (Accepted)";}
				else if((invited.getBoolean(2)) && !(invited.getBoolean(3))) {ansatt += " (Declined)";}
				listModel.addElement(ansatt);
			}
			while(invitedExternal.next()) {
				String ekstern = invitedExternal.getString(1) + " (External)";
				listModel.addElement(ekstern);
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
	
	/**
	 * Returns the appointmentID to the current selected appointment.
	 * Returns -1 if no appointment is selected.
	 * 
	 * @return
	 */
	public int getCurrenAppmntID() {
		return currentAppmntID;
	}
}
