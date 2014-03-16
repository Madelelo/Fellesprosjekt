package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	
	public FindRoomFrame() {
		super("Available rooms");
		setSize(300, 280);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(15, 15));
		
		init();
		setup();
		refresh();
		
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
	
	private void refresh() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listModel.addElement("Hei");
		roomList.setModel(listModel);
		
	}


	@Override
	public void valueChanged(ListSelectionEvent el) {
		MainFrame.newAppmntPane.location.setText(roomList.getSelectedValue());
		MainFrame.changeAppmntPane.location.setText(roomList.getSelectedValue());
	}


	@Override
	public void actionPerformed(ActionEvent ea) {
		if(ea.getActionCommand().equals("OK")) {
			dispose();
		}
	}
}
