package organizationsystem.biz.itf;

import java.util.List;

import organizationsystem.dto.DepartmentDTO;
import organizationsystem.entity.Department;
import organizationsystem.except.OrganizationSystemException;

public interface DepartmentMgt {
	
	public List<Department> findDepartments();
	
	public Department readDepartmentById(int id);
	
	public int registerDepartment(DepartmentDTO dto)throws OrganizationSystemException;
	
	public int modifyDepartment(DepartmentDTO dto)throws OrganizationSystemException;
	
	public void removeDepartment(int id)throws OrganizationSystemException;

	
}
