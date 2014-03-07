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

import database.Database;
import model.*;

public class MainFrame extends JFrame implements ActionListener {
	
	private static LoginPane loginPane;
	private static MenuPane menuPane;
	private static NewAppmntPane newAppmntPane;
	
	private static JPanel responsePane;
	private static JLabel responseLabel;
	
	private static Database db;
	
	public MainFrame() {
		super("Avtalekalender");
		setSize(400, 400);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		
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
		db = new Database();
		loginPane = new LoginPane();
		menuPane = new MenuPane();
		newAppmntPane = new NewAppmntPane();
		responseLabel = new JLabel();
		responsePane = new JPanel();
		
		try {
			if(db.getConnection().isValid(3)) {
				responseLabel.setText("Koblet til database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			
			String qry = "SELECT username, password FROM employee WHERE username='" + username + "' AND password='" + password + "';";
			
			ResultSet result = db.readQuery(qry);
			try {
				if(result.next()) {
					responseLabel.setText("Du er nå logget inn.");
					remove(loginPane);
					
					programSession();
				} else {
					responseLabel.setText("Feil brukernavn eller passord.");
					loginPane.usernameField.setText("");
					loginPane.pwField.setText("");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(e.getActionCommand().equals("Ny avtale")) {
			remove(menuPane);
			add(newAppmntPane, BorderLayout.NORTH);
		}
		
		else if(e.getActionCommand().equals("Opprett avtale")) {
			int start = Integer.parseInt(newAppmntPane.starttime.getText());
			int end = Integer.parseInt(newAppmntPane.endtime.getText());
			String loc = newAppmntPane.location.getText();
			String desc = newAppmntPane.description.getText();
			
			Appointment appointment = new Appointment(start, end, loc, desc);
			
			remove(newAppmntPane);
			add(menuPane, BorderLayout.NORTH);
		}
		
		else if(e.getActionCommand().equals("Endre avtale")) {
			
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
