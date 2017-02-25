package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.vo.RoleVO;

public interface RoleService {
	public int insertRole(Role Role);

	public List<RoleVO> getList();

	public Role getRowById(int id);

	public Role getRowByRole(String role);

	public int updateRow(Role Role);

	public int deleteRow(int id);
}
