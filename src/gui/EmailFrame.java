package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Appointment;
import model.EmailLogic;

/**
 * 
 * JFrame that holds the GUI for inviting external users by email.
 *
 */
public class EmailFrame extends JFrame implements ActionListener {
	
	private static JTextField email;
	private static JTextField message;
	private static JButton sendBtn;
	private static JPanel topPane;
	private static JPanel bottomPane;
	private static Appointment appmnt;
	
	public EmailFrame(Appointment a) {
		super("Invite external employee");
		setSize(500, 200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		appmnt = a;
		
		init();
		setup();
		
		setVisible(true);
	}
	
	public void init() {
		email = new JTextField();
		message = new JTextField();
		sendBtn = new JButton("Invite by email");
		topPane = new JPanel(new GridLayout(2, 2, 5, 5));
		bottomPane = new JPanel(new FlowLayout());
	}
	
	public void setup() {
		sendBtn.addActionListener(this);
		topPane.add(new JLabel("E-mail adress:"));
		topPane.add(email);
		topPane.add(new JLabel("Personal message (optional):"));
		topPane.add(message);
		bottomPane.add(sendBtn);
		topPane.setPreferredSize(new Dimension(300, 100));
		add(topPane, BorderLayout.NORTH);
		add(bottomPane, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Invite by email")) {
			if(!email.getText().isEmpty()) {
				String toEmail = email.getText();
				String msg = message.getText();
				
				EmailLogic.sendEmail(toEmail, msg, appmnt);
				MainFrame.db.inviteExternal(toEmail, MainFrame.changeAppmntPane.getCurrenAppmntID());
				email.setText("");
				message.setText("");
				MainFrame.responseLabel.setText("Invitation sent by e-mail.");
				try {
					MainFrame.changeAppmntPane.refreshInviteList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				MainFrame.responseLabel.setText("You must enter an e-mail adress first.");
			}
		}
		
	}

}
