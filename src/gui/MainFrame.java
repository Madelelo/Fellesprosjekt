package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		setSize(400, 400);
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
		try {
			newAppmntPane = new NewAppmntPane();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeAppmntPane = new ChangeAppmntPane();
		responseLabel = new JLabel();
		responsePane = new JPanel();
		
		if(db.isConnected()) {
			responseLabel.setText("Koblet til database.");
		}
	}
	
	
	/**
	 * Adds all the actionlisteners for the buttons.
	 */
	public void addActionListeners() {
		loginPane.loginBtn.addActionListener(this);
		
		menuPane.logoutBtn.addActionListener(this);
		menuPane.newAppmntBtn.addActionListener(this);
		menuPane.changeAppmntBtn.addActionListener(this);
		menuPane.weekBtn.addActionListener(this);
		
		newAppmntPane.newAppmntBtn.addActionListener(this);
		
		changeAppmntPane.showAllBtn.addActionListener(this);
		changeAppmntPane.inviteBtn.addActionListener(this);
		changeAppmntPane.saveAppmntBtn.addActionListener(this);
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
		add(menuPane, BorderLayout.NORTH);
	}
	
	public void addResponsePane() {
		responsePane.setLayout(new FlowLayout());
		responsePane.add(responseLabel);
		add(responsePane, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Logg inn")) {
			String username = loginPane.usernameField.getText();
			String password = loginPane.pwField.getText();
			loggedInAs = db.validateLogin(username, password);

			if(!loggedInAs.getEmail().isEmpty()) {
				responseLabel.setText("Du er nå logget inn.");
				remove(loginPane);		
				programSession();
			} else {
				responseLabel.setText("Feil brukernavn eller passord.");
				loginPane.usernameField.setText("");
				loginPane.pwField.setText("");
			}
		}
		
		else if(e.getActionCommand().equals("Ny avtale")) {
			remove(menuPane);
			add(newAppmntPane, BorderLayout.NORTH);
		}
		
		else if(e.getActionCommand().equals("Opprett avtale")) {
			int start = Integer.parseInt(newAppmntPane.starttime.getText());
			int end = Integer.parseInt(newAppmntPane.endtime.getText());
			int date = Integer.parseInt(newAppmntPane.date.getText());
			String loc = newAppmntPane.location.getText();
			String desc = newAppmntPane.description.getText();
			
			if(db.createAppointment(new Appointment(date, start, end, loc, desc), loggedInAs)) {
				responseLabel.setText("Avtale lagt til.");
			} else {
				responseLabel.setText("Kunne ikke legge til avtale.");
			}
			
			remove(newAppmntPane);
			add(menuPane, BorderLayout.NORTH);
		}
		
		else if(e.getActionCommand().equals("Endre avtale")) {
			remove(menuPane);
			add(changeAppmntPane, BorderLayout.NORTH);
		}
		
		else if(e.getActionCommand().equals("Vis ukekalender")) {
	
		}
		
		else if(e.getActionCommand().equals("Logg ut")) {
			responseLabel.setText("Du er nå logget ut.");
			remove(menuPane);
			add(loginPane, BorderLayout.NORTH);
		}
		
	}

}
