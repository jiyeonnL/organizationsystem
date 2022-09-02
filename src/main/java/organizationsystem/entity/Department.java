package organizationsystem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "DEPARTMENT", schema = "TEST")
@Entity
public class Department implements Serializable {
	@Id
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	private String head;
	private String location;
	private String contact;

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

	public Department(int id, String name, String head, String location, String contact) {
		super();
		this.id = id;
		this.name = name;
		this.head = head;
		this.location = location;
		this.contact = contact;
	}

	public Department(int id, String name) {
		this(id, name, null, null, null);
	}
	
	public Department() {
		super();
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", head=" + head + ", location=" + location + ", contact="
				+ contact + "]";
	}
	
	

}
