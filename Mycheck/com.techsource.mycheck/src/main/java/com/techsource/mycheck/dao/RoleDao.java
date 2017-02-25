package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.vo.RoleVO;

public interface RoleDao {
	public int insertRole(Role Role);

	public Role getRowByRole(String role);

	public List<RoleVO> getList();

	public Role getRowById(int id);

	public int updateRow(Role Role);

	public int deleteRow(int id);
}
