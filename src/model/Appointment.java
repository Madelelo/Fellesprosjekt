package model;

import java.util.List;

public class Appointment {

	private int appID;
	private int date;
	private int starttime;
	private int endtime;
	private double duration;
	private String location;
	private String description;
	public List<Employee> participants;
	
	/**
	 * Creates the appointment.
	 * 
	 * @param starttime
	 * @param endtime
	 * @param location
	 * @param description
	 */
	public Appointment(int date, int starttime, int endtime, String location, String description) {
		setDate(date);
		setStarttime(starttime);
		setEndtime(endtime);
		setLocation(location);
		setDescription(description);
	}
	
	public Appointment(int id, int date, int starttime, int endtime, String location, String description) {
		appID = id;
		setDate(date);
		setStarttime(starttime);
		setEndtime(endtime);
		setLocation(location);
		setDescription(description);
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public int getDate() {
		return date;
	}

	/**
	 * Sets the starttime of the appointment.
	 * 
	 * @param time
	 */
	public void setStarttime(int time) {
		starttime = time;
	}
	
	public int getStarttime() {
		return starttime;
	}

	/**
	 * Sets the endtime of the appointment.
	 * 
	 * @param time
	 */
	public void setEndtime(int time) {
		endtime = time;
	}
	
	public int getEndtime() {
		return endtime;
	}

	/**
	 * Sets the duration of the appointment.
	 * 
	 * @param dur
	 */
	public void setDuration(double dur) {
		duration = dur;
	}
	
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Sets the location of the appointment.
	 * @param loc
	 */
	public void setLocation(String loc) {
		location = loc;
	}
	
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the description of the appointment.
	 * @param desc
	 */
	public void setDescription(String desc) {
		description = desc;
	}
	
	public String getDescription() {
		return description;
	}

}
