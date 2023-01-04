package service;

import java.util.*;
import dao.*;

public class Page {

	// beginRow
	public int getBeginRow(int currentPage, int rowPerPage) {
		
		int beginRow = (currentPage - 1) * rowPerPage;
				
		return beginRow;
	}
	
	// previousPage
	public int getPreviousPage(int currentPage, int pageLength) {
		
		int previousPage = (((currentPage - 1) / pageLength) * pageLength) - (pageLength - 1);
		
		return previousPage;
		
	}
	
	// nextPage
	public int getNextPage(int currentPage, int pageLength) {
		
		int nextPage = (((currentPage - 1) / pageLength) * pageLength) + (pageLength + 1);
		
		return nextPage;
		
	}
	
	// lastpage
	public int getLastPage(int count, int rowPerPage) {
		
		int lastPage = count /rowPerPage;
		if((count % rowPerPage) != 0) {
			lastPage += 1;
		}
		
		return lastPage;
	}
	
	// pageList
	public ArrayList<Integer> getPageList(int currentPage, int pageLength) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int x=1; x<=pageLength; x+=1) {
			int page = (((currentPage -1) / pageLength) * pageLength) + x;
			list.add(page);
		}
		
		return list;
	}
	
	// 차트 월별 수입/지출의 연도 pageList
	public ArrayList<Integer> getYearPageList(int year, int pageLength) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		CashDao cashDao = new CashDao();
		
		map = cashDao.selectMinMaxYear();
		int minYear = (Integer) map.get("minYear");	// 최소년도
		
		for(int x=1; x<=pageLength; x+=1) {
			int page = minYear + (((year - minYear) / pageLength) * pageLength) + x - 1;
			list.add(page);
		}
		
		return list;
	}
	
	// previousYear
	public int getPreviousYear(int year, int pageLength) {
		
		int previousYear = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		CashDao cashDao = new CashDao();
		
		map = cashDao.selectMinMaxYear();
		int minYear = (Integer) map.get("minYear");	// 최소년도
		
		previousYear = minYear + (((year - minYear) / pageLength) * pageLength) - pageLength;
		
		return previousYear;
	} 
	
	// nextYear
	public int getNextYear(int year, int pageLength) {
		
		int nextYear = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		CashDao cashDao = new CashDao();
		
		map = cashDao.selectMinMaxYear();
		int minYear = (Integer) map.get("minYear");	// 최소년도
		
		nextYear = minYear + (((year - minYear) / pageLength) * pageLength) + pageLength;
		
		return nextYear;
	} 
	
	
}
