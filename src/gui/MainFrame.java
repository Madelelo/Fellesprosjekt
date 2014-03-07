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

public class MainFrame extends JFrame implements ActionListener {
	
	private static JPanel loginPane;
	private static JTextField usernameField;
	private static JTextField pwField;
	
	private static JPanel buttonPane;
	private static JButton loginBtn;
	
	private static JPanel responsePane;
	private static JLabel responseLabel;
	
	private static JPanel programPane;
	
	private static Database db;
	
	public MainFrame() {
		super("Avtalekalender");
		setSize(400, 400);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		db = new Database();
		
		loginSession();
		
		setVisible(true);
	}
	
	public void loginSession() {
		usernameField = new JTextField(10);
		pwField = new JTextField(10);
		loginBtn = new JButton("Logg inn");
		loginBtn.addActionListener(this);
		responseLabel = new JLabel();
		
		
		loginPane = new JPanel();
		loginPane.setLayout(new GridLayout(2,2));
		loginPane.add(new JLabel("Brukernavn:"));
		loginPane.add(usernameField);
		loginPane.add(new JLabel("Passord:"));
		loginPane.add(pwField);
		add(loginPane, BorderLayout.NORTH);
		
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.add(loginBtn);
		add(buttonPane, BorderLayout.CENTER);
		
		responsePane = new JPanel();
		responsePane.setLayout(new FlowLayout());
		responsePane.add(responseLabel);
		add(responsePane, BorderLayout.SOUTH);
	}
	
	public void programSession() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Logg inn")) {
			String username = usernameField.getText();
			String password = pwField.getText();
			
			String qry = "SELECT username, password FROM employee WHERE username='" + username + "' AND password='" + password + "';";
			
			ResultSet result = db.readQuery(qry);
			try {
				if(result.next()) {
					responseLabel.setText("Du er nå logget inn.");
					remove(loginPane);
					remove(buttonPane);
					
					programSession();
				} else {
					responseLabel.setText("Feil brukernavn eller passord.");
					usernameField.setText("");
					pwField.setText("");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
