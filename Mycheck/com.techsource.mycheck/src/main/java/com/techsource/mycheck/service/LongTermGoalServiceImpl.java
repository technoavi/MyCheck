package com.techsource.mycheck.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.LongtermGoalDao;
import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyEmployee;
import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.LongTermGoal;
import com.techsource.mycheck.domain.LongTermGoalEmp;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.LongTermGoalVO;

@Service("longtermGoalService")
@Transactional
public class LongTermGoalServiceImpl implements LongtermGoalService {
	private final Logger logger = LoggerFactory.getLogger(LongTermGoalServiceImpl.class);

	@Autowired
	LongtermGoalDao longtermGoalDao;
	
	@Autowired
	EmployeeDao employeeDao;
	
	
@Override
//@Transactional
public boolean insertLongtermGoal(LongTermGoalVO longTermGoalVo) {
	logger.info(" insertLongtermGoalcaleed " );
	try {
		LongTermGoal longTermGoal= new LongTermGoal();
		longTermGoal.setName(longTermGoalVo.getName());
		longTermGoal.setDescription(longTermGoalVo.getDescription());
		longTermGoal.setStartDate(longTermGoalVo.getStartDate());
		longTermGoal.setEndDate(longTermGoalVo.getEndDate());
		longTermGoal.setCreated(new Date());
		longTermGoal.setDeleted(new Date());
		longTermGoal.setModified(new Date());
		
		longtermGoalDao.insertLongtermGoal(longTermGoal);
		
		Set<LongTermGoalEmp > longTermGoalEmps = new HashSet<LongTermGoalEmp>();
		// employee longterm relation
		LongTermGoalEmp longtermemp = new LongTermGoalEmp();
		longtermemp.setCreated(new Date());
		longtermemp.setModified(new Date());
		longtermemp.setDeleted(new Date());

		Employee employee = employeeDao.getById(longTermGoalVo.getEmpId());
		longtermemp.setEmployee(employee);
	
		employee.setLongTermGoalEmps(longTermGoalEmps);
		longtermemp.setLongTermGoal(longTermGoal);
		
		longTermGoalEmps.add(longtermemp);
		longTermGoal.setLongTermGoalEmps(longTermGoalEmps);
		
		
		return true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
return false;
}


/* (non-Javadoc)
 * @see com.techsource.mycheck.service.LongtermGoalService#updateLongTermGoal(com.techsource.mycheck.vo.LongTermGoalVO)
 */
@Override
public boolean updateLongTermGoal(LongTermGoalVO goalVO) {
	logger.info("updateSurveyBoard called ");
	try {
		
		LongTermGoal longTermGoal= longtermGoalDao.getRowById(goalVO.getId());
		longTermGoal.setName(goalVO.getName());
		longTermGoal.setDescription(goalVO.getDescription());
		longTermGoal.setStartDate(goalVO.getStartDate());
		longTermGoal.setEndDate(goalVO.getEndDate());
		longTermGoal.setModified(new Date());
		longtermGoalDao.updateLongTermGoal(longTermGoal);
		
		
		return true;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
}
/* (non-Javadoc)
 * @see com.techsource.mycheck.service.LongtermGoalService#deleteLongTermGoalById(int)
 */
@Override
//@Transactional
public boolean deleteLongTermGoalById(int id) {
	// TODO Auto-generated method stub
	 return longtermGoalDao.deleteLongTermGoalById(id);
	
}


/* (non-Javadoc)
 * @see com.techsource.mycheck.service.LongtermGoalService
 * #getlongtermGoalByEmpId(int)
 */
@Override
public List<LongTermGoalVO> getlongtermGoalByEmpId(int eId) {
	// TODO Auto-generated method stub
	return longtermGoalDao.getlongtermGoalByEmpId(eId);
	
}
/* (non-Javadoc)
 * @see com.techsource.mycheck.service.LongtermGoalService#getLongTermGoalBygoalId(int)
 */
@Override
public LongTermGoalVO getLongTermGoalBygoalId(int gId) {
	return longtermGoalDao.getLongTermGoalBygoalId(gId);
}
}
