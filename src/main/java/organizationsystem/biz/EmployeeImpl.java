package organizationsystem.biz;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import organizationsystem.biz.itf.EmployeeMgt;
import organizationsystem.dto.EmployeeDTO;
import organizationsystem.entity.Department;
import organizationsystem.entity.Employee;
import organizationsystem.except.OrganizationSystemException;
import organizationsystem.rao.itf.EmployeeRAO;
import organizationsystem.util.CopyUtil;

public class EmployeeImpl implements EmployeeMgt {
	
	private EmployeeRAO rao;
	
	@PersistenceContext
	private EntityManager em;
	
	public void setRao(EmployeeRAO rao) {
		this.rao = rao;
	}
	
	//직원목록 전체조회
	//public List<Employee> findEmployees() {
	//	return em.createQuery("SELECT d.Name dName, e.* FROM Department d JOIN Employee e ON d.id = e.depart_id ORDER BY e.id", Employee.class)
	//			.getResultList();
	//}
	
	//총 직원 수 
	public int findEmployeesNum() {
		List<Integer> a = em.createNativeQuery("SELECT COUNT(*) FROM TEST.EMPLOYEE").getResultList();
		return a.get(0);
	}

	//페이지네이션
	public List<Employee> readEmployeesByPage(int page) {
		return em.createQuery("SELECT e FROM Department d JOIN Employee e ON d.id = e.department.id ORDER BY e.id", Employee.class)
				.setFirstResult(page).setMaxResults(10).getResultList();
	}
	
	//직원 단건 조회
	public Employee readEmployeeById(int id) {
		return em.find(Employee.class, id);
	}
	
	//부서별 직원목록 조회
	public List<Employee> readEmployeeByDepartId(int dId) {
		Query ql = em.createNativeQuery("SELECT * FROM TEST.Employee WHERE depart_id = ? ORDER BY id").setParameter(1, dId);
		return ql.getResultList();
	}
	
	//부서 삭제 시 직원 이동하기
	public void moveEmployeeTo(int fromDId, int toDId) {
        em.createNativeQuery("UPDATE TEST.EMPLOYEE SET DEPART_ID = :toDId WHERE DEPART_ID = :fromDId")
        .setParameter("toDId", toDId).setParameter("fromDId", fromDId)
        .executeUpdate();
		
		/*
		 * HashMap<String, Integer> map = new HashMap<String, Integer>();
		 * map.put("fromDId", fromDId); map.put("toDId", toDId);
		 * rao.updateEmployeeTo(map);
		 */
	}
	
	//직원 등록
	public int registerEmployee(EmployeeDTO dto)throws OrganizationSystemException {
		try {
			List<Integer> a = em.createNativeQuery("SELECT NVL(MAX(id), 0) FROM TEST.EMPLOYEE").getResultList();
			System.out.println(a.toString());
			int nextId = a.get(0) + 1;
			//int nextId = rao.selectMaxId() + 1;
			dto.setId(nextId);
			
			Employee employee = new Employee();
			CopyUtil.copyProperties(dto, employee);
			//System.out.println("등록할 객체1 : "+employee);
			Department department = new Department();
			department.setId(dto.getDepart_id());
			employee.setDepartment(department);
			//System.out.println("등록할 객체2 : "+employee);
			em.persist(employee);
			em.flush();
		}catch(Exception e){
			if((dto.getName()).length() > 20 || (dto.getPosition()).length() > 20 || (dto.getHiredDate()).length() > 20 || (dto.getBirth()).length() > 20) {
	    		throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
	    	}else {
	    		throw new OrganizationSystemException("직원 등록을 실패했습니다.");
	    	}
		}
		return 0;
	}

	//직원 수정
	public int modifyEmployee(EmployeeDTO dto)throws OrganizationSystemException {

		try {
			Employee employee = new Employee();
			CopyUtil.copyProperties(dto, employee);
			
			System.out.println("이 객체로 수정 1 : "+employee);
			Department department = new Department();
			department.setId(dto.getDepart_id());
			employee.setDepartment(department);
			System.out.println("이 객체로 수정 2 : "+employee);
			
			
			em.merge(employee);
		}catch(Exception e){
			if((dto.getName()).length() > 20 || (dto.getPosition()).length() > 20 || (dto.getHiredDate()).length() > 20 || (dto.getBirth()).length() > 20) {
				throw new OrganizationSystemException("입력한 내용이 등록 가능한 길이를 초과했습니다.");
			}else {
	    		throw new OrganizationSystemException("직원 수정을 실패했습니다.");
	    	}
		}
		return dto.getId();
	}

	//직원 삭제
	public void removeEmployee(int id) {
		Employee employee = em.find(Employee.class, id);
		em.remove(employee);
	}
	
	

}
