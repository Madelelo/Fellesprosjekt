package gui;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

/**
 * 
 * JPanel with GUI for accepting and declining invitations.
 *
 */
public class InvitationPane extends JPanel {
	
	protected static JList<String> invitationList;
	protected static JButton acceptBtn;
	protected static JButton declineBtn;
	
	public InvitationPane() {
		acceptBtn = new JButton("Accept");
		declineBtn = new JButton("Decline");
	}
	
	public void setup() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getInvitations(MainFrame.loggedInAs);
		while(appmnts.next()) {
			String avtale = "Appointment: " + appmnts.getString(1) + ", " + appmnts.getString(2) + ", ID: " + appmnts.getString(3);
			listModel.addElement(avtale);
		}
		invitationList = new JList<String>(listModel);
		invitationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invitationList.setLayoutOrientation(JList.VERTICAL);
		
		setLayout(new GridLayout(2, 2, 5, 5));
		add(new JLabel("Choose invitation:"));
		add(invitationList);
		add(acceptBtn);
		add(declineBtn);
		
	}
	
	public void refresh() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getInvitations(MainFrame.loggedInAs);
		while(appmnts.next()) {
			String avtale = "Avtale: " + appmnts.getString(1) + ", " + appmnts.getString(2) + ", ID: " + appmnts.getString(3);
			listModel.addElement(avtale);
		}
		invitationList.setModel(listModel);
	}

}
