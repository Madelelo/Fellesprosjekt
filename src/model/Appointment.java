package model;

import java.util.List;

public class Appointment {

	private int appID;
	private int starttime;
	private int endtime;
	private double duration;
	private String location;
	private String description;
	public List<Employee> participants;
	
	

	/**
	 * Adds the starttime of the appointment.
	 * 
	 * @param time
	 */
	public void addStarttime(int time) {
		starttime = time;
		return;
	}

	/**
	 * Adds the endtime of the appointment.
	 * 
	 * @param time
	 */
	public void addEndtime(int time) {
		endtime = time;
		return;
	}

	/**
	 * Adds the duration of the appointment.
	 * 
	 * @param dur
	 */
	public void addDuration(double dur) {
		duration = dur;
		return;
	}
	
	/**
	 * Sets the location of the appointment.
	 * @param loc
	 */
	public void setLocation(String loc) {
		location = loc;
		return;
	}
	
	/**
	 * Sets the description of the appointment.
	 * @param desc
	 */
	public void setDescription(String desc) {
		description = desc;
		return;
	}

}
