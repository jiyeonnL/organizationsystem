package organizationsystem.rao.itf;

import java.util.HashMap;
import java.util.List;

import organizationsystem.dto.EmployeeDTO;

public interface EmployeeRAO {
	
	public List<EmployeeDTO> selectEmployees();
	
	public EmployeeDTO selectEmployeeById(int id);
	
	public int selectMaxId();
	
	public int insertEmployee(EmployeeDTO dto);
	
	public void updateEmployee(EmployeeDTO dto);
	
	public void deleteEmployee(int id);
	
	public List<EmployeeDTO> selectEmployeeByDepartId(int dId);
	
	public void updateEmployeeTo(HashMap<String, Integer> map);
	
	public int selectEmployeesNumber();
	
	public List<EmployeeDTO> selectEmployeesByPage(int page);

}
