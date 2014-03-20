package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.TimeLogic;

/**
 * 
 * JFrame for showing available rooms for an appointment.
 *
 */
public class FindRoomFrame extends JFrame implements ListSelectionListener, ActionListener {
	
	private JList<String> roomList;
	
	private JPanel topPane;
	private JPanel bottomPane;
	
	private JButton okBtn;
	
	protected int chosenPane;
	public static final int NEW_APPMNT = 0, CHANGE_APPMNT = 1;
	
	public FindRoomFrame(int chosenPane) throws SQLException {
		super("Available rooms");
		setSize(300, 280);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(15, 15));
		
		this.chosenPane = chosenPane;
		
		init();
		setup();
		
		if(this.chosenPane == NEW_APPMNT) {
			if(!MainFrame.newAppmntPane.date.getText().isEmpty() && !MainFrame.newAppmntPane.starttime.getText().isEmpty()
					&& (!MainFrame.newAppmntPane.endtime.getText().isEmpty() || !MainFrame.newAppmntPane.duration.getText().isEmpty())) {
				refresh();
			}
		} else if(this.chosenPane == CHANGE_APPMNT) {
			if(!MainFrame.changeAppmntPane.date.getText().isEmpty() && !MainFrame.changeAppmntPane.starttime.getText().isEmpty()
					&& (!MainFrame.changeAppmntPane.endtime.getText().isEmpty() || !MainFrame.changeAppmntPane.duration.getText().isEmpty())) {
				refresh();
			}
		}
		
		setVisible(true);
	}

	
	private void init() {
		roomList = new JList<String>();
		topPane = new JPanel(new FlowLayout());
		bottomPane = new JPanel(new FlowLayout());
		okBtn = new JButton("OK");
	}
	
	private void setup() {
		roomList.setPreferredSize(new Dimension(275, 175));
		roomList.addListSelectionListener(this);
		okBtn.addActionListener(this);
		topPane.add(roomList);
		bottomPane.add(okBtn);
		add(new JLabel("Select an available room:"), BorderLayout.NORTH);
		add(topPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
		
	}
	
	private void refresh() throws SQLException {
		ResultSet bookedRooms = MainFrame.db.getBookedRooms();
		ResultSet allRooms = MainFrame.db.getRooms();
		ArrayList<String> availableRooms = new ArrayList<String>();
		
		String date = "";
		String start = "";
		String end = "";
		String dur = "";
		
		int invited = -1;
		
		if(chosenPane == NEW_APPMNT) {
			
			date = MainFrame.newAppmntPane.date.getText();
			start = MainFrame.newAppmntPane.starttime.getText();
			end = MainFrame.newAppmntPane.endtime.getText();
			dur = MainFrame.newAppmntPane.duration.getText();
			
		} else if(chosenPane == CHANGE_APPMNT) {
			
			date = MainFrame.changeAppmntPane.date.getText();
			start = MainFrame.changeAppmntPane.starttime.getText();
			end = MainFrame.changeAppmntPane.endtime.getText();
			dur = MainFrame.changeAppmntPane.duration.getText();
			
			ResultSet invitedCount = MainFrame.db.getInvitedCount(MainFrame.changeAppmntPane.getCurrenAppmntID());
			ResultSet externalInvitedCount = MainFrame.db.getExternalInvitedCount(MainFrame.changeAppmntPane.getCurrenAppmntID());
			invitedCount.next();
			externalInvitedCount.next();
			invited = invitedCount.getInt(1);
			invited += externalInvitedCount.getInt(1);
			System.out.println(invited);
		}
		
		while(allRooms.next()) {
			if(!availableRooms.contains(allRooms.getString(1)) && invited <= allRooms.getInt(2)) {
				availableRooms.add(allRooms.getString(1));
			}
		}
		
		while(bookedRooms.next()) {
			if(TimeLogic.timeOverlaps(date, start, end, dur, 
					bookedRooms.getString(4), bookedRooms.getString(5), bookedRooms.getString(6), bookedRooms.getString(7))) {
				availableRooms.remove(bookedRooms.getString(1));
			}
		}
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i < availableRooms.size(); i++) {
			ResultSet room = MainFrame.db.getRoom(availableRooms.get(i));
			room.next();
			String roomString = room.getString(1) + ", capacity: " + room.getInt(2);
			listModel.addElement(roomString);
		}
		roomList.setModel(listModel);
		
	}


	@Override
	public void valueChanged(ListSelectionEvent el) {
		MainFrame.newAppmntPane.location.setText(roomList.getSelectedValue().split(", ")[0]);
		MainFrame.changeAppmntPane.location.setText(roomList.getSelectedValue().split(", ")[0]);
	}


	@Override
	public void actionPerformed(ActionEvent ea) {
		if(ea.getActionCommand().equals("OK")) {
			dispose();
		}
	}
}
