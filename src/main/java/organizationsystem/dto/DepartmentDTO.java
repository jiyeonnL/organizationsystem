package organizationsystem.dto;

import java.io.Serializable;

public class DepartmentDTO implements Serializable {
	
	private int id;
	private String name;
	private String head;
	private String location;
	private String contact;
	
	public DepartmentDTO() {
		super();
	}
	
	public DepartmentDTO(int id, String name) {
		this(id, name, null, null, null);
	}
	
	public DepartmentDTO(int id, String name, String head, String location, String contact) {
		super();
		this.id = id;
		this.name = name;
		this.head = head;
		this.location = location;
		this.contact = contact;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "DepartmentDTO [id=" + id + ", name=" + name + ", head=" + head + ", location=" + location + ", contact="
				+ contact + "]";
	}
	
	
	

}
