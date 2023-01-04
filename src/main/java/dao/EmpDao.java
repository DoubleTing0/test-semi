package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Emp;

public class EmpDao {

	// empList
	// 사용하는 곳 : EmpService
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
	// 사용하는 곳 : EmpService
	public int countEmp(Connection conn, String searchCategory, String searchText) throws Exception {
		
		int resultCount = 0;
		
		String sql = "SELECT COUNT(emp_code) cnt"
				+ "	 FROM board"
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
	
	
}
