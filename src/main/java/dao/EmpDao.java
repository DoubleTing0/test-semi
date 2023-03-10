package dao;

import java.sql.Connection;	
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Emp;

public class EmpDao {

	// empList
	// 사용하는 곳 : EmpListController
	// 검색 페이징 포함
	public ArrayList<Emp> selectEmpList(Connection conn, String searchCategory, String searchText, int beginRow, int rowPerPage) throws Exception {
		
		ArrayList<Emp> list = null;
		
		String sql = "SELECT emp_code empCode"
				+ "			, emp_id empId"
				+ "			, emp_name empName"
				+ "			, active"
				+ "			, auth_code authCode"
				+ "			, createdate"
				+ "	 FROM emp"
				+ "	 WHERE " + searchCategory + " LIKE ?"
				+ "	 ORDER BY emp_code DESC"
				+ "	 LIMIT ?, ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1,  "%" + searchText + "%");
		stmt.setInt(2,  beginRow);
		stmt.setInt(3, rowPerPage);
		
		ResultSet rs = stmt.executeQuery();
		
		list = new ArrayList<Emp>();
		
		while(rs.next()) {
			
			Emp emp = new Emp();
			emp.setEmpCode(rs.getInt("empCode"));
			emp.setEmpId(rs.getString("empId"));
			emp.setEmpName(rs.getString("empName"));
			emp.setActive(rs.getString("active"));
			emp.setAuthCode(rs.getInt("authCode"));
			emp.setCreatedate(rs.getString("createdate"));
			
			list.add(emp);
			
		}
		
		return list;
		
	}
	
	
	// 검색 후 emp 총 카운트
	// 사용하는 곳 : EmpListController
	public int countEmp(Connection conn, String searchCategory, String searchText) throws Exception {
		
		int resultCount = 0;
		
		String sql = "SELECT COUNT(emp_code) cnt"
				+ "	 FROM emp"
				+ "	 WHERE " + searchCategory + " LIKE ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + searchText + "%");
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			resultCount = rs.getInt("cnt");
		}
		
		// 디버깅
		System.out.println(resultCount + " <-- resultCount");
		
		return resultCount;
		
	}
	
	// empOne 한명의 정보 출력
	// 사용하는 곳 : UpdateEmpController
	public Emp selectEmpOne(Connection conn, int empCode) throws Exception {
		
		Emp resultEmp = null;
		
		String sql = "SELECT emp_id empId"
				+ "			, emp_name empName"
				+ "			, active"
				+ "			, auth_code authCode"
				+ "			, createdate"
				+ "	 FROM emp"
				+ "	 WHERE emp_Code = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setInt(1, empCode);
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			
			resultEmp = new Emp();
			
			resultEmp.setEmpId(rs.getString("empId"));
			resultEmp.setEmpName(rs.getString("empName"));
			resultEmp.setActive(rs.getString("active"));
			resultEmp.setAuthCode(rs.getInt("authCode"));
			resultEmp.setCreatedate(rs.getString("createdate"));
			
		}
		
		return resultEmp;
		
	}
	
	// emp 수정
	// 사용하는 곳 : UpdateEmpController
	
	public int updateEmp(Connection conn, Emp emp) throws Exception {
		int resultRow = 0;
		
		String sql = "UPDATE emp"
				+ "	 SET emp_name = ?"
				+ "		, active = ?"
				+ "		, auth_code = ?"
				+ "	 WHERE emp_code = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, emp.getEmpName());
		stmt.setString(2, emp.getActive());
		stmt.setInt(3, emp.getAuthCode());
		stmt.setInt(4, emp.getEmpCode());
		
		resultRow = stmt.executeUpdate();
		
		if(resultRow == 1) {
			System.out.println("emp 수정 성공!");
		} else {
			System.out.println("emp 수정 실패!");
		}
		
		return resultRow;
		
	}
	
	// emp 삭제
	// 사용하는 곳 : DeleteEmpController
	public int deleteEmp(Connection conn, int empCode) throws Exception {
		
		int resultRow = 0;
		
		String sql = "DELETE"
				+ "	 FROM emp"
				+ "	 WHERE emp_code = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setInt(1, empCode);
		
		resultRow = stmt.executeUpdate();
		
		if(resultRow == 1) {
			System.out.println("emp 삭제 성공!");
		} else {
			System.out.println("emp 삭제 실패!");
		}
		
		return resultRow;
		
	}
	
	
	// 1) emp ID 중복확인
	// true : ID가 이미 존재(가입불가) false : ID 사용 가능(가입가능)
	// 사용하는 곳 : AddCustomerController, AddEmpController
	public boolean checkEmpId(Connection conn, Emp emp) throws Exception {
		
		boolean result = false;
		
		String sql = "SELECT emp_id empId"
				+ "	 FROM emp"
				+ "	 WHERE emp_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, emp.getEmpId());
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			result = true;
		}
		
		return result;
		
	}
	
	// emp 회원가입
	// 2) 회원가입
	// 사용하는 곳 : AddEmpController
	public int addEmp(Connection conn, Emp emp) throws Exception {
		
		int resultRow = 0;
		
		String sql = "INSERT INTO emp ("
				+ "			emp_id"
				+ "			, emp_pw"
				+ "			, emp_name"
				+ "			, active"
				+ "			, auth_code"
				+ "			, createdate"
				+ "	 ) VALUES ("
				+ "			?"
				+ "			, PASSWORD(?)"
				+ "			, ?"
				+ "			, ?"
				+ "			, ?"
				+ "			, NOW()"
				+ ")";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, emp.getEmpId());
		stmt.setString(2, emp.getEmpPw());
		stmt.setString(3, emp.getEmpName());
		stmt.setString(4, "N");		// 초기값 설정해야함
		stmt.setInt(5, 0);
		
		resultRow = stmt.executeUpdate();
		
		if(resultRow == 1) {
			System.out.println("emp 회원 가입 성공");
		} else {
			System.out.println("emp 회원 가입 실패");
		}
		
		
		return resultRow;
		
	}
	
	

	
	
	
}
