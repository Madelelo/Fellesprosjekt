package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPane extends JPanel {
	
	protected static JTextField usernameField;
	protected static JTextField pwField;
	protected static JButton loginBtn;
	
	public LoginPane() {
		usernameField = new JTextField(10);
		pwField = new JTextField(10);
		loginBtn = new JButton("Logg inn");
		
		setLayout(new GridLayout(3,2));
		add(new JLabel("Brukernavn:"));
		add(usernameField);
		add(new JLabel("Passord:"));
		add(pwField);
		add(new JLabel("Klikk for � logge inn"));
		add(loginBtn);
	}

}
