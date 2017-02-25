package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.GroupVO;

public interface GroupService {
	public int insertGroup(GroupVO group);

	public GroupVO getRowByGroup(String group);

	public List<GroupVO> getList();

	public List<GroupVO> getListByDepartment(String departmentId);

	public GroupVO getRowById(int id);

	public int updateRow(GroupVO group);

	public int deleteRow(int id);
}
