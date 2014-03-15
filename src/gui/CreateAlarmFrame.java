package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.sun.imageio.stream.StreamCloser.CloseAction;

/**
 * 
 * JFrame for making new alarm.
 *
 */
public class CreateAlarmFrame extends JFrame implements ActionListener {
	
	private static JPanel topPane;
	private static JPanel midPane;
	private static JPanel bottomPane;
	
	private static JTextField alarmMsg;
	private static JTextField alarmTime;
	
	private static JList<String> appmntList;
	
	private static JButton appmntBtn;
	private static JButton newAlarmBtn;
	
	private int appmntID;
	
	public CreateAlarmFrame() throws SQLException {
		super("Make new alarm");
		setSize(500, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		init();
		setup();
		refresh();
	}
	
	public void init() {
		topPane = new JPanel(new GridLayout(1,2));
		midPane = new JPanel(new GridLayout(2, 2));
		bottomPane = new JPanel(new GridLayout(1, 1));
		
		alarmMsg = new JTextField();
		alarmTime = new JTextField();
		appmntList = new JList<String>();
		appmntBtn = new JButton("Pick appointment");
		newAlarmBtn = new JButton("Create alarm");
		
		appmntID = -1;
		
		appmntBtn.addActionListener(this);
		newAlarmBtn.addActionListener(this);
	}
	
	public void setup() {
		topPane.add(appmntList);
		topPane.add(appmntBtn);
		
		midPane.add(new JLabel("Alarm message:"));
		midPane.add(alarmMsg);
		midPane.add(new JLabel("Alarm time (HH:MM:SS):"));
		midPane.add(alarmTime);
		
		bottomPane.add(newAlarmBtn);
		
		add(topPane, BorderLayout.NORTH);
		add(midPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public void refresh() throws SQLException {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		ResultSet appmnts = MainFrame.db.getAcceptedAppointments(MainFrame.loggedInAs);
		if (appmnts != null) {
			while(appmnts.next()) {
				String avtale = "Appointment: " + appmnts.getString(2) + ", " + appmnts.getString(3) + ", ID: " + appmnts.getString(1);
				listModel.addElement(avtale);
			}
		}
		appmntList.setModel(listModel);
		appmntList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		appmntList.setLayoutOrientation(JList.VERTICAL);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Pick appointment")) {
			String appmnt = appmntList.getSelectedValue();
			if(appmnt != null) {
				setAppmntID(Integer.parseInt(appmnt.split("ID: ")[1]));
			}
		}
		
		else if(e.getActionCommand().equals("Create alarm")) {
			String msg = alarmMsg.getText();
			String time = alarmTime.getText();
			String email = MainFrame.loggedInAs.getEmail();
			
			if(!(time.isEmpty() || msg.isEmpty() || getAppmntID() == -1)) {
				MainFrame.db.createAlarm(msg, time, getAppmntID(), email);
				MainFrame.responseLabel.setText("New alarm created.");
				try {
					MainFrame.notificationPane.refresh();
				} catch (SQLException ex) {
					System.out.println("Could not refresh notifications.");
					System.err.println(ex.getMessage());
				}
				dispose();
			}
		}
		
	}
	
	private void setAppmntID(int appmntID) {
		this.appmntID = appmntID;
	}
	
	private int getAppmntID() {
		return appmntID;
	}

}
