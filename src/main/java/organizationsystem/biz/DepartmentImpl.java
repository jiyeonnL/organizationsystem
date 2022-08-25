package organizationsystem.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import organizationsystem.biz.itf.DepartmentMgt;
import organizationsystem.biz.itf.EmployeeMgt;
import organizationsystem.dto.DepartmentDTO;
import organizationsystem.except.OrganizationSystemException;
import organizationsystem.rao.DepartmentRAO;
import organizationsystem.rao.itf.EmployeeRAO;

public class DepartmentImpl implements DepartmentMgt {
	
	@Autowired
	private EmployeeMgt employeeMgt;
	
	private DepartmentRAO rao;
	
	private EmployeeRAO emplRao;
	
	private static final int UNASSIGNED = 1; 
	
	public void setRao(DepartmentRAO rao) {
		this.rao = rao;
	}
	
    public List<DepartmentDTO> findDepartments() {
		return rao.selectDepartments();
	}
    
    public DepartmentDTO readDepartmentById(int id) {
    	return rao.selectDepartmentById(id);
    }

    public int registerDepartment(DepartmentDTO dto) throws OrganizationSystemException{ 
    	//현재 가진 가장 큰 키 값을 조회해서 +1 값으로 새로운 id 생성
    	int nextId = rao.selectMaxId() + 1;
    	dto.setId(nextId);
    	
    	int result = rao.insertDepartment(dto);
    	if(result == 0) {
    		if((dto.getHead()).length() > 10 || (dto.getName()).length() > 30 || (dto.getLocation()).length() > 100 || (dto.getContact()).length() > 50 || (dto.getHead()).length() > 50) {
    			throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
    		}else {
    			throw new OrganizationSystemException("부서 등록을 실패했습니다.");
    		}
    	}
    	
    	return nextId;
    }
    
    public int modifyDepartment(DepartmentDTO dto) throws OrganizationSystemException {
    	int result = rao.updateDepartment(dto);
    	if(result == 0) {
    		if((dto.getHead()).length() > 10 || (dto.getName()).length() > 30 || (dto.getLocation()).length() > 100 || (dto.getContact()).length() > 50 || (dto.getHead()).length() > 50) {
    			throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
    		}else {
    			throw new OrganizationSystemException("부서 수정을 실패했습니다.");
    		}
    	}
    	
    	return dto.getId();
    }
    
    public void removeDepartment(int id) throws OrganizationSystemException {
    	employeeMgt.moveEmployeeTo(id, UNASSIGNED);
    	int result = rao.deleteDepartment(id);
    	if(result == 0) throw new OrganizationSystemException("부서 삭제를 실패했습니다.");
    }
    
}
