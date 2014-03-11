package model;

import java.util.List;

public class Employee {
	private String email;
	private String username;
	private String password;
	public List<Appointment> appointments;

	public Employee(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Employee() {
		email = "";
		username = "";
		password = "";
	}

	/**
	 * Returns an employees email.
	 * 
	 * @return String email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Changes the email.
	 * 
	 * @param mail
	 */
	public void setEmail(String mail) {
		email = mail;
		return;
	}

	/**
	 * Returns the username.
	 * 
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Changes the username.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
		return;
	}

	/**
	 * Returns the password.
	 * 
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the password.
	 * 
	 * @param pass
	 */
	public void setPassword(String pass) {
		password = pass;
		return;
	}
}
