package service;

import java.sql.Connection;	
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.EmpDao;
import util.DBUtil;
import vo.Emp;

public class EmpService {
	
	private EmpDao empDao;

	// EmpList 출력
	// 사용하는 곳 : EmpListController
	public ArrayList<Emp> getEmpList(String searchCategory, String searchText, int currentPage, int rowPerPage) {
		
		ArrayList<Emp> list = null;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			list = new ArrayList<Emp>();
			
			int beginRow = Page.getBeginRow(currentPage, rowPerPage);
			
			this.empDao = new EmpDao();
			list = this.empDao.selectEmpList(conn, searchCategory, searchText, beginRow, rowPerPage);
			conn.commit();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
	// EmpList 페이징
	public ArrayList<HashMap<String, Object>> getPageBoard(String searchCategory, String searchText, int currentPage, int rowPerPage) {
		
		ArrayList<HashMap<String, Object>> list = null;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			this.empDao = new EmpDao();
			
			// 페이징 처리
			int pageLength = 10;
			int count = this.empDao.countEmp(conn, searchCategory, searchText);
			
			int previousPage = Page.getPreviousPage(currentPage, pageLength);
			int nextPage = Page.getNextPage(currentPage, pageLength);
			int lastPage = Page.getLastPage(count, rowPerPage);
			ArrayList<Integer> pageList = Page.getPageList(currentPage, pageLength);
			
			list = new ArrayList<HashMap<String, Object>>();
			
			HashMap<String, Object> m1 = new HashMap<String, Object>();
			m1.put("previousPage", previousPage);
			m1.put("nextPage", nextPage);
			m1.put("lastPage", lastPage);
			m1.put("pageList", pageList);
			
			list.add(m1);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
	
	
}
