package model;

import java.util.List;

public class Appointment {

	private int appID;
	private String date;
	private String starttime;
	private String endtime;
	private String duration;
	private String location;
	private String description;
	private String owner;
	public List<Employee> participants;
	
	/**
	 * Creates the appointment.
	 * 
	 * @param starttime
	 * @param endtime
	 * @param location
	 * @param description
	 */
	public Appointment(String date, String starttime, String endtime, String duration, String location, String description) {
		setDate(date);
		setStarttime(starttime);
		setEndtime(endtime);
		setDuration(duration);
		setLocation(location);
		setDescription(description);
	}
	
	public Appointment(int id, String date, String starttime, String endtime, String duration, String location, String description) {
		appID = id;
		setDate(date);
		setStarttime(starttime);
		setEndtime(endtime);
		setDuration(duration);
		setLocation(location);
		setDescription(description);
	}
	
	public Appointment(String date, String starttime, String owner) {
		setDate(date);
		setStarttime(starttime);
		setOwner(owner);
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}

	/**
	 * Sets the starttime of the appointment.
	 * 
	 * @param time
	 */
	public void setStarttime(String time) {
		starttime = time;
	}
	
	public String getStarttime() {
		return starttime;
	}

	/**
	 * Sets the endtime of the appointment.
	 * 
	 * @param time
	 */
	public void setEndtime(String time) {
		endtime = time;
	}
	
	public String getEndtime() {
		return endtime;
	}

	/**
	 * Sets the duration of the appointment.
	 * 
	 * @param dur
	 */
	public void setDuration(String dur) {
		duration = dur;
	}
	
	public String getDuration() {
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
	
	public int getAppointmentID() {
		return appID;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return owner;
	}

}
