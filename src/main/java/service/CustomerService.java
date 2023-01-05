package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.CustomerAddressDao;
import dao.CustomerDao;
import dao.PwHistoryDao;
import util.DBUtil;
import vo.Customer;
import vo.CustomerAddress;
import vo.PwHistory;

public class CustomerService {

	private CustomerDao customerDao;
	private CustomerAddressDao customerAddressDao;	// 회원가입 때 사용
	private PwHistoryDao pwHistoryDao;				// 회원가입 때 사용
	
	// CustomerList 출력
	// 사용하는 곳 : CustomerListController
	public ArrayList<Customer> getCustomerList(String searchCategory, String searchText, int currentPage, int rowPerPage) {
		
		ArrayList<Customer> list = null;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			list = new ArrayList<Customer>();
			
			int beginRow = Page.getBeginRow(currentPage, rowPerPage);
			
			this.customerDao = new CustomerDao();
			list = this.customerDao.selectCustomerList(conn, searchCategory, searchText, beginRow, rowPerPage);
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
	
	// CustomerList 페이징
	// 사용하는 곳 : CustomerListController
	public ArrayList<HashMap<String, Object>> getPage(String searchCategory, String searchText, int currentPage, int rowPerPage) {
		
		ArrayList<HashMap<String, Object>> list = null;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			this.customerDao = new CustomerDao();
			
			// 페이징 처리
			int pageLength = 10;
			int count = this.customerDao.countCustomer(conn, searchCategory, searchText);
			
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
	
	// customerOne customer 한명의 정보를 출력
	// 사용하는 곳 : UpdateCustomerController
	public Customer getCustomerOne(int customerCode) {

		Customer resultCustomer = null;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			this.customerDao = new CustomerDao();
			resultCustomer = this.customerDao.selectCustomerOne(conn, customerCode);
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
		
		return resultCustomer;
		
	}

	
	// customer 수정
	// 사용하는 곳 : UpdateCustomerController
	public int updateCustomer(Customer customer) {

		int resultRow = 0;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			this.customerDao = new CustomerDao();
			resultRow = this.customerDao.updateCustomer(conn, customer);
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
		
		return resultRow;
		
	}	
	
	// customer 삭제
	// 사용하는곳 : DeleteCustomerController
	public int deleteCustomer(int customerCode) {
		
		int resultRow = 0;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			this.customerDao = new CustomerDao();
			resultRow = this.customerDao.deleteCustomer(conn, customerCode);
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
		
		return resultRow;
		
	}
	
	// customer 추가 -> 회원 가입
	// 사용하는 곳 : AddCustomerController
	public int addCustomer(Customer customer, PwHistory pwHistory, CustomerAddress customerAddress) {
		
		int resultRow = 0;
		int resultRowAddress = 0;
		int resultRowPw = 0;
		
		Connection conn = null;
		
		try {
			
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			this.customerDao = new CustomerDao();
			this.customerAddressDao = new CustomerAddressDao();
			this.pwHistoryDao = new PwHistoryDao();
			
			
			resultRow = this.customerDao.addCustomer(conn, customer);
			resultRowAddress = this.customerAddressDao.addAddreses(conn, customerAddress);
			resultRowPw = this.pwHistoryDao.addPwHistory(conn, pwHistory);
			
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
		
		
		return resultRow;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}