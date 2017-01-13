package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String  department, workerID, firstName, lastName, id, email,
	role;
	private int isManager, isLoggedIn;
	public static ArrayList<Worker> workerList;
	public String query;
	
	public String toString(){
		String str;
		return null;
	}
	
	public Worker(String workerID, String firstName, String lastName, String id, String email, String role,
			String department, int isManager, int isLoggedIn) {
		super();
		this.department = department;
		this.workerID = workerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.role = role;
		this.isManager = isManager;
		this.isLoggedIn = isLoggedIn;
	}

	public Worker(){}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkerID() {
		return workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getIsManager() {
		return isManager;
	}

	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}

	public int getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(int isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getDept() {   
		return this.department;
	}

	public void setDept(String department) {
		this.department = department;
	}


}