package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	private static JPanel buttonPane;
	
	public InvitationPane() {
		acceptBtn = new JButton("Accept");
		declineBtn = new JButton("Decline");
		buttonPane = new JPanel(new GridLayout(1, 2, 5, 5));
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
		
		setLayout(new BorderLayout());
		add(new JLabel("Choose invitation:"), BorderLayout.NORTH);
		add(invitationList, BorderLayout.CENTER);
		buttonPane.add(acceptBtn);
		buttonPane.add(declineBtn);
		add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setPreferredSize(new Dimension(400, 100));
		
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
