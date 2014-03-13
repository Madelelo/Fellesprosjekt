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
	
	protected static JList invitationList;
	protected static JButton acceptBtn;
	protected static JButton declineBtn;
	
	public InvitationPane() {
		acceptBtn = new JButton("Godta");
		declineBtn = new JButton("Avslå");
	}
	
	public void setup() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getInvitations(MainFrame.loggedInAs);
		while(appmnts.next()) {
			String avtale = "Avtale: " + appmnts.getString(2) + ", " + appmnts.getString(3) + ", ID: " + appmnts.getString(1);
			listModel.addElement(avtale);
		}
		invitationList = new JList<String>(listModel);
		invitationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invitationList.setLayoutOrientation(JList.VERTICAL);
		
		setLayout(new GridLayout(2,2));
		add(new JLabel("Velg invitasjon:"));
		//add(invitationList);
		add(acceptBtn);
		add(declineBtn);
		
	}

}
