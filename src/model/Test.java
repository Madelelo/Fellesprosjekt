package model;

public class Test {

	public static void main(String[] args) {
		System.out.println(marthe.getEmail());
	}

	private static Employee marthe = createEmployee("marthepus@msn.no", "marthe", "passord123");

	public static Employee createEmployee(String email, String username,
			String password) {
		Employee e = new Employee();
		e.setEmail(email);
		e.setPassword(password);
		e.setUsername(username);
		return e;
	}

}
