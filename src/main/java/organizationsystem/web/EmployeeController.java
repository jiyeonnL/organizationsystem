package organizationsystem.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import organizationsystem.biz.EmployeeImpl;
import organizationsystem.biz.itf.EmployeeMgt;
import organizationsystem.dto.DepartmentDTO;
import organizationsystem.dto.EmployeeDTO;
import organizationsystem.except.OrganizationSystemException;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeMgt employeeMgt;
	
	@GetMapping(path = "/employee",  produces = "application/json")
	public Object readEmployees(HttpServletRequest request, HttpServletResponse response){
		return employeeMgt.findEmployees();
	}
	
	@GetMapping(path = "/employee/num",  produces = "application/json")
	public int findEmployeesNum(HttpServletRequest request, HttpServletResponse response){
		return employeeMgt.findEmployeesNum();
	}
	
	@GetMapping(path = "/employee/page/{page}",  produces = "application/json")
	public Object readEmployeesByPage(HttpServletRequest request, HttpServletResponse response, @PathVariable("page") Integer page){
		return employeeMgt.readEmployeesByPage(page);
	}
	
	@GetMapping(path = "/employee/{id}", produces = "application/json")
	public EmployeeDTO readEmployeeById(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
		return employeeMgt.readEmployeeById(id);
	}
	
	@GetMapping(path = "/employee/depart/{dId}", produces = "application/json")
	public Object readEmployeeByDepartId(HttpServletRequest request, HttpServletResponse response, @PathVariable("dId") Integer dId) {
		return employeeMgt.readEmployeeByDepartId(dId);
	}
	
	@PostMapping(path = "/employee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> register(HttpServletRequest request, @RequestBody EmployeeDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		try {
			employeeMgt.registerEmployee(dto);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(OrganizationSystemException e){
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PutMapping(path = "/employee/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> modify(HttpServletRequest request, @RequestBody EmployeeDTO dto, @PathVariable("id") Integer id) {
		ResponseEntity<String> entity = null;
		dto.setId(id);
		try {
			employeeMgt.modifyEmployee(dto);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(OrganizationSystemException e){
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@DeleteMapping(path = "/employee/{id}")
	public void remove(HttpServletRequest request, @PathVariable("id") Integer id) {
		employeeMgt.removeEmployee(id);
	}
	
	@GetMapping("/employee/getback")
    public Integer practiceApi(HttpServletResponse response) {
		int i = 1;
        System.out.println("api 요청: "+i);
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        return i;
    }
	
}
