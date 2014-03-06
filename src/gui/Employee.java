package gui;

import java.util.List;

public class Employee {
	private String email;
	private String username;
	private String password;
	public List<Appointment> appointments;

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

	/**
	 * Checks if the username and password the user puts in matches the username
	 * and password of an employee. Both have to match to return true, if not,
	 * an error message is printed to the user and the method returns false.
	 * 
	 * @param username
	 * @param pass
	 * @return
	 */
	public boolean login(String username, String pass) {
		if (this.username == username && this.password == pass) {
			return true;
		} else {
			System.out.println("Wrong username and password combination");
			return false;
		}
	}

}
