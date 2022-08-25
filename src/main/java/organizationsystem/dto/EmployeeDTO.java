package organizationsystem.dto;

import java.io.Serializable;
import java.util.Date;

public class EmployeeDTO implements Serializable {
	
	private int id;
	private String name;
	private String position;
	private String hiredDate;
	private String birth;
	private int depart_id;
	private String dName;
	
	public EmployeeDTO() {
		super();
	}
	
	public EmployeeDTO(String dName, int id, String name, String position, String hiredDate, String birth, int depart_id) {
		super();
		this.dName = dName;
		this.id = id;
		this.name = name;
		this.position = position;
		this.hiredDate = hiredDate;
		this.birth = birth;
		this.depart_id = depart_id;
	}
	
	public String getDName() {
		return dName;
	}
	public void setDName(String dName) {
		this.dName = dName;
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
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	
	
	@Override
	public String toString() {
		return "EmployeeDTO [dName=" + dName + ", id=" + id + ", name=" + name + ", position=" + position + ", hiredDate=" + hiredDate + ", birth="
				+ birth + ", depart_id=" + depart_id + "]";
	}

}
