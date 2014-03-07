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
	
	public Appointment(int starttime, int endtime, String location, String description) {
		setStarttime(starttime);
		setEndtime(endtime);
		setLocation(location);
		setDescription(description);
	}

	/**
	 * Sets the starttime of the appointment.
	 * 
	 * @param time
	 */
	public void setStarttime(int time) {
		starttime = time;
		return;
	}

	/**
	 * Sets the endtime of the appointment.
	 * 
	 * @param time
	 */
	public void setEndtime(int time) {
		endtime = time;
		return;
	}

	/**
	 * Sets the duration of the appointment.
	 * 
	 * @param dur
	 */
	public void setDuration(double dur) {
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
