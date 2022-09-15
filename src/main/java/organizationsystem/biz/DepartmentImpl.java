package organizationsystem.biz;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import organizationsystem.biz.itf.DepartmentMgt;
import organizationsystem.biz.itf.EmployeeMgt;
import organizationsystem.dto.DepartmentDTO;
import organizationsystem.entity.Department;
import organizationsystem.entity.Employee;
import organizationsystem.except.OrganizationSystemException;
import organizationsystem.rao.DepartmentRAO;
import organizationsystem.rao.itf.EmployeeRAO;
import organizationsystem.util.CopyUtil;

public class DepartmentImpl implements DepartmentMgt {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private EmployeeMgt employeeMgt;

	private DepartmentRAO rao;
	private EmployeeRAO emplRao;
	private static final int UNASSIGNED = 1; 
	
	public void setRao(DepartmentRAO rao) {
		this.rao = rao;
	}
	
    public List<Department> findDepartments() {
		return em.createQuery("SELECT d FROM Department d ORDER BY d.id", Department.class)
				.getResultList();
	}
    
    public Department readDepartmentById(int id) {
    	return em.find(Department.class, id);
    }

    public int registerDepartment(DepartmentDTO dto) throws OrganizationSystemException{   
    	try {
    		//현재 가진 가장 큰 키 값을 조회해서 +1 값으로 새로운 id 생성
    		List<Integer> a = em.createNativeQuery("SELECT NVL(MAX(id), 0) FROM TEST.DEPARTMENT").getResultList();
    		System.out.println(a.toString());
    		int nextId = a.get(0) + 1;
    		//int nextId = rao.selectMaxId() + 1;
    		dto.setId(nextId);
    		Department department = new Department();
    		CopyUtil.copyProperties(dto, department);                                                            
    		em.persist(department);
    		em.flush();
    	}catch(Exception e) {
    		if((dto.getHead()).length() > 10 || (dto.getName()).length() > 30 || (dto.getLocation()).length() > 100 || (dto.getContact()).length() > 50 || (dto.getHead()).length() > 50) {
    			throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
    		}else {
    			throw new OrganizationSystemException("부서 등록을 실패했습니다.");
    		}
    	}
    	return 0;
    }
    
    public int modifyDepartment(DepartmentDTO dto) throws OrganizationSystemException {
    	
    	try{
    		Department department = new Department();
    		CopyUtil.copyProperties(dto, department);  
    		em.merge(department);
    	}catch(Exception e) {
    		if((dto.getHead()).length() > 10 || (dto.getName()).length() > 30 || (dto.getLocation()).length() > 100 || (dto.getContact()).length() > 50 || (dto.getHead()).length() > 50) {
    			throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
    		}else {
    			throw new OrganizationSystemException("부서 수정을 실패했습니다.");
    		}
    	}
    	return dto.getId();
    }
    
    public void removeDepartment(int id) throws OrganizationSystemException {
    	
    	try {
    		//int result = rao.deleteDepartment(id);
    		employeeMgt.moveEmployeeTo(id, UNASSIGNED);
    		Department department = new Department();
    		department = em.find(Department.class, id);
    		em.remove(department);
    	}catch(Exception e) {
    		throw new OrganizationSystemException("부서 삭제를 실패했습니다.");
    	}

    }
    
}
