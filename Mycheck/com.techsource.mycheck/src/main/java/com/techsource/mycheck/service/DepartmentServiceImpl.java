package com.techsource.mycheck.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.DepartmentDao;
import com.techsource.mycheck.vo.DepartmentVO;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	private final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);
	
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public int insertDepartment(DepartmentVO department) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DepartmentVO getRowByDepartment(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DepartmentVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepartmentVO getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(DepartmentVO department) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public List<DepartmentVO> getListByDivision(String divisionId) {
		logger.info("getListByDivision called");
		return departmentDao.getListByDivision(divisionId);
		
	}

}
