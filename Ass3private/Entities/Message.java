package Entities;
import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private int type;
	private String name;
	private String dept;

	public Message() {
		this.setType(0);  
		this.setName("");
		this.setDept("");
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}