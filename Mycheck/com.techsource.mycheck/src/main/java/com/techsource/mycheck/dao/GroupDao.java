package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Group;
import com.techsource.mycheck.vo.GroupVO;

public interface GroupDao {
	public int insertGroup(Group Group);

	public Group getRowByGroup(String role);

	public List<GroupVO> getList();

	public Group getRowById(int id);

	public int updateRow(Group Group);

	public int deleteRow(int id);
	
	public List<GroupVO> getListByDepartment(String departmentId);
}
