package organizationsystem.biz;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import organizationsystem.biz.itf.EmployeeMgt;
import organizationsystem.dto.EmployeeDTO;
import organizationsystem.except.OrganizationSystemException;
import organizationsystem.rao.itf.EmployeeRAO;

public class EmployeeImpl implements EmployeeMgt {
	
	private EmployeeRAO rao;
	
	public void setRao(EmployeeRAO rao) {
		this.rao = rao;
	}
	
	public List<EmployeeDTO> findEmployees() {
		return rao.selectEmployees();
	}
	
	public List<EmployeeDTO> readEmployeesByPage(int page) {
		return rao.selectEmployeesByPage(page);
	}
	
	public EmployeeDTO readEmployeeById(int id) {

		return rao.selectEmployeeById(id);
	}
	
	public int registerEmployee(EmployeeDTO dto)throws OrganizationSystemException {
		int nextId = rao.selectMaxId() + 1;
		dto.setId(nextId);
		try {
			rao.insertEmployee(dto);
		}catch(Exception e){
			if((dto.getName()).length() > 20 || (dto.getPosition()).length() > 20 || (dto.getHiredDate()).length() > 20 || (dto.getBirth()).length() > 20) {
	    		throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
	    	}else {
	    		throw new OrganizationSystemException("직원 등록을 실패했습니다.");
	    	}
		}
		return nextId;
	}

	public int modifyEmployee(EmployeeDTO dto)throws OrganizationSystemException {

		try {
			rao.updateEmployee(dto);
		}catch(Exception e){
			if((dto.getName()).length() > 20 || (dto.getPosition()).length() > 20 || (dto.getHiredDate()).length() > 20 || (dto.getBirth()).length() > 20) {
				throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
			}else {
	    		throw new OrganizationSystemException("직원 수정을 실패했습니다.");
	    	}
		}
		return dto.getId();
	}

	public void removeEmployee(int id) {
		rao.deleteEmployee(id);
	}
	
	public List<EmployeeDTO> readEmployeeByDepartId(int dId) {
		return rao.selectEmployeeByDepartId(dId);
	}
	
	public void moveEmployeeTo(int fromDId, int toDId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("fromDId", fromDId);
		map.put("toDId", toDId);
		rao.updateEmployeeTo(map);
	}
	
	public int findEmployeesNum() {
		return rao.selectEmployeesNumber();
	}

}
