package organizationsystem.biz.itf;

import java.util.List;

import organizationsystem.dto.EmployeeDTO;
import organizationsystem.except.OrganizationSystemException;

public interface EmployeeMgt {
	
    public List<EmployeeDTO> findEmployees();
    
    public List<EmployeeDTO> readEmployeesByPage(int page);
    
    public EmployeeDTO readEmployeeById(int id);
	
	public int registerEmployee(EmployeeDTO dto)throws OrganizationSystemException;
	
	public int modifyEmployee(EmployeeDTO dto)throws OrganizationSystemException;
	
	public void removeEmployee(int id);
	
	public List<EmployeeDTO> readEmployeeByDepartId(int dId);
	
	/**
	 * 직원들 부서이동
	 * @param fromDId 현재 부서id
	 * @param toDId 이동할 부서id
	 */
    public void moveEmployeeTo(int fromDId, int toDId);
    
    public int findEmployeesNum();
}
