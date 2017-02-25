package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.QuarterVO;

public interface QuaterService {

	public int insertQuater(QuarterVO quater);

	public List<QuarterVO> getListByYear(String year);

	public List<QuarterVO> getList();

	public List<QuarterVO> getListByEmployeeId(String empId);

	public QuarterVO getRowById(int id);

	public int updateRow(QuarterVO quater);

	public int deleteRow(int id);
	
	public List<QuarterVO> getYearsbyEmpId(int id,String state);
	
	public List<QuarterVO>getYearAndQtrbyEmpId(int id,String year);
}
