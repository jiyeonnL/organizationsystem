package organizationsystem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "EMPLOYEE", schema = "TEST")
@Entity
public class Employee implements Serializable {
	@Id
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String name;
	private String position;
	private String hiredDate;
	private String birth;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depart_id")
	private Department department;
	
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", position=" + position + ", hiredDate=" + hiredDate
				+ ", birth=" + birth + ", department=" + department + "]";
	}
	public Employee(int id, String name, String position, String hiredDate, String birth, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.hiredDate = hiredDate;
		this.birth = birth;
		this.department = department;
		
	}
	
	public Employee() {
		super();
	}

}
