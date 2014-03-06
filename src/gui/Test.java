package gui;

public class Test {
	
	Employee marthe = createEmployee("marthepus@msn.no", "marthe", "passord123");
	
	public Employee createEmployee(String email, String username, String password) {
		Employee e = new Employee();
		e.setEmail(email);
		e.setPassword(password);
		e.setUsername(username);
		return e;
	}
	
	

}
