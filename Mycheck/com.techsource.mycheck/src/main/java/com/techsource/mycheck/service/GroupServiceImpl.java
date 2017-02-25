package com.techsource.mycheck.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.GroupDao;
import com.techsource.mycheck.vo.GroupVO;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
	private final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);
	
	@Autowired
	private GroupDao groupDao;

	@Override
	public int insertGroup(GroupVO group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GroupVO getRowByGroup(String group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<GroupVO> getListByDepartment(String departmentId) {
		// TODO Auto-generated method stub
		return groupDao.getListByDepartment(departmentId);
	}

	@Override
	public GroupVO getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(GroupVO group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
