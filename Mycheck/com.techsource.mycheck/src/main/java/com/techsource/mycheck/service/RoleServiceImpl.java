package com.techsource.mycheck.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.RoleDao;
import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.vo.RoleVO;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Override
	public int insertRole(Role Role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public List<RoleVO> getList() {
		logger.info("getList called");
		// TODO Auto-generated method stub
		return roleDao.getList();
	}

	@Override
	public Role getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getRowByRole(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(Role Role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
