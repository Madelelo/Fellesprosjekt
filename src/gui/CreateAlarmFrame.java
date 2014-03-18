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

import model.TimeLogic;

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
	
	private static JLabel currentAppmnt;
	
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
		midPane = new JPanel(new GridLayout(3, 2));
		bottomPane = new JPanel(new GridLayout(1, 1));
		
		alarmMsg = new JTextField();
		alarmTime = new JTextField();
		appmntList = new JList<String>();
		appmntBtn = new JButton("Pick appointment");
		newAlarmBtn = new JButton("Create alarm");
		currentAppmnt = new JLabel("");
		
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
		midPane.add(currentAppmnt);
		
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
				currentAppmnt.setText("Appointment " + appmntID + " is picked");
			}
		}
		
		else if(e.getActionCommand().equals("Create alarm")) {
			String msg = alarmMsg.getText();
			String time = alarmTime.getText();
			String email = MainFrame.loggedInAs.getEmail();
			ResultSet date = null;
			ResultSet starttime = null;
			String appmntDate = "";
			String appmntStarttime = "";	
			
			if(!(time.isEmpty() || msg.isEmpty() || appmntID == -1)) {
				
				try {
					date = MainFrame.db.getAppointmentDate(appmntID);
					starttime = MainFrame.db.getAppointmentStarttime(appmntID);
					
					date.next();
					starttime.next();
					
					if(date != null && starttime != null) {
						appmntDate = date.getDate(1).toString();
						appmntStarttime = starttime.getTime(1).toString();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				if(TimeLogic.isValidAlarmTime(time, appmntDate, appmntStarttime)) {
					MainFrame.db.createAlarm(msg, time, appmntID, email);
					MainFrame.responseLabel.setText("New alarm created.");
					
					try {
						MainFrame.notificationPane.refresh();
					} catch (SQLException ex) {
						System.out.println("Could not refresh notifications.");
						System.err.println(ex.getMessage());
					}
					dispose();
					
				} else {
					MainFrame.responseLabel.setText("Could not create alarm. Alarmtime set after scheduled appointment.");
				}
			} else {
				MainFrame.responseLabel.setText("Could not create alarm. Pick an appointment and fill out fields correctly.");
			}
		}
		
	}
	
	private void setAppmntID(int appmntID) {
		this.appmntID = appmntID;
	}

}
