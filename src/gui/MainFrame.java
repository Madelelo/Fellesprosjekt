package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.*;

public class MainFrame extends JFrame implements ActionListener {

	private static LoginPane loginPane;
	private static MenuPane menuPane;
	private static NewAppmntPane newAppmntPane;
	private static ChangeAppmntPane changeAppmntPane;
	private static JPanel responsePane;
	private static JLabel responseLabel;

	protected static DatabaseConnection db;
	protected static Employee loggedInAs;

	public MainFrame() {
		super("Avtalekalender");
		setSize(800, 800);
		setLayout(new BorderLayout());
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

		try {
			changeAppmntPane = new ChangeAppmntPane();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		responseLabel = new JLabel();
		responsePane = new JPanel();

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
		MenuPane.weekBtn.addActionListener(this);

		NewAppmntPane.newAppmntBtn.addActionListener(this);

		ChangeAppmntPane.inviteBtn.addActionListener(this);
		ChangeAppmntPane.saveAppmntBtn.addActionListener(this);
	}

	/**
	 * Executes the login session of the program.
	 */
	public void loginSession() {
		add(loginPane, BorderLayout.NORTH);
	}

	/**
	 * Executes the main session of the program.
	 * @throws SQLException 
	 */
	public void programSession() {
		add(menuPane, BorderLayout.WEST);
		newAppmntPane.setup();
		try {
			changeAppmntPane.setup();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addResponsePane() {
		responsePane.setLayout(new FlowLayout());
		responsePane.add(responseLabel);
		add(responsePane, BorderLayout.SOUTH);
	}
	
	public void clear() {
		remove(newAppmntPane);
		remove(changeAppmntPane);
	}

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
				LoginPane.usernameField.setText("");
				LoginPane.pwField.setText("");
			}
		}

		else if (e.getActionCommand().equals("Ny avtale")) {
			clear();
			add(newAppmntPane, BorderLayout.CENTER);
		}

		else if (e.getActionCommand().equals("Opprett avtale")) {
			int start = Integer.parseInt(NewAppmntPane.starttime.getText());
			int end = Integer.parseInt(NewAppmntPane.endtime.getText());
			int date = Integer.parseInt(NewAppmntPane.date.getText());
			String loc = NewAppmntPane.location.getText();
			String desc = NewAppmntPane.description.getText();

			if (db.createAppointment(new Appointment(date, start, end, loc,
					desc), loggedInAs)) {
				responseLabel.setText("Avtale lagt til.");
			} else {
				responseLabel.setText("Kunne ikke legge til avtale.");
			}

			clear();
		}

		else if (e.getActionCommand().equals("Endre avtale")) {
			clear();
			add(changeAppmntPane, BorderLayout.CENTER);
		}

		else if (e.getActionCommand().equals("Vis ukekalender")) {
			clear();
		}

		else if (e.getActionCommand().equals("Logg ut")) {
			responseLabel.setText("Du er nå logget ut.");
			clear();
			remove(menuPane);
			loginSession();
		}

	}

}
