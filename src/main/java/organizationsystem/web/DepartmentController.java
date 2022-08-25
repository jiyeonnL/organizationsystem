package organizationsystem.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import organizationsystem.biz.itf.DepartmentMgt;
import organizationsystem.dto.DepartmentDTO;
import organizationsystem.except.OrganizationSystemException;


@RestController
public class DepartmentController {
	
	@Autowired
	DepartmentMgt departmentMgt;
	
	@GetMapping(path = "/department", produces = "application/json")
    public Object findDepartments(HttpServletRequest request, HttpServletResponse response) {
		return departmentMgt.findDepartments();
	}
	
	@GetMapping(path = "/department/{id}", produces = "application/json")
	public DepartmentDTO readDepartmentById(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
		return departmentMgt.readDepartmentById(id);
	}
	
	@PostMapping(path = "/department", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> register(HttpServletRequest request, @RequestBody DepartmentDTO dto) throws Exception {
		ResponseEntity<String> entity = null;
		try {
			departmentMgt.registerDepartment(dto);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(OrganizationSystemException e){
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PutMapping(path = "/department/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> modify(HttpServletRequest request, @RequestBody DepartmentDTO dto, @PathVariable("id") Integer id) {
		ResponseEntity<String> entity = null;
		dto.setId(id);
		try {
			departmentMgt.modifyDepartment(dto);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(OrganizationSystemException e){
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@DeleteMapping(path = "/department/{id}")
	public ResponseEntity<String> remove(HttpServletRequest request, @PathVariable("id") Integer id) {
		ResponseEntity<String> entity = null;
		try {
			departmentMgt.removeDepartment(id);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(OrganizationSystemException e){
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
    

}
