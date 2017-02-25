package com.techsource.mycheck.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.SurveyDao;
import com.techsource.mycheck.dao.SurveyQuestionDao;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.SurveyQuestion;
import com.techsource.mycheck.domain.SurveySurveyqstn;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestSurveyBoard;
import com.techsource.mycheck.vo.SurveyPercentageVO;
import com.techsource.mycheck.vo.SurveyResultVO;
import com.techsource.mycheck.vo.SurveyVO;

@Service("surveyQuestionService")
public class SurveyQuestionServiceImpl implements SurveyQuestionService {
	private final Logger logger = LoggerFactory.getLogger(SurveyQuestionServiceImpl.class);

	@Autowired
	SurveyQuestionDao surveyQuestionDao;

	@Autowired
	SurveyDao surveyDao;

	@Autowired
	EmployeeDao employeeDao;

	@Override
	@Transactional
	public boolean deleteQuestionById(int qstnId) {

		return surveyQuestionDao.deleteQuestionById(qstnId);
	}

	@Override
	@Transactional
	public boolean addSurveyQuestion(RequestSurveyBoard requestSurveyBoard) {

		try {
			SurveyVO surveyBDVO = requestSurveyBoard.getSurvey();
			Survey survey = surveyDao.getRowById(surveyBDVO.getId());
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
			List<CommonSurveyQuestionVO> surveyQueastionVO = requestSurveyBoard.getSurveyQuestions();
			Set<SurveySurveyqstn> surveysurveyQstn = new HashSet<SurveySurveyqstn>();
			for (CommonSurveyQuestionVO surveyQsnVO : surveyQueastionVO) {
				// target board questions
				SurveyQuestion surveyQuestion = new SurveyQuestion();
				surveyQuestion.setCreated(new Date());
				surveyQuestion.setModified(new Date());
				surveyQuestion.setDeleted(new Date());
				surveyQuestion.setName(surveyQsnVO.getName());
				surveyQuestion.setRating(surveyQsnVO.getRating());
				surveyQuestionDao.addSurveyQuestion(surveyQuestion);
				// relation of target board and target board questions
				SurveySurveyqstn surveysrveyqstn2questions2 = new SurveySurveyqstn();
				surveysrveyqstn2questions2.setSurveyQuestion(surveyQuestion);
				surveysrveyqstn2questions2.setSurvey(survey);
				surveysrveyqstn2questions2.setCreated(new Date());
				surveysrveyqstn2questions2.setModified(new Date());
				surveysrveyqstn2questions2.setDeleted(new Date());

				surveysurveyQstn.add(surveysrveyqstn2questions2);
				surveyQuestion.setSurveySurveyqstns(surveysurveyQstn);

			}

			survey.setSurveySurveyqstns(surveysurveyQstn);
			surveyDao.updateRow(survey);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public List<CommonSurveyQuestionVO> getSurveryQuestionsBySurveyId(int id) {
		// TODO Auto-generated method stub
		return surveyQuestionDao.getSurveryQuestionsBySurveyId(id);

	}

	@Override
	@Transactional
	public boolean updateQuestionBySurveyId(int id, String name) {
		logger.info("updateQuestionBySurveyId called ");
		try {
			return surveyQuestionDao.updateQuestionBySurveyId(id, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public List<CommonSurveyQuestionVO> getSurveryQuestionsByEmpId(int id) {
		logger.info("getSurveryQuestionsByEmpId called ");
		List<CommonSurveyQuestionVO> list1 = null;
		try {
			boolean flag = surveyDao.checkEmployeeQuestion(id);
			if (flag) {
				throw new Exception("already exist");
			} else {
				EmployeeVO empVo = employeeDao.getgrpDepDevsnByLinemngrId(id);
				SurveyVO surveyVOs = surveyDao.getSurveyIdByDepDivGrp(empVo.getDepartmentId(), empVo.getDivisionId(),
						empVo.getGroupId());
				list1 = surveyQuestionDao.getSurveryQuestionsBySurveyId(surveyVOs.getId());
				return list1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;

	}

	@Override
	@Transactional
	public int getSurverIdByEmpId(int id) {
		logger.info("getSurverIdEmpId called ");
		EmployeeVO empVo = employeeDao.getgrpDepDevsnByLinemngrId(id);
		System.out.println("employee vos ;"+ empVo.getId());
		System.out.println(
				"dep " + empVo.getDepartmentId() + " div " + empVo.getDivisionId() + "gp " + empVo.getGroupId());
		SurveyVO surveyVOs = surveyDao.getSurveyIdByDepDivGrp(empVo.getDepartmentId(), empVo.getDivisionId(),
				empVo.getGroupId());
		System.out.println("id " + surveyVOs.getId());
		int sid = surveyVOs.getId();
		System.out.println("sid ddddd   " + sid);

		return sid;
	}

	@Override
	@Transactional
	public SurveyPercentageVO getSurveryQuestionsPercentBySurveyId(int id) {
		// return surveyQuestionDao.getSurveryQuestionsPercentBySurveyId( id);
		logger.info("getSurveryQuestionsPercentBySurveyId called ");
		SurveyPercentageVO surveyPercentageVO = new SurveyPercentageVO();
		int ovr=0;
		int totalQuestions=0;
		int overallPercentage=0;
		try {
			// 1.getting grp dep div by surveyId
			SurveyVO surveyVO = surveyDao.getGrpDepDevsnBySurveyId(id);
			if(surveyVO!=null){
			System.out.println("dep " + surveyVO.getDepartmentId() + " div " + surveyVO.getDivisionId() + "gp "
					+ surveyVO.getGroupId());

			// 2. get all the emp and count no. of emp based on div dep and grp

			List<CommonSurveyQuestionVO> surveyQuestionVOs2 = new ArrayList<CommonSurveyQuestionVO>();
			List<EmployeeVO> totalEmployees = employeeDao.getEmpByDivDepIdGrpId(surveyVO.getDivisionId(),surveyVO.getDepartmentId(), surveyVO.getGroupId());
			if (totalEmployees.size() > 0) {
				System.out.println("totalEmployees ::" + totalEmployees.size());
				// 3. get emp who participated in survey in survey_emp table
				List<EmployeeVO> participatedEmployees = surveyDao.getSurveyEmpBySurveyId(id);
				System.out.println("participaed emp ::" + participatedEmployees.size());

				for (EmployeeVO employeeVO1 : participatedEmployees) {

				}

				// 4. get all the question by surveyId

				List<CommonSurveyQuestionVO> surveyQuestionVOs = surveyQuestionDao.getSurveryQuestionsBySurveyId(id);

				for (CommonSurveyQuestionVO surveyQuestionVO : surveyQuestionVOs) {

					// get surveyQuestionResults by surveyQuestionId
					List<SurveyResultVO> resultVOs = surveyQuestionDao
							.getRatingsSurveyQuestionId(surveyQuestionVO.getId());
					int sqId = 0;
					String name = null;
			
					int totalRating = 0;
					for (SurveyResultVO surveyResultVO : resultVOs) {
						sqId = surveyResultVO.getId();
						name = surveyResultVO.getName();

						totalRating = totalRating + surveyResultVO.getRating();
					}
					totalQuestions++;

					// int participatedAvg = totalRating /
					// participatedEmployees.size() * 10;

					int totalAvg = totalRating / totalEmployees.size() * 10;

					// System.out.println(" final question results :: " + sqId +
					// " -- " + participatedAvg + " -- " + totalAvg);

					CommonSurveyQuestionVO surveyQuestionVO2 = new CommonSurveyQuestionVO();
					surveyQuestionVO2.setId(sqId);
					surveyQuestionVO2.setName(name);
					surveyQuestionVO2.setRating(totalAvg);
					surveyQuestionVOs2.add(surveyQuestionVO2);
 ovr=ovr+totalAvg;
				}
			
float overallPercentage1= (float) ovr/totalQuestions;
DecimalFormat df = new DecimalFormat("###.#");
System.out.println("total marks"+overallPercentage1);
				surveyPercentageVO.setSurveyId(id);
				surveyPercentageVO.setTotalEmployees(totalEmployees.size());
				surveyPercentageVO.setParticipateEmployees(participatedEmployees.size());
				surveyPercentageVO.setSurveyQuestions(surveyQuestionVOs2);
				surveyPercentageVO.setOverallPercentage(df.format(overallPercentage1));

			} else {
				throw new Exception("Employee Not Found");
			}
			}else{
				throw new Exception("Group Dep Div Not found");
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return surveyPercentageVO;
	}

}
