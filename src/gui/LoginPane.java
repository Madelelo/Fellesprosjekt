package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * JPanel with GUI for logging in.
 *
 */
public class LoginPane extends JPanel {
	
	protected static JTextField usernameField;
	protected static JPasswordField pwField;
	protected static JButton loginBtn;
	
	public LoginPane() {
		usernameField = new JTextField(20);
		pwField = new JPasswordField(20);
		loginBtn = new JButton("Log in");
		
		setLayout(new GridLayout(3, 2, 5, 5));
		add(new JLabel("Username:"));
		add(usernameField);
		add(new JLabel("Password:"));
		add(pwField);
		add(new JLabel("Click to log in"));
		add(loginBtn);
	}

}
