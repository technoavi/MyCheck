package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.DepartmentVO;

public interface DepartmentService {
	public int insertDepartment(DepartmentVO department);

	public DepartmentVO getRowByDepartment(String role);

	public List<DepartmentVO> getList();

	public List<DepartmentVO> getListByDivision(String division);

	public DepartmentVO getRowById(int id);

	public int updateRow(DepartmentVO department);

	public int deleteRow(int id);
}
