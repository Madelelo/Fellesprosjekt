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
	protected static JButton saveAppmntBtn;
	protected static JPanel topPane;
	protected static JPanel midPane;
	protected static JPanel bottomPane;
	protected static JList<String> invitedList;
	protected static JList<String> notInvitedList;
	protected static JList<String> appmntList;
	
	public ChangeAppmntPane() throws SQLException {
		topPane = new JPanel();
		midPane = new JPanel();
		bottomPane = new JPanel();
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		duration = new JTextField();
		location = new JTextField();
		description = new JTextField();
		chooseAppmntBtn = new JButton("Velg avtale");
		inviteBtn = new JButton("Inviter til avtale");
		saveAppmntBtn = new JButton("Lagre avtale");
	}
	
	public void setup() throws SQLException {
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getAppointmentsBy(MainFrame.loggedInAs);
		while(appmnts.next()) {
			String avtale = "Avtale: " + appmnts.getString(2) + ", " + appmnts.getString(3) + ", ID: " + appmnts.getString(1);
			listModel.addElement(avtale);
		}
		appmntList = new JList<String>(listModel);
		appmntList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appmntList.setLayoutOrientation(JList.VERTICAL);
		
		setLayout(new BorderLayout());
		topPane.setLayout(new GridLayout(1, 2));
		midPane.setLayout(new GridLayout(7, 2));
		bottomPane.setLayout(new GridLayout(1, 3));
		
		topPane.add(appmntList);
		topPane.add(chooseAppmntBtn);
		
		midPane.add(new JLabel("Dato (YYYY-MM-DD):"));
		midPane.add(date);
		midPane.add(new JLabel("Starttidspunkt (HH:MM:SS):"));
		midPane.add(starttime);
		midPane.add(new JLabel("Slutttidspunkt (HH:MM:SS):"));
		midPane.add(endtime);
		midPane.add(new JLabel("[Eller] Lengde (H.M):"));
		midPane.add(duration);
		midPane.add(new JLabel("Sted:"));
		midPane.add(location);
		midPane.add(new JLabel("Beskrivelse:"));
		midPane.add(description);
		midPane.add(saveAppmntBtn);
		
		add(topPane, BorderLayout.NORTH);
		add(midPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
	}
	
	public void putValues(ResultSet appmnt) throws SQLException {
		appmnt.next();
		int appmntID = appmnt.getInt(1);
		
//		DefaultListModel<String> listModel = new DefaultListModel<String>();
//		ResultSet invited = MainFrame.db.getInvitedEmployees(appmntID);
//		while(invited.next()) {
//			String ansatt = invited.getString(2);
//			listModel.addElement(ansatt);
//		}
//		invitedList = new JList<String>(listModel);
//		invitedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		invitedList.setLayoutOrientation(JList.VERTICAL);
//		
//		listModel = new DefaultListModel<String>();
//		ResultSet notInvited = MainFrame.db.getUninvitedEmployees(appmntID);
//		while(notInvited.next()) {
//			String ansatt = notInvited.getString(2);
//			listModel.addElement(ansatt);
//		}
//		notInvitedList = new JList<String>(listModel);
//		notInvitedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		notInvitedList.setLayoutOrientation(JList.VERTICAL);
		
		date.setText(appmnt.getDate(2).toString());
		starttime.setText(appmnt.getTime(3).toString());
		endtime.setText(appmnt.getTime(4).toString());
		duration.setText(Double.toString(appmnt.getDouble(5)));
		description.setText(appmnt.getString(6).toString());
		location.setText(appmnt.getString(7).toString());
		
//		bottomPane.add(invitedList);
//		bottomPane.add(notInvitedList);
		bottomPane.add(inviteBtn);
	}
}
