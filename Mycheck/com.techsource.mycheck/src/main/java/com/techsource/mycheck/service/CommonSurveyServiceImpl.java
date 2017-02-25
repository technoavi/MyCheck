package com.techsource.mycheck.service;

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

import com.techsource.mycheck.dao.CommonSurveyDao;
import com.techsource.mycheck.dao.CommonSurveyQuestionDao;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyEmployee;
import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.domain.CommonSurveyQuestionsRelation;
import com.techsource.mycheck.domain.CommonSurveySubmission;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.CommonSurveyPercentageVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.RequestCommonSurveyBoard;

@Service("commonSurveyService")
public class CommonSurveyServiceImpl implements CommonSurveyService {
	private final Logger logger = LoggerFactory.getLogger(CommonSurveyServiceImpl.class);

	@Autowired
	private CommonSurveyDao commonSurveyDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CommonSurveyQuestionDao commonSurveyQuestionDao;
	
	@Autowired
	private CommonSurveyQuestionService commonSurveyQuestionService;

	/*
	 * @Override
	 * 
	 * @Transactional public boolean
	 * insertCommonSurveyBoard(RequestCommonSurveyBoard
	 * requestCommonSurveyBoard) {
	 * logger.info(" into insertCommonSurveyBoard()");
	 */

	/*
	 * try { CommonSurveyVO surveyBoardVO =
	 * requestCommonSurveyBoard.getCommonsurvey();
	 * 
	 * 
	 * CommonSurvey commonSurvey = new CommonSurvey();
	 * commonSurvey.setName(surveyBoardVO.getName());
	 * commonSurvey.setDescription(surveyBoardVO.getDescription());
	 * commonSurvey.setStartDate(surveyBoardVO.getStartDate());
	 * commonSurvey.setEndDate(surveyBoardVO.getEndDate());
	 * commonSurvey.setStatus(Constants.ACTIVE); commonSurvey.setCreated(new
	 * Date()); commonSurvey.setModified(new Date());
	 * commonSurvey.setDeleted(new Date()); commonSurvey.setState("OPEN");
	 * logger.debug("survey vo " + commonSurvey);
	 * 
	 * List<CommonSurveyQuestionVO> surveyQuestionsVO =
	 * requestCommonSurveyBoard.getCommonSurveyQuestion(); Set<SurveySurveyqstn>
	 * surveyTargetquestions = new HashSet<SurveySurveyqstn>(); for
	 * (CommonSurveyQuestionVO surveyQuestionVO : surveyQuestionsVO) { // target
	 * board questions CommonSurveyQuestion surveytQuestion = new
	 * CommonSurveyQuestion(); surveytQuestion.setCreated(new Date());
	 * surveytQuestion.setModified(new Date()); surveytQuestion.setDeleted(new
	 * Date()); surveytQuestion.setName(surveyQuestionVO.getName());
	 * surveytQuestion.setRating(surveyQuestionVO.getRating());
	 * commonSurveyQuestionDao.insertCommonSurveyQuestion(surveytQuestion);
	 * logger.debug("survey qstn " + surveytQuestion); // relation of target
	 * board and target board questions
	 * 
	 * SurveySurveyqstn surveyBoardSurveyQuestn = new SurveySurveyqstn();
	 * surveyBoardSurveyQuestn.setSurveyQuestion(surveytQuestion);
	 * surveyBoardSurveyQuestn.setSurvey(commonSurvey);
	 * surveyBoardSurveyQuestn.setCreated(new Date());
	 * surveyBoardSurveyQuestn.setModified(new Date());
	 * surveyBoardSurveyQuestn.setDeleted(new Date());
	 * 
	 * 
	 * surveyTargetquestions.add(surveyBoardSurveyQuestn);
	 * surveytQuestion.setSurveySurveyqstns(surveyTargetquestions);
	 * logger.info("surveyqstn "); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return true;
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return false; }
	 */

	@Override
	@Transactional
	public boolean insertCommonSurveyBoard(RequestCommonSurveyBoard requestCommonSurveyBoard) {
		logger.info(" into insertCommonSurveyBoard()");

		try {
			CommonSurveyVO commonSurveyVO = requestCommonSurveyBoard.getCommonsurvey();
			CommonSurvey commonSurvey = new CommonSurvey();
			commonSurvey.setName(commonSurveyVO.getName());
			commonSurvey.setDescription(commonSurveyVO.getDescription());
			commonSurvey.setStatus(Constants.ACTIVE);
			commonSurvey.setStartDate(commonSurveyVO.getStartDate());
			commonSurvey.setEndDate(commonSurveyVO.getEndDate());
			commonSurvey.setCreated(new Date());
			commonSurvey.setModified(new Date());
			commonSurvey.setDeleted(new Date());
			commonSurvey.setState("OPEN");
			// commonSurveyQuestions,commonSurveyQuestionsRelations,commonSurveyEmployees
			// Set of target board questions
			List<CommonSurveyQuestionVO> surveyQuestionVO = requestCommonSurveyBoard.getCommonSurveyQuestion();
			Set<CommonSurveyQuestionsRelation> commonsurveyQuestionVO = new HashSet<CommonSurveyQuestionsRelation>();
			for (CommonSurveyQuestionVO commonSurveyQuestionVO : surveyQuestionVO) {
				// target board questions
				CommonSurveyQuestions commonSurveyQuestion = new CommonSurveyQuestions();
				commonSurveyQuestion.setCreated(new Date());
				commonSurveyQuestion.setModified(new Date());
				commonSurveyQuestion.setDeleted(new Date());
				commonSurveyQuestion.setRating(commonSurveyQuestionVO.getRating());
				commonSurveyQuestion.setName(commonSurveyQuestionVO.getName());
				commonSurveyQuestionDao.insertCommonSurveyQuestion(commonSurveyQuestion);
				// relation of target board and target board questions
				CommonSurveyQuestionsRelation commonSurveyQstnRelation = new CommonSurveyQuestionsRelation();
				commonSurveyQstnRelation.setCommonSurveyQuestions(commonSurveyQuestion);
				commonSurveyQstnRelation.setCommonSurvey(commonSurvey);
				commonSurveyQstnRelation.setCreated(new Date());
				commonSurveyQstnRelation.setModified(new Date());
				commonSurveyQstnRelation.setDeleted(new Date());

				commonsurveyQuestionVO.add(commonSurveyQstnRelation);
				// commonSurveyQuestion.setCommonSurveyQuestionsRelations(commonSurveyQstnRelation);
				commonSurveyQuestion.setCommonSurveyQuestionsRelations(commonsurveyQuestionVO);
			}
			commonSurveyDao.insertCommonSurveyBoard(commonSurvey);

			Set<CommonSurveyEmployee> commonsurveyemployees = new HashSet<CommonSurveyEmployee>();
			// employee,quarter and targetBoard relation
			CommonSurveyEmployee empQuatecommonsurveyemployee = new CommonSurveyEmployee();
			empQuatecommonsurveyemployee.setCreated(new Date());
			empQuatecommonsurveyemployee.setModified(new Date());
			empQuatecommonsurveyemployee.setDeleted(new Date());
			empQuatecommonsurveyemployee.setStatus("OPEN");
			commonsurveyemployees.add(empQuatecommonsurveyemployee);

			Employee employee = employeeDao.getById(requestCommonSurveyBoard.getEmpId());
			empQuatecommonsurveyemployee.setEmployee(employee);
			employee.setCommonSurveyEmployees(commonsurveyemployees);

			empQuatecommonsurveyemployee.setCommonSurvey(commonSurvey);
			commonSurvey.setCommonSurveyEmployees(commonsurveyemployees);
			// targetBoard.setTargetboardTargetquestionses(targetboardTargetquestions);
			commonSurvey.setCommonSurveyQuestionsRelations(commonsurveyQuestionVO);
			// commonSurvey.setCommonSurveyQuestionEmployeeRelations(commonsurveyQuestionVO);

			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	@Transactional
	public boolean updateCommonSurveyBoard(RequestCommonSurveyBoard requestCommonSurveyBoard) {
		logger.info("updateSurveyBoard called ");
		try {
			CommonSurveyVO commonSurveyVO = requestCommonSurveyBoard.getCommonsurvey();

			CommonSurvey commonsurvey = commonSurveyDao.getRowById(commonSurveyVO.getId());
			commonsurvey.setName(commonSurveyVO.getName());
			commonsurvey.setDescription(commonSurveyVO.getDescription());
			commonsurvey.setModified(new Date());
			commonSurveyDao.updateRow(commonsurvey);
			for (CommonSurveyQuestionVO commonSurveyQstnVo : requestCommonSurveyBoard.getCommonSurveyQuestion()) {
				CommonSurveyQuestions commonQuestions = commonSurveyQuestionDao.getRowById(commonSurveyQstnVo.getId());
				commonQuestions.setRating(commonSurveyQstnVo.getRating());
				commonQuestions.setModified(new Date());
				commonQuestions.setName(commonSurveyQstnVo.getName());
				commonSurveyQuestionDao.updateRow(commonQuestions);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public List<CommonSurveyVO> getCommonSurveyList() {
		logger.info("getCommonSurveyList called");
		return commonSurveyDao.getCommonSurveyList();
	}

	@Override
	@Transactional
	public boolean checkCommonSurveyNameExist(String name) {
		// TODO Auto-generated method stub
		return commonSurveyDao.checkCommonSurveyNameExist(name);
	}

	@Override
	@Transactional
	public CommonSurveyVO getCommonSurveyDetailsById(int id) {
		// TODO Auto-generated method stub
		return commonSurveyDao.getCommonSurveyDetailsById(id);
	}

	private boolean comapre(List<CommonSurveyVO> list, int id) {

		for (CommonSurveyVO commonSurveyVO : list) {

			if (commonSurveyVO.getId() == id) {
				return true;
			}

		}
		return false;

	}

	@Override
	@Transactional
	public List<CommonSurveyVO> getCommonSurveyByEmpId(int id) {
		// TODO Auto-generated method stub
		// List<CommonSurveyVO> commonSurveyVOs2 = null;

		try {

			List<CommonSurveyVO> commonSurveyVOs2 = commonSurveyDao.getCommonSurveyByEmpId(id);
			//boolean flag = commonSurveyDao.checkCommonSurveyEmployeeParticipatedByEmpyId(id);
			//System.out.println(flag);
			//if (flag) {
				// List<CommonSurveyVO> commonSurveyVOs3 = new
				// ArrayList<CommonSurveyVO>();
				List<CommonSurveyVO> commonSurveyVOs = commonSurveyDao.getCommonSurveysByEmpyIdAlreadyparticipated(id);
				System.out.println("surveysss are :" + commonSurveyVOs.size());
				// if (commonSurveyVOs != null) {
				// for (CommonSurveyVO commonSurveyVO : commonSurveyVOs) {
				// System.out.println("ids are " + commonSurveyVO.getId());
				// CommonSurveyVO commonSurveyVOs4 = commonSurveyDao
				// .getCommonSurveyByNotParticipated(commonSurveyVO.getId());
				// if (commonSurveyVOs4 != null) {
				// commonSurveyVOs3.add(commonSurveyVOs4);
				// }
				// // System.out.println("surveys are
				// // :"+commonSurveyVOs3.size());
				//
				// }
				//
				// return commonSurveyVOs3;
				// }
				List<CommonSurveyVO> commonSurveyVOs3 = new ArrayList<CommonSurveyVO>();
				for (CommonSurveyVO commonSurveyVO : commonSurveyVOs2) {
					if (!this.comapre(commonSurveyVOs, commonSurveyVO.getId())) {
						commonSurveyVOs3.add(commonSurveyVO);
					}

				}
				return commonSurveyVOs3;

				// return commonSurveyVOs2;
			//}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	@Transactional
	public List<CommonSurveyVO> getCommonSurveyByDate(String sdate, String edate) {
		// TODO Auto-generated method stub
		return commonSurveyDao.getCommonSurveyByDate(sdate, edate);
	}
	@Override
	@Transactional
	public boolean closingCommonSurveyBySurveyId(int sid) {
		try {
			CommonSurveyPercentageVO vb=commonSurveyQuestionService.getCommonSurveryQuestionsPercentBySurveyId(sid);
			CommonSurveySubmission  submission= new  CommonSurveySubmission();
			submission.setCommonSurvey(commonSurveyDao.getRowById(sid));
			submission.setTotalEmployee(vb.getTotalEmployees());
			submission.setParticipatedEmployee(String.valueOf(vb.getParticipateEmployees()));
			int totalEmp=vb.getTotalEmployees();
			int partcEmp=vb.getParticipateEmployees();
			int uncp=totalEmp-partcEmp;
			submission.setUnparticipatedEmp(String.valueOf(uncp));
			submission.setParticipatedPerc(String.valueOf(vb.getOverallPercentage()));
			submission.setStatus("CLOSE");
			submission.setCreated(new Date() );
			submission.setModified(new Date() );
			submission.setDeleted(new Date() );
		boolean flag=	commonSurveyDao.insertCommonSurveySubmission(submission);
			if(flag){
				//Survey survey= new Survey();
				
				CommonSurvey survey = commonSurveyDao.getRowById(sid);
				survey.setState("CLOSE");
				//survey.setStatus("INACTIVE");
				survey.setModified(new Date());
				commonSurveyDao.updateRow(survey);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
