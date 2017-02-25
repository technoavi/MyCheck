package com.techsource.mycheck.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.DivisionDao;
import com.techsource.mycheck.vo.DivisionVO;

@Service("divisionService")
public class DivisionServiceImpl implements DivisionService {

	private final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);

	@Autowired
	private DivisionDao divisionDao;

	@Override
	@Transactional
	public List<DivisionVO> getActiveList() {
		logger.info("getAll called");
		return divisionDao.getList();
	}

}
