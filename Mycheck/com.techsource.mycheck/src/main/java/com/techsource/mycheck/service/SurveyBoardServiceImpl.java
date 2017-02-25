package com.techsource.mycheck.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.DepartmentDao;
import com.techsource.mycheck.dao.DivisionDao;
import com.techsource.mycheck.dao.EmailGroupDao;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.GroupDao;
import com.techsource.mycheck.dao.SurveyDao;
import com.techsource.mycheck.dao.SurveyQuestionDao;
import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Group;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.SurveyEmployee;
import com.techsource.mycheck.domain.SurveyQuestion;
import com.techsource.mycheck.domain.SurveyResult;
import com.techsource.mycheck.domain.SurveySubmission;
import com.techsource.mycheck.domain.SurveySurveyqstn;
import com.techsource.mycheck.domain.Surveydetails;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MailGroup;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestSurveyBoard;
import com.techsource.mycheck.vo.RequestSurveyRating;
import com.techsource.mycheck.vo.SurveyPercentageVO;
import com.techsource.mycheck.vo.SurveyVO;

@Service("surveyBoardService")
public class SurveyBoardServiceImpl implements SurveyBoardService {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.techsource.mycheck.service.SurveyBoardService#checkSurveyNameExist(
	 * java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.techsource.mycheck.service.SurveyBoardService#insertSurveyRating(com.
	 * techsource.mycheck.vo.RequestSurveyRating)
	 */
	private final Logger logger = LoggerFactory.getLogger(SurveyBoardServiceImpl.class);

	@Autowired
	private SurveyQuestionDao surveyQuestionDao;
	

	@Autowired
	private SurveyDao surveyDao;

	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmailGroupDao emailGroupDao;
	
	@Autowired
	private SurveyQuestionService surveyQuestionService;

	@Override
	@Transactional
	public boolean insertSurveyRating(RequestSurveyRating requestSurveyRating) {
		logger.info(" into surveyBoard()");
		try {

			Map<Integer, Integer> datas = requestSurveyRating.getSurveyQuestion();
			// 1. get survey object
			Survey survey = surveyDao.getRowById(requestSurveyRating.getSurveyId());
			// 2. get employee
			Employee employee = employeeDao.getById(requestSurveyRating.getEmpId());

			// 3. get survey questions
			for (Map.Entry<Integer, Integer> entry : datas.entrySet()) {
				System.out.println(entry.getKey() + "/" + entry.getValue());
				// entry.getKey() is survey question id and entry.getValue() is
				// survey question rating
				SurveyQuestion surveyQuestion = surveyQuestionDao.getRowById(entry.getKey());

				// 4. save the survey rating

				Set<SurveyResult> surveyResults = new HashSet<SurveyResult>();

				SurveyResult surveyResult = new SurveyResult();
				surveyResult.setSurvey(survey);
				surveyResult.setEmployee(employee);
				surveyResult.setSurveyQuestion(surveyQuestion);
				int i = (int) entry.getValue();
				surveyResult.setRating((byte) i);
				surveyResult.setCreated(new Date());
				surveyResult.setModified(new Date());
				surveyResult.setDeleted(new Date());

				surveyResults.add(surveyResult);

				survey.setSurveyResults(surveyResults);
				employee.setSurveyResults(surveyResults);
				surveyQuestion.setSurveyResults(surveyResults);
				
				//5. insert SurveyResult
				surveyDao.insertSurveyRating(surveyResult);

			}
			if(datas.size()>0){
				Set<SurveyEmployee> surveyEmployees = new HashSet<SurveyEmployee>();
				Survey survey1 = surveyDao.getRowById(requestSurveyRating.getSurveyId());
				
				Employee employee1 = employeeDao.getById(requestSurveyRating.getEmpId());
				SurveyEmployee surveyEmployee = new SurveyEmployee();
				surveyEmployee.setSurvey(survey1);
				surveyEmployee.setEmployee(employee1);
				surveyEmployee.setStatus("CLOSE");
				surveyEmployee.setCreated(new Date());
				surveyEmployee.setModified(new Date());
				surveyEmployee.setDeleted(new Date());
				surveyDao.insertSurveyEmployee(surveyEmployee);

			
			
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
	public boolean insertsurveyBoard(RequestSurveyBoard requestSurveyBoard) {
		logger.info(" into surveyBoard()");
		String msg=null;
		String subject=null;
		try {
System.out.println("insert surveybd /////////");
			SurveyVO surveyBoardVO = requestSurveyBoard.getSurvey();

			// checking survey existing or not
			boolean flag = surveyDao.checkSurvey(surveyBoardVO.getName());
		
			if (!flag) {
			//if survey nt exist 
				
				Division div = divisionDao.getRowById(requestSurveyBoard.getBusinessDivision());
				Department departmnt = departmentDao.getRowById(requestSurveyBoard.getDepartment());
				Group group = groupDao.getRowById(requestSurveyBoard.getGroup());
				// checking survey existing for same dep div and grp and state
				// open or not

				boolean flag1 = surveyDao.checkSurveyIdByDepDivGrp(div.getId(), departmnt.getId(), group.getId());
List<EmployeeVO>employeeVOs1 = employeeDao.getEmpByDivDepIdGrpId(div.getId(), departmnt.getId(), group.getId());


				if (!flag1) {
						// no pre grp exist
					Survey surveyBoard = new Survey();
					surveyBoard.setName(surveyBoardVO.getName());
					surveyBoard.setDescription(surveyBoardVO.getDescription());
					surveyBoard.setStartDate(surveyBoardVO.getStartDate());
					surveyBoard.setEndDate(surveyBoardVO.getEndDate());
					surveyBoard.setStatus(Constants.ACTIVE);
					surveyBoard.setCreated(new Date());
					surveyBoard.setModified(new Date());
					surveyBoard.setDeleted(new Date());
					surveyBoard.setState("OPEN");
					logger.debug("survey vo " + surveyBoard);

					// List<SurveyEmployee> surveyQuestionsVO =
					// requestSurveyBoard.get();
					// Set of survey board questions
					List<CommonSurveyQuestionVO> surveyQuestionsVO = requestSurveyBoard.getSurveyQuestions();
					Set<SurveySurveyqstn> surveyTargetquestions = new HashSet<SurveySurveyqstn>();
					for (CommonSurveyQuestionVO surveyQuestionVO : surveyQuestionsVO) {
						// target board questions
						SurveyQuestion surveytQuestion = new SurveyQuestion();
						surveytQuestion.setCreated(new Date());
						surveytQuestion.setModified(new Date());
						surveytQuestion.setDeleted(new Date());
						surveytQuestion.setName(surveyQuestionVO.getName());
						surveytQuestion.setRating(surveyQuestionVO.getRating());
						surveyQuestionDao.insertSurveyQuestion(surveytQuestion);
						logger.debug("survey qstn " + surveytQuestion);
						// relation of target board and target board questions
						SurveySurveyqstn surveyBoardSurveyQuestn = new SurveySurveyqstn();
						surveyBoardSurveyQuestn.setSurveyQuestion(surveytQuestion);
						surveyBoardSurveyQuestn.setSurvey(surveyBoard);
						surveyBoardSurveyQuestn.setCreated(new Date());
						surveyBoardSurveyQuestn.setModified(new Date());
						surveyBoardSurveyQuestn.setDeleted(new Date());

						surveyTargetquestions.add(surveyBoardSurveyQuestn);
						surveytQuestion.setSurveySurveyqstns(surveyTargetquestions);
						logger.info("surveyqstn ");
					}

					surveyBoard.setSurveySurveyqstns(surveyTargetquestions);
					surveyDao.insertSurvey(surveyBoard);
					Set<Surveydetails> surveyDetails = new HashSet<Surveydetails>();
					// survey,div, deptmnt, grup relation
					Surveydetails surveydetails2 = new Surveydetails();
					surveydetails2.setCreated(new Date());
					surveydetails2.setModified(new Date());
					surveydetails2.setDeleted(new Date());
			
					// get div info
					Division div1 = divisionDao.getRowById(requestSurveyBoard.getBusinessDivision());
					surveydetails2.setDivision(div1);
					div.setSurveydetailses(surveyDetails);

					// get dep info
					Department departmnt1 = departmentDao.getRowById(requestSurveyBoard.getDepartment());
					surveydetails2.setDepartment(departmnt1);
					departmnt.setSurveydetailses(surveyDetails);

					// get gp info
					Group group1 = groupDao.getRowById(requestSurveyBoard.getGroup());
					surveydetails2.setGroup(group1);
					group.setSurveydetailses(surveyDetails);

					// survey
					surveydetails2.setSurvey(surveyBoard);
					surveyBoard.setSurveydetailses(surveyDetails);
					surveyDetails.add(surveydetails2);
					// survey emp
					/*
					 * Set<SurveyEmployee> surveyEmployees = new
					 * HashSet<SurveyEmployee>(0); SurveyEmployee surveyEmployee
					 * = new SurveyEmployee(); surveyEmployee.setCreated(new
					 * Date()); surveyEmployee.setModified(new Date());
					 * surveyEmployee.setDeleted(new Date()); Employee emp =
					 * employeeDao.getById(requestSurveyBoard.getEmpId());
					 * surveyEmployee.setEmployee(emp);
					 * emp.setSurveyEmployees(surveyEmployees); Survey sur =
					 * surveyDao.getRowById(requestSurveyBoard.getSurvey().getId
					 * ()); surveyEmployee.setSurvey(sur);
					 * emp.setSurveyEmployees(surveyEmployees);
					 * 
					 * surveyBoard.setSurveyEmployees(surveyEmployees);
					 */
				 
					// after creation of survey send mail to all employees
					
					subject="Survey Creation";
					msg="Hi!! All /n Please go  through the survey. /nThanks and Regards. /n/nMycheck Team.";
					for (EmployeeVO employeeVO : employeeVOs1) {
					List<String> emails = emailGroupDao.getEmployeeEmailsByEmpId(employeeVO.getId());
					if (emails.size() > 0) {
						ExecutorService executor = Executors.newFixedThreadPool(1);
						Runnable worker = new MailGroup(emails, subject, msg);
						executor.execute(worker);
						executor.shutdown();
						flag = true;
					}
					
					}
					
					
					return true;
				}// end of grp 
				else {
					throw new Exception("group allready exist");
				}
			
			} //end of survey condition
			else {
				throw new Exception("survey allready exist");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public List<SurveyVO> getSurveyList() {
		logger.info("getSurveyList called");
		return surveyDao.getSurveyDetails();
	}

	@Override
	@Transactional
	public boolean updateSurveyBoard(RequestSurveyBoard requestSurveyBoard) {
		logger.info("updateSurveyBoard called ");
		try {
			SurveyVO surveyBoardVO = requestSurveyBoard.getSurvey();
			// List<TargetQuestionVO> targetQuestionVOs =
			// requestTargetBoard.getTargetQuestions();
			Survey surveyBoard = surveyDao.getRowById(surveyBoardVO.getId());
			surveyBoard.setName(surveyBoardVO.getName());
			surveyBoard.setDescription(surveyBoardVO.getDescription());
			/*
			 * String startdate = jsonObject2.get("startdate").toString();
			 * surveyVO.setStartDate(df.parse(startdate)); String enddate =
			 * jsonObject2.get("enddate").toString();
			 */
			surveyBoard.setStartDate(surveyBoardVO.getStartDate());
			surveyBoard.setEndDate(surveyBoardVO.getEndDate());
			surveyBoard.setModified(new Date());

			surveyDao.updateRow(surveyBoard);

			/*
			 * Surveydetails surveydetails= new Surveydetails();
			 * surveydetails.setDivision(surveyBoardVO.getDivision());
			 */

			logger.debug("survey borad " + surveyBoard);
			for (CommonSurveyQuestionVO surveyQstnVO : requestSurveyBoard.getSurveyQuestions()) {
				SurveyQuestion surveyQuestion = surveyQuestionDao.getRowById(surveyQstnVO.getId());
				//System.out.println("targetQuestionVO - " + surveyQstnVO.getStatus());
				surveyQuestion.setRating(surveyQstnVO.getRating());
				surveyQuestion.setModified(new Date());
				surveyQuestion.setName(surveyQstnVO.getName());
				surveyQuestionDao.updateRow(surveyQuestion);
				logger.debug("survey qstn" + surveyQuestion);
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
	public SurveyVO getSurveyDetailsById(int id) {
		// TODO Auto-generated method stub
		return surveyDao.getSurveyDetailsById(id);
	}

	@Override
	@Transactional
	public boolean updateSurveyStatusBySurveyId(int id) {
		// TODO Auto-generated method stub
		return surveyDao.updateSurveyStatusBySurveyId(id);
	}

	@Override
	@Transactional
	public boolean checkSurveyNameExist(String name) {
		// TODO Auto-generated method stub
		return surveyDao.checkSurvey(name);
	}
	
	@Override
	@Transactional
	public List<SurveyVO> getSurveyByLineMngrIdAndDate(int lineManagerId, String toDate, String fromDate) {
		try {
			EmployeeVO employeeVObj = employeeDao.getEmployeeDetailsById(lineManagerId);
			if (employeeVObj != null) {
//				// System.out.println(employeeVOs.getFname());
//				List<EmployeeVO> employeeVOs = employeeDao.getEmpByEmpId(employeeVObj.getId(),
//						employeeVObj.getDepartmentId(), employeeVObj.getDivisionId(), employeeVObj.getGroupId(), true);
//				StringBuffer empIds = new StringBuffer();
//
//				int count = 1;
//				String sep = ",";
//				for (EmployeeVO employeeVO : employeeVOs) {
//					if (count > 1) {
//						empIds.append(sep).append(employeeVO.getId());
//					} else {
//						empIds.append(employeeVO.getId());
//					}
//					count++;
//				}
//				if (empIds.length() >= 1) {
//					System.out.println("comma separated data ::" + empIds);
//
//					return (List<SurveyVO>) surveyDao.getSurveyBdByLnmngrIdAndDate(empIds.toString(),  toDate,  fromDate);
//				}
				
				return surveyDao.getSurveyListByDepDivGrp(toDate, fromDate, employeeVObj.getDepartmentId(), employeeVObj.getDivisionId(), employeeVObj.getGroupId());
			} else {
				System.out.println("else called");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public boolean closingSurveyBySurveyId(int sid) {
		try {
			SurveyPercentageVO vb=surveyQuestionService.getSurveryQuestionsPercentBySurveyId(sid);
			SurveySubmission  submission= new  SurveySubmission();
			submission.setSurvey(surveyDao.getRowById(sid));
			submission.setTotalEmployee(vb.getTotalEmployees());
			submission.setParticipatedEmployee(vb.getParticipateEmployees());
			int totalEmp=vb.getTotalEmployees();
			int partcEmp=vb.getParticipateEmployees();
			int uncp=totalEmp-partcEmp;
			submission.setUnparticipatedEmp(uncp);
			submission.setParticipatedPerc(vb.getOverallPercentage());
			submission.setStatus("CLOSE");
			submission.setCreated(new Date() );
			submission.setModified(new Date() );
			submission.setDeleted(new Date() );
		boolean flag=	surveyDao.insertSurveySubmission(submission);
			if(flag){
				//Survey survey= new Survey();
				
				Survey survey = surveyDao.getRowById(sid);
				survey.setState("CLOSE");
			///	survey.setStatus("INACTIVE");
				survey.setModified(new Date());
				surveyDao.updateRow(survey);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
