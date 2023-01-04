package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EmpService;
import vo.Emp;

@WebServlet("/emp/empList")
public class EmpListController extends HttpServlet {
	
	private EmpService empService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 인코딩 : UTF-8
		request.setCharacterEncoding("UTF-8");
		
		String searchCategory = "emp_code";
		if(request.getParameter("searchCategory") != null) {
			searchCategory = request.getParameter("searchCategory");
		}
		
		String searchText = "";
		if(request.getParameter("searchText") != null) {
			searchText = request.getParameter("searchText");
		}
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 10;
		if(request.getParameter("rowPerPage") != null) {
			rowPerPage = Integer.parseInt(request.getParameter("rowPerPage"));
		}
		
		
		this.empService = new EmpService();
		
		ArrayList<Emp> empList = new ArrayList<Emp>();
		
		empList = this.empService.getEmpList();
		
		request.setAttribute("empList", empList);
	
		request.getRequestDispatcher("/WEB-INF/view/emp/empList.jsp").forward(request, response);
		
		
	}

}
