package com.techsource.mycheck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.techsource.mycheck.dao.CommonSurveyDao;
import com.techsource.mycheck.dao.CommonSurveyQuestionDao;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyEmployee;
import com.techsource.mycheck.domain.CommonSurveyQuestionEmployeeRelation;
import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.domain.CommonSurveyQuestionsRelation;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.vo.CommonSurveyPercentageVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestCommonSurveyBoard;
import com.techsource.mycheck.vo.RequestCommonSurveyRating;

@Service("commonSurveyQuestionService")
public class CommonSurveyQuestionServiceImpl implements CommonSurveyQuestionService {
	
	private final Logger logger = LoggerFactory.getLogger(CommonSurveyQuestionServiceImpl.class);

	@Autowired
	CommonSurveyQuestionDao commonSurveyQuestionDao;
	
	@Autowired
	CommonSurveyDao commonSurveyDao;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	@Transactional
	public List<CommonSurveyQuestionVO> getCommonSurveryQuestionsBySurveyId(int id) {
		// TODO Auto-generated method stub
		return commonSurveyQuestionDao.getCommonSurveryQuestionsBySurveyId(id);
	}
	
	
	@Override
	@Transactional
	public boolean deleteQuestionByQstnId(int id) {
		// TODO Auto-generated method stub
		return commonSurveyQuestionDao.deleteCommonSurveyQuestionByQstnId(id);
	}
	
	@Override
	@Transactional
	public boolean updateCommonSurveyStatusByCommonSurveyId(int id) {
		// TODO Auto-generated method stub
		return commonSurveyDao.updateCommonSurveyStatusByCommonSurveyId(id);
	}
	@Override
	@Transactional
	public boolean addCommonSurveyQuestion(RequestCommonSurveyBoard requestSurveyBoard) {

		try {
			CommonSurveyVO surveyBDVO = requestSurveyBoard.getCommonsurvey();
			CommonSurvey survey = commonSurveyDao.getRowById(surveyBDVO.getId());
			if (survey == null) {
				return false;
			}
			// targetBoard.setName(targetBoardVO.getName());
			// targetBoard.setDescription(targetBoardVO.getDescription());
			// targetBoard.setStatus(Constants.ACTIVE);
			// targetBoard.setCreated(new Date());
			survey.setModified(new Date());
			// targetBoard.setDeleted(new Date());

			// Set of target board questions
			List<CommonSurveyQuestionVO> surveyQueastionVO = requestSurveyBoard.getCommonSurveyQuestion();
			Set<CommonSurveyQuestionsRelation> surveysurveyQstn = new HashSet<CommonSurveyQuestionsRelation>();
			for (CommonSurveyQuestionVO surveyQsnVO : surveyQueastionVO) {
				// target board questions
				CommonSurveyQuestions surveyQuestion = new CommonSurveyQuestions();
				surveyQuestion.setCreated(new Date());
				surveyQuestion.setModified(new Date());
				surveyQuestion.setDeleted(new Date());
				surveyQuestion.setName(surveyQsnVO.getName());
				surveyQuestion.setRating(surveyQsnVO.getRating());
				commonSurveyQuestionDao.addCommonSurveyQuestion(surveyQuestion);
				// relation of target board and target board questions
				CommonSurveyQuestionsRelation surveysrveyqstn2questions2 = new CommonSurveyQuestionsRelation();
				surveysrveyqstn2questions2.setCommonSurveyQuestions(surveyQuestion);
				surveysrveyqstn2questions2.setCommonSurvey(survey);
				surveysrveyqstn2questions2.setCreated(new Date());
				surveysrveyqstn2questions2.setModified(new Date());
				surveysrveyqstn2questions2.setDeleted(new Date());

				surveysurveyQstn.add(surveysrveyqstn2questions2);
				surveyQuestion.setCommonSurveyQuestionsRelations(surveysurveyQstn);

			}

			survey.setCommonSurveyQuestionsRelations(surveysurveyQstn);
			commonSurveyDao.updateRow(survey);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Override
	@Transactional
	public boolean updateCommonSurveyQuestionByCommonSurveyId(int id, String name) {
		logger.info("updateCommonSurveyQuestionByCommonSurveyId called ");
		try {
			return commonSurveyQuestionDao.updateCommonSurveyQuestionByCommonSurveyId(id, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Override
	@Transactional
	public List<CommonSurveyQuestionVO> getCommonSurveyQuestionBySurveyId(int id,int eid) {
		logger.info("getCommonSurveyQuestionBySurveyId called ");	
		List<CommonSurveyQuestionVO> list1 = null;
	
		try {
			boolean flag = commonSurveyDao.checkCommonSurveyEmployeeParticipatedByEmpyId(eid);

			if (!flag) {

				list1 = commonSurveyQuestionDao.getCommonSurveryQuestionsBySurveyId(id);
				System.out.println("sq :"+list1);
				return list1;
			} else {
				//EmployeeVO empVo = employeeDao.getgrpDepDevsnByLinemngrId(id);
			
				list1 = null;
				System.out.println("sq null:"+list1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}
	@Override
	@Transactional
	public boolean insertCommonSurveyRating(RequestCommonSurveyRating requestSurveyRating) {
		logger.info(" into surveyBoard()");
		try {

			Map<Integer, Integer> datas = requestSurveyRating.getCommonSurveyQuestion();
			CommonSurvey survey = commonSurveyDao.getRowById(requestSurveyRating.getCommonSurveyId());
			Employee employee = employeeDao.getById(requestSurveyRating.getEmpId());

			for (Map.Entry<Integer, Integer> entry : datas.entrySet()) {
				System.out.println(entry.getKey() + "/" + entry.getValue());
				// entry.getKey() is survey question id and entry.getValue() is
				// survey question rating
				CommonSurveyQuestions surveyQuestion = commonSurveyQuestionDao.getRowById(entry.getKey());

				// 4. save the survey rating

				Set<CommonSurveyQuestionEmployeeRelation> commonsurveyQstnEmpRelations = new HashSet<CommonSurveyQuestionEmployeeRelation>();

				CommonSurveyQuestionEmployeeRelation commonsurveyqstnrelation = new CommonSurveyQuestionEmployeeRelation();
				commonsurveyqstnrelation.setCommonSurvey(survey);
				commonsurveyqstnrelation.setEmployee(employee);
				commonsurveyqstnrelation.setCommonSurveyQuestions(surveyQuestion);
				int i = (int) entry.getValue();
				commonsurveyqstnrelation.setRating(i);
				commonsurveyqstnrelation.setCreated(new Date());
				commonsurveyqstnrelation.setModified(new Date());
				commonsurveyqstnrelation.setDeleted(new Date());

				commonsurveyQstnEmpRelations.add(commonsurveyqstnrelation);

				survey.setCommonSurveyQuestionEmployeeRelations(commonsurveyQstnEmpRelations);
				employee.setCommonSurveyQuestionEmployeeRelations(commonsurveyQstnEmpRelations);
				surveyQuestion.setCommonSurveyQuestionEmployeeRelations(commonsurveyQstnEmpRelations);
				
				//5. insert SurveyResult
				commonSurveyDao.insertCommonSurveyRating(commonsurveyqstnrelation);

			}
			if(datas.size()>0){
				Set<CommonSurveyEmployee> surveyEmployees = new HashSet<CommonSurveyEmployee>();
				CommonSurvey survey1 = commonSurveyDao.getRowById(requestSurveyRating.getCommonSurveyId());
				
				Employee employee1 = employeeDao.getById(requestSurveyRating.getEmpId());
				CommonSurveyEmployee commonsurveyEmployee = new CommonSurveyEmployee();
				commonsurveyEmployee.setCommonSurvey(survey1);
				commonsurveyEmployee.setEmployee(employee1);
				commonsurveyEmployee.setStatus("CLOSE");
			//	surveyEmployee.setCreated(new Date());
				commonsurveyEmployee.setModified(new Date());
			//	surveyEmployee.setDeleted(new Date());
				commonSurveyDao.updateCommonSurveyEmployee(commonsurveyEmployee);

			
			
			}
			
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	@Transactional
	public CommonSurveyPercentageVO getCommonSurveryQuestionsPercentBySurveyId(int id) {
		logger.info("getSurveryQuestionsPercentBySurveyId called ");
		CommonSurveyPercentageVO surveyPercentageVO = new CommonSurveyPercentageVO();
		int totalRating = 0;
		//int totalAvg=0;
		try {
			List<CommonSurveyQuestionVO> surveyQuestionVOs2 = new ArrayList<CommonSurveyQuestionVO>();
		List<EmployeeVO> totalEmployees = employeeDao.totalEmployees();
			if (totalEmployees.size() > 0) {
				//System.out.println("totalEmployees ::" + totalEmployees.size());
				
				List<EmployeeVO> participatedEmployees = commonSurveyDao.getCommonSurveyEmpBySurveyId(id);
				System.out.println("participaed emp ::" + participatedEmployees.size());
if(participatedEmployees!=null){
				List<CommonSurveyQuestionVO> surveyQuestionVOs = commonSurveyQuestionDao.getCommonSurveryQuestionsBySurveyId(id);

				for (CommonSurveyQuestionVO surveyQuestionVO : surveyQuestionVOs) {

					List<CommonSurveyQuestionEmployeeRelationVO> resultVOs = commonSurveyQuestionDao
							.getRatingsCommonSurveyQuestionId(surveyQuestionVO.getId());
					int sqId = 0;
					String name = null;
					
					for (CommonSurveyQuestionEmployeeRelationVO surveyResultVO : resultVOs) {
						sqId = surveyResultVO.getId();
						name = surveyResultVO.getName();

						totalRating = totalRating + surveyResultVO.getRating();
					}
					int totalAvg = totalRating / totalEmployees.size() * 10;


					CommonSurveyQuestionVO surveyQuestionVO2 = new CommonSurveyQuestionVO();
					surveyQuestionVO2.setId(sqId);
					surveyQuestionVO2.setName(name);
					surveyQuestionVO2.setRating(totalAvg);
					surveyQuestionVOs2.add(surveyQuestionVO2);

				}

				surveyPercentageVO.setCommonSurveyId(id);
				surveyPercentageVO.setTotalEmployees(totalEmployees.size());
				surveyPercentageVO.setParticipateEmployees(participatedEmployees.size());
				surveyPercentageVO.setSurveyQuestions(surveyQuestionVOs2);

				System.out.println("final question results  " + new Gson().toJson(surveyPercentageVO));
				
			} else {
				throw new Exception("Employee Not partici[pated");
			}
			}else {
				throw new Exception("Employee Not Found");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return surveyPercentageVO;
	}
	
}
