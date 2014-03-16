package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.*;

/**
 * 
 * JFrame that holds all the graphics of the program.
 *
 */
public class MainFrame extends JFrame implements ActionListener {

	private static LoginPane loginPane;
	private static MenuPane menuPane;
	protected static NewAppmntPane newAppmntPane;
	protected static ChangeAppmntPane changeAppmntPane;
	private static InvitationPane invitationPane;
	protected static NotificationPane notificationPane;
	
	private static JPanel responsePane;
	protected static JLabel responseLabel;
	private boolean setupOnce;

	protected static DatabaseConnection db;
	protected static Employee loggedInAs;

	/**
	 * Constructs the JFrame and sets its attributes.
	 */
	public MainFrame() {
		super("Appointment Calendar");
		setSize(800, 500);
		setLayout(new BorderLayout(5, 5));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();
		addActionListeners();
		addResponsePane();

		loginSession();

		setVisible(true);
	}

	/**
	 * Initializes the class variables.
	 */
	public void init() {
		db = new DatabaseConnection();
		loginPane = new LoginPane();
		menuPane = new MenuPane();
		newAppmntPane = new NewAppmntPane();
		changeAppmntPane = new ChangeAppmntPane();
		invitationPane = new InvitationPane();
		notificationPane = new NotificationPane();
		
		responseLabel = new JLabel();
		responsePane = new JPanel();
		
		setupOnce = false;

		if (db.isConnected()) {
			responseLabel.setText("Connected to database.");
		}
	}

	/**
	 * Adds all the actionlisteners for the buttons.
	 */
	public void addActionListeners() {
		LoginPane.loginBtn.addActionListener(this);

		MenuPane.logoutBtn.addActionListener(this);
		MenuPane.newAppmntBtn.addActionListener(this);
		MenuPane.changeAppmntBtn.addActionListener(this);
		MenuPane.invitationBtn.addActionListener(this);
		MenuPane.weekBtn.addActionListener(this);
		MenuPane.alarmBtn.addActionListener(this);

		NewAppmntPane.newAppmntBtn.addActionListener(this);
		NewAppmntPane.findRoomBtn.addActionListener(this);

		ChangeAppmntPane.inviteBtn.addActionListener(this);
		ChangeAppmntPane.removeBtn.addActionListener(this);
		ChangeAppmntPane.saveAppmntBtn.addActionListener(this);
		ChangeAppmntPane.deleteAppmntBtn.addActionListener(this);
		ChangeAppmntPane.chooseAppmntBtn.addActionListener(this);
		ChangeAppmntPane.findRoomBtn.addActionListener(this);
		
		InvitationPane.acceptBtn.addActionListener(this);
		InvitationPane.declineBtn.addActionListener(this);
		
		NotificationPane.newAlarmBtn.addActionListener(this);
		NotificationPane.deleteAlarmBtn.addActionListener(this);
		NotificationPane.deleteNotiBtn.addActionListener(this);
	}

	/**
	 * Executes the login session of the program.
	 */
	public void loginSession() {
		add(loginPane, BorderLayout.NORTH);
	}

	/**
	 * Executes the main session of the program.
	 */
	public void programSession() {
		add(menuPane, BorderLayout.WEST);
		
		if(!setupOnce) {
			newAppmntPane.setup();
			try {
				changeAppmntPane.setup();
				invitationPane.setup();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setupOnce = true;
		}
	}
	
	/**
	 * Adds a JPanel at the bottom where messages to the user is displayed.
	 */
	public void addResponsePane() {
		responsePane.setLayout(new FlowLayout());
		responsePane.add(responseLabel);
		add(responsePane, BorderLayout.SOUTH);
	}
	
	/**
	 * Clears the JFrame for JPanels to avoid adding them multiple times.
	 */
	public void clear() {
		newAppmntPane.clearValues();
		remove(newAppmntPane);
		remove(changeAppmntPane);
		changeAppmntPane.clearValues();
		remove(invitationPane);
		remove(notificationPane);
	}
	
	/**
	 * Method that handles all the things that should happen when buttons is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Log in")) {
			String username = LoginPane.usernameField.getText();
			String password = LoginPane.pwField.getText();
			loggedInAs = db.validateLogin(username, password);

			if (!loggedInAs.getEmail().isEmpty()) {
				responseLabel.setText("You are now logged in.");
				remove(loginPane);
				programSession();
			} else {
				responseLabel.setText("Wrong username and password combination.");
			}
			LoginPane.usernameField.setText("");
			LoginPane.pwField.setText("");
		}

		else if (e.getActionCommand().equals("New appointment")) {
			clear();
			add(newAppmntPane, BorderLayout.CENTER);
		}

		else if (e.getActionCommand().equals("Create appointment")) {
			String start = NewAppmntPane.starttime.getText();
			String end = NewAppmntPane.endtime.getText();
			String date = NewAppmntPane.date.getText();
			String dur = NewAppmntPane.duration.getText();
			String desc = NewAppmntPane.description.getText();
			String loc = NewAppmntPane.location.getText();

			if (db.createAppointment(new Appointment(date, start, end, dur, loc,
					desc), loggedInAs)) {
				responseLabel.setText("Appointment created.");
				clear();
			} else {
				responseLabel.setText("Could not create appointment.");
			}
		}

		else if (e.getActionCommand().equals("Change appointment")) {
			clear();
			try {
				changeAppmntPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			add(changeAppmntPane, BorderLayout.CENTER);
		}
		
		else if (e.getActionCommand().equals("Pick appointment")) {
			String appmnt = ChangeAppmntPane.appmntList.getSelectedValue();
			if(appmnt != null) {
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				ResultSet appmntSet = db.getAppointment(appmntID);
				responseLabel.setText("Appointment " + appmntID + " chosen.");
				
				try {
					changeAppmntPane.putValues(appmntSet);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Delete appointment")) {
			String appmnt = ChangeAppmntPane.appmntList.getSelectedValue();
			if(appmnt != null) {
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.deleteAppointment(appmntID);
				responseLabel.setText("Appointment deleted.");
				changeAppmntPane.clearValues();
				try {
					changeAppmntPane.refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Save appointment")) {
			//clear();
		}
		
		else if (e.getActionCommand().equals("Invite to appointment")) {
			String email = ChangeAppmntPane.notInvitedList.getSelectedValue();
			if(email != null) {
				int appmntID = changeAppmntPane.getCurrenAppmntID();
				db.inviteTo(email, appmntID);
				responseLabel.setText("User invited to appointment.");
				try {
					changeAppmntPane.refreshInviteList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Remove from appointment")) {
			String email = ChangeAppmntPane.invitedList.getSelectedValue().split(" ")[0];
			if(email != null) {
				int appmntID = changeAppmntPane.getCurrenAppmntID();
				db.removeFrom(email, appmntID);
				responseLabel.setText("User removed from appointment.");
				try {
					changeAppmntPane.refreshInviteList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Invitations")) {
			clear();
			try {
				invitationPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			add(invitationPane, BorderLayout.CENTER);
		}
		
		else if (e.getActionCommand().equals("Accept")) {
			String appmnt = InvitationPane.invitationList.getSelectedValue();
			if(appmnt != null) { 
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.confirmInvitation(loggedInAs.getEmail(), appmntID);
				responseLabel.setText("You have accepted the invitation.");
				
				try {
					db.answerNotification(appmntID, loggedInAs.getEmail(), e.getActionCommand());
					invitationPane.refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Decline")) {
			String appmnt = InvitationPane.invitationList.getSelectedValue();
			if(appmnt != null) { 
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.declineInvitation(loggedInAs.getEmail(), appmntID);
				responseLabel.setText("You have declined the invitation.");
				
				try {
					db.answerNotification(appmntID, loggedInAs.getEmail(), e.getActionCommand());
					invitationPane.refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		else if (e.getActionCommand().equals("Show week calendar")) {
			clear();
		}
		
		else if (e.getActionCommand().equals("Notifications")) {
			clear();
			add(notificationPane, BorderLayout.CENTER);
			try {
				notificationPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand().equals("Delete notification")) {
			String noti = NotificationPane.notiList.getSelectedValue();
			if(noti != null) {
				int notiID = Integer.parseInt(noti.split("ID: ")[1]);
				db.deleteNotification(notiID);
				responseLabel.setText("Notification deleted.");
				
				try {
					notificationPane.refresh();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Delete alarm")) {
			String alarm = NotificationPane.alarmList.getSelectedValue();
			if(alarm != null) {
				int alarmID = Integer.parseInt(alarm.split("ID: ")[1]);
				db.deleteNotification(alarmID);
				responseLabel.setText("Alarm deleted");
				
				try {
					notificationPane.refresh();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}

		else if (e.getActionCommand().equals("Log out")) {
			responseLabel.setText("You are now logged out.");
			clear();
			remove(menuPane);
			loginSession();
		}
		
		repaint();
		setVisible(true);
		
		if (e.getActionCommand().equals("Find room")) {
			new FindRoomFrame();
		}
		
		else if (e.getActionCommand().equals("Make new alarm")) {
			try {
				new CreateAlarmFrame();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
