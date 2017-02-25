package com.techsource.mycheck.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.QuarterDao;
import com.techsource.mycheck.vo.QuarterVO;

@Service("quaterService")
public class QuaterServiceImpl implements QuaterService{
	
	private final Logger logger = LoggerFactory.getLogger(QuaterServiceImpl.class);

	@Autowired
	QuarterDao quaterDao;
	
	@Override
	public int insertQuater(QuarterVO quater) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<QuarterVO> getListByYear(String year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuarterVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuarterVO> getListByEmployeeId(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuarterVO getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(QuarterVO quater) {
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
	public List<QuarterVO>  getYearsbyEmpId(int id,String state) {
		// TODO Auto-generated method stub
		return (List<QuarterVO>) quaterDao.getYearsbyEmpId(id,state);
	}

	@Override
	@Transactional
	public List<QuarterVO> getYearAndQtrbyEmpId(int id,String year) {
		// TODO Auto-generated method stub
		 return (List<QuarterVO>) quaterDao.getYearAndQtrbyEmpId(id, year);
	}

}
