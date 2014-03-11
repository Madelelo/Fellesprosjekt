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
	protected static JList employeeList;
	protected static JPanel pane;
	
	public NewAppmntPane() throws SQLException {
		pane = new JPanel();
		date = new JTextField();
		starttime = new JTextField();
		endtime = new JTextField();
		location = new JTextField();
		description = new JTextField();
		newAppmntBtn = new JButton("Opprett avtale");
		inviteBtn = new JButton("Inviter ansatte");
		
		DefaultListModel listModel = new DefaultListModel();
		ResultSet employees = MainFrame.db.getEmployees();
		while(employees.next()) {
			String ansatt = employees.getString(2);
			listModel.addElement(ansatt);
		}
		
		employeeList = new JList(listModel);
		employeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		employeeList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(employeeList);
		
		setLayout(new BorderLayout());
		pane.setLayout(new GridLayout(6, 1));
		
		pane.add(new JLabel("Dato (YYYYMMDD):"));
		pane.add(date);
		pane.add(new JLabel("Starttidspunkt (HHMMSS):"));
		pane.add(starttime);
		pane.add(new JLabel("Slutttidspunkt (HHMMSS):"));
		pane.add(endtime);
		pane.add(new JLabel("Sted:"));
		pane.add(location);
		pane.add(new JLabel("Beskrivelse:"));
		pane.add(description);
		pane.add(newAppmntBtn);
		add(pane, BorderLayout.NORTH);
		add(employeeList, BorderLayout.SOUTH);
		add(inviteBtn, BorderLayout.EAST);
	}

}
