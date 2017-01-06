package Entities;

import java.io.Serializable;

public class Worker implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String dept;

	public Worker(String name, String dept) {
		this.setName(name);
		this.setDept(dept);
	}

	public String getDept() {   
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}