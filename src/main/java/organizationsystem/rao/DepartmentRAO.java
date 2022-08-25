package organizationsystem.rao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import organizationsystem.dto.DepartmentDTO;

public class DepartmentRAO {
	private DataSource dataSource = null;
	public void init() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/RadiusXADS");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<DepartmentDTO> selectDepartments() {
		List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "SELECT * FROM TEST.Department ORDER BY id";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				connection = dataSource.getConnection();
				stmt = connection.createStatement();
				rs = stmt.executeQuery(ql);
				while(rs.next()) {
					
					departments.add(
					    new DepartmentDTO(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("head"),
							rs.getString("location"),
							rs.getString("contact"))
					);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
				if(rs != null) {
					try{rs.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(stmt != null) {
					try{stmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		System.out.println("결과:" + departments);
		
		return departments;
		
	}
	
	public DepartmentDTO selectDepartmentById(int id) {
		DepartmentDTO dto = new DepartmentDTO();
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "SELECT name, head, location, contact FROM TEST.Department WHERE id=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.prepareStatement(ql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto.setId(id);
					dto.setName(rs.getString("name"));
					dto.setHead(rs.getString("head"));
					dto.setLocation(rs.getString("location"));
					dto.setContact(rs.getString("contact"));	
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
				if(rs != null) {
					try{rs.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(pstmt != null) {
					try{pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		return dto;
	}
	
	
	public int selectMaxId() {
		int max = 0;
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "SELECT NVL(MAX(id), 0) FROM TEST.DEPARTMENT";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				connection = dataSource.getConnection();
				stmt = connection.createStatement();
				rs = stmt.executeQuery(ql);
				if(rs.next()) {
					max = rs.getInt(1);	
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
				if(rs != null) {
					try{rs.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(stmt != null) {
					try{stmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		return max;
		
	}
	
	public int insertDepartment(DepartmentDTO dto) {

		int rowCount = 0;
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "INSERT INTO TEST.Department (id, name, head, location, contact)" + "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.prepareStatement(ql);
				
				pstmt.setInt(1, dto.getId());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getHead());
				pstmt.setString(4, dto.getLocation());
				pstmt.setString(5, dto.getContact());
				
				rowCount = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try{pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		System.out.println("결과:" + rowCount);
		
		return rowCount;
		
	}
	
	public int updateDepartment(DepartmentDTO dto) {

		int rowCount = 0;
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "UPDATE TEST.Department SET name=?, head=?, location=?, contact=? WHERE id=?";
			PreparedStatement pstmt = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.prepareStatement(ql);
				
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getHead());
				pstmt.setString(3, dto.getLocation());
				pstmt.setString(4, dto.getContact());
				pstmt.setInt(5,  dto.getId());
				
				rowCount = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try{pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		System.out.println("결과:" + rowCount);
		
		return rowCount;
		
	}
	
	
	public int deleteDepartment(int id) {

		int rowCount = 0;
		
		if(dataSource != null) {
			Connection connection = null; 
			String ql = "DELETE FROM TEST.Department WHERE id=?";
			PreparedStatement pstmt = null;
			try {
				connection = dataSource.getConnection();
				pstmt = connection.prepareStatement(ql);
				
				pstmt.setInt(1, id);
				
				rowCount = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try{pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				if(connection != null) {
					try {connection.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
		}
		
		System.out.println("결과:" + rowCount);
		
		return rowCount;
		
	}
	

	
//	public List<DepartmentDTO> selectDepartments_back() {
//		List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
//		
//		//DepartmentDTO department = new DepartmentDTO(1, "인사팀");
//		
//		departments.add(new DepartmentDTO(1, "인사팀"));
//		
//		departments.add(new DepartmentDTO(2, "개발팀"));
//		
//		System.out.println("결과:" + departments);
//		
//		return departments;
//		
//	}
}
