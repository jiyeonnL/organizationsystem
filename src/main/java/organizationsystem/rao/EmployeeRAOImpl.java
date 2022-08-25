package organizationsystem.rao;

import java.util.HashMap;
import java.util.List;

import organizationsystem.dto.EmployeeDTO;
import organizationsystem.rao.itf.EmployeeRAO;

public class EmployeeRAOImpl implements EmployeeRAO {
	
	private EmployeeRAO mapper = null;
	
	public void setMapper(EmployeeRAO mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<EmployeeDTO> selectEmployees() {
		
		return mapper.selectEmployees();
	}
	
	@Override
	public EmployeeDTO selectEmployeeById(int id) {
		
		return mapper.selectEmployeeById(id);
	}
	
	@Override
	public int selectMaxId() {
		return mapper.selectMaxId();
	}

	@Override
	public int insertEmployee(EmployeeDTO dto) {
		
		return mapper.insertEmployee(dto);
	}

	@Override
	public void updateEmployee(EmployeeDTO dto) {
		mapper.updateEmployee(dto);
	}

	@Override
	public void deleteEmployee(int id) {
		mapper.deleteEmployee(id);
	}
	
	@Override
	public List<EmployeeDTO> selectEmployeeByDepartId(int dId) {
		return mapper.selectEmployeeByDepartId(dId);
	}
	
	@Override
	public void updateEmployeeTo(HashMap<String, Integer> map) {
		mapper.updateEmployeeTo(map);
	}

	@Override
	public int selectEmployeesNumber() {
		return mapper.selectEmployeesNumber();
	}

	@Override
	public List<EmployeeDTO> selectEmployeesByPage(int page) {
		return mapper.selectEmployeesByPage(page);
	}

}
