package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import vo.PwHistory;

public class PwHistoryDao {

	// 비밀번호 이력 추가
	// 사용하는 곳 : AddCustomerController, 추후에 회원이 비밀번호 변경할 때
	public int addPwHistory(Connection conn, PwHistory pwHistory) throws Exception {
		
		int resultRow = 0;
		
		String sql = "INSERT INTO pw_history ("
				+ "			customer_id"
				+ "			, pw"
				+ "			, createdate"
				+ ") VALUES ("
				+ "			?"
				+ "			, PASSWORD(?)"
				+ "			, NOW()"
				+ ")";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, pwHistory.getCustomerId());
		stmt.setString(2, pwHistory.getPw());
		
		resultRow = stmt.executeUpdate();
		
		if(resultRow == 1) {
			System.out.println("PW 이력 추가 성공");
		} else {
			System.out.println("PW 이력 추가 실패");
		}
		
		return resultRow;
		
	}
	
	// 비밀번호 이력 전체 삭제(관리자 삭제, 탈퇴)
	// 사용하는곳 : DeleteCustomerController, 
	public int deletePwHistory(Connection conn, PwHistory pwHistory) throws Exception {
		
		int resultRow = 0;
		
		String sql = "DELETE"
				+ "	 FROM pw_history"
				+ "	 WHERE customer_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, pwHistory.getCustomerId());
		
		resultRow = stmt.executeUpdate();
		
		if(resultRow == 1) {
			System.out.println("pw 이력 전체 삭제 성공!");
		} else {
			System.out.println("pw 이력 전체 삭제 실패!");
		}
		
		return resultRow;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
