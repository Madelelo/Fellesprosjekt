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
	private static NewAppmntPane newAppmntPane;
	private static ChangeAppmntPane changeAppmntPane;
	private static InvitationPane invitationPane;
	private static NotificationPane notificationPane;
	
	private static JPanel responsePane;
	private static JLabel responseLabel;
	private boolean setupOnce;

	protected static DatabaseConnection db;
	protected static Employee loggedInAs;

	/**
	 * Constructs the JFrame and sets its attributes.
	 */
	public MainFrame() {
		super("Avtalekalender");
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
			responseLabel.setText("Koblet til database.");
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

		ChangeAppmntPane.inviteBtn.addActionListener(this);
		ChangeAppmntPane.removeBtn.addActionListener(this);
		ChangeAppmntPane.saveAppmntBtn.addActionListener(this);
		ChangeAppmntPane.deleteAppmntBtn.addActionListener(this);
		ChangeAppmntPane.chooseAppmntBtn.addActionListener(this);
		
		InvitationPane.acceptBtn.addActionListener(this);
		InvitationPane.declineBtn.addActionListener(this);
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
				notificationPane.setup();
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
		
		if (e.getActionCommand().equals("Logg inn")) {
			String username = LoginPane.usernameField.getText();
			String password = LoginPane.pwField.getText();
			loggedInAs = db.validateLogin(username, password);

			if (!loggedInAs.getEmail().isEmpty()) {
				responseLabel.setText("Du er nå logget inn.");
				remove(loginPane);
				programSession();
			} else {
				responseLabel.setText("Feil brukernavn eller passord.");
			}
			LoginPane.usernameField.setText("");
			LoginPane.pwField.setText("");
		}

		else if (e.getActionCommand().equals("Ny avtale")) {
			clear();
			add(newAppmntPane, BorderLayout.CENTER);
		}

		else if (e.getActionCommand().equals("Opprett avtale")) {
			String start = NewAppmntPane.starttime.getText();
			String end = NewAppmntPane.endtime.getText();
			String date = NewAppmntPane.date.getText();
			String dur = NewAppmntPane.duration.getText();
			String desc = NewAppmntPane.description.getText();
			String loc = NewAppmntPane.location.getText();

			if (db.createAppointment(new Appointment(date, start, end, dur, loc,
					desc), loggedInAs)) {
				responseLabel.setText("Avtale lagt til.");
				clear();
			} else {
				responseLabel.setText("Kunne ikke legge til avtale.");
			}
		}

		else if (e.getActionCommand().equals("Endre avtale")) {
			clear();
			try {
				changeAppmntPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			add(changeAppmntPane, BorderLayout.CENTER);
		}
		
		else if (e.getActionCommand().equals("Velg avtale")) {
			String appmnt = ChangeAppmntPane.appmntList.getSelectedValue();
			if(appmnt != null) {
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				ResultSet appmntSet = db.getAppointment(appmntID);
				responseLabel.setText("Avtale " + appmntID + " valgt.");
				
				try {
					changeAppmntPane.putValues(appmntSet);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Slett avtale")) {
			String appmnt = ChangeAppmntPane.appmntList.getSelectedValue();
			if(appmnt != null) {
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.deleteAppointment(appmntID);
				responseLabel.setText("Avtale ble slettet");
				changeAppmntPane.clearValues();
				try {
					changeAppmntPane.refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Lagre avtale")) {
			//clear();
		}
		
		else if (e.getActionCommand().equals("Inviter til avtale")) {
			String email = ChangeAppmntPane.notInvitedList.getSelectedValue();
			if(email != null) {
				int appmntID = changeAppmntPane.getCurrenAppmntID();
				db.inviteTo(email, appmntID);
				responseLabel.setText("Brukeren ble invitert til avtale.");
				try {
					changeAppmntPane.refreshInviteList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Fjern fra avtale")) {
			String email = ChangeAppmntPane.invitedList.getSelectedValue();
			if(email != null) {
				int appmntID = changeAppmntPane.getCurrenAppmntID();
				db.removeFrom(email, appmntID);
				responseLabel.setText("Brukeren ble fjernet fra avtale.");
				try {
					changeAppmntPane.refreshInviteList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getActionCommand().equals("Invitasjoner")) {
			clear();
			try {
				invitationPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			add(invitationPane, BorderLayout.CENTER);
		}
		
		else if (e.getActionCommand().equals("Godta")) {
			String appmnt = InvitationPane.invitationList.getSelectedValue();
			if(appmnt != null) { 
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.confirmInvitation(loggedInAs.getEmail(), appmntID);
				responseLabel.setText("Du har godtatt invitasjonen.");
			}
			
			try {
				invitationPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if (e.getActionCommand().equals("Avslå")) {
			String appmnt = InvitationPane.invitationList.getSelectedValue();
			if(appmnt != null) { 
				int appmntID = Integer.parseInt(appmnt.split("ID: ")[1]);
				db.declineInvitation(loggedInAs.getEmail(), appmntID);
				responseLabel.setText("Du har avslått invitasjonen.");
			}
			
			try {
				invitationPane.refresh();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		else if (e.getActionCommand().equals("Vis ukekalender")) {
			clear();
		}
		
		else if (e.getActionCommand().equals("Se varsler")) {
			clear();
			add(notificationPane, BorderLayout.CENTER);
		}

		else if (e.getActionCommand().equals("Logg ut")) {
			responseLabel.setText("Du er nå logget ut.");
			clear();
			remove(menuPane);
			loginSession();
		}
		
		repaint();
		setVisible(true);
	}

}
