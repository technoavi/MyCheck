package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.vo.DepartmentVO;

public interface DepartmentDao {
	public int insertDepartment(Department Department);

	public Department getRowByDepartment(String role);

	public List<Department> getList();

	public Department getRowById(int id);

	public int updateRow(Department Department);

	public int deleteRow(int id);
	
	public List<DepartmentVO> getListByDivision(String division);
}
