package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * JFrame for showing available rooms for an appointment.
 *
 */
public class FindRoomFrame extends JFrame implements ListSelectionListener {
	
	private JList<String> roomList;
	
	public FindRoomFrame() {
		super("Available rooms");
		setSize(300, 200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		
		init();
		setup();
		refresh();
		
		setVisible(true);
	}

	
	private void init() {
		roomList = new JList<String>();
		
	}
	
	private void setup() {
		roomList.setPreferredSize(new Dimension(300, 200));
		roomList.addListSelectionListener(this);
		add(roomList);
		
	}
	
	private void refresh() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		MainFrame.newAppmntPane.location.setText(roomList.getSelectedValue());
		MainFrame.changeAppmntPane.location.setText(roomList.getSelectedValue());
	}
}
