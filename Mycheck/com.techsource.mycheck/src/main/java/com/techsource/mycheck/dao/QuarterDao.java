package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Quaters;
import com.techsource.mycheck.vo.QuarterVO;

public interface QuarterDao {
	public int insertQuater(Quaters quater);

	public List<QuarterVO> getList();

	public Quaters getRowById(int id);

	public int updateRow(Quaters quater);

	public int deleteRow(int id);

	public List<QuarterVO> getListByEmployeeId(String empId);
	
	public List<QuarterVO>  getYearsbyEmpId(int id,String state);
	
	public List<QuarterVO> getYearAndQtrbyEmpId(int id,String year);

	public Quaters getNextQuarterDetails(int empId,boolean flag);

	public boolean getQuartersExistsByEmpId(int id);
}
