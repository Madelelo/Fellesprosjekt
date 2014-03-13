package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * JPanel with GUI for showing notifications and alarms.
 *
 */
public class NotificationPane extends JPanel {

	protected static JLabel notLabel;
	protected static JLabel alarmLabel;
	protected static JTextArea notArea;
	protected static JTextArea alarmArea;
	
	public NotificationPane() {
		
		notLabel = new JLabel("Varsler");
		alarmLabel = new JLabel("Alarmer");
		
		
	}
}
