package com.techsource.mycheck.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.SurveyVO;

@Repository("surveyDao")
public class SurveyDaoImpl implements SurveyDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.techsource.mycheck.dao.SurveyDao#checkSurvey(java.lang.String)
	 */

	private final Logger logger = LoggerFactory.getLogger(SurveyDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public SurveyVO getSurveyIdByDepDivGrp(int depId, int divId, int grpId) {
		logger.info("getSurveyIdByDepDivGrp called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select s.id as id, s.name as name from ");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(Division.class.getName() + " AS div ,");
			builder.append(Department.class.getName() + " AS dep ,");
			builder.append(Group.class.getName() + " AS gp ,");
			builder.append(Surveydetails.class.getName() + " AS sd ");

		
			builder.append("where sd.survey.id=s.id and  ");
			builder.append("sd.division.id=div.id and ");
			builder.append("sd.department.id=dep.id and ");
			builder.append("sd.group.id=gp.id and sd.division.id=:divId and ");
			builder.append("sd.department.id=:depId and ");
			//builder.append("sd.group.id=:grpId  and s.state='OPEN' ");
			builder.append("sd.group.id=:grpId  and s.state='OPEN' ");

			// System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("divId", divId);
			query2.setParameter("depId", depId);
			query2.setParameter("grpId", grpId);

			@SuppressWarnings("unchecked")
			SurveyVO caList = (SurveyVO) query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class))
					.uniqueResult();

			System.out.println("total value is " + caList.getId());
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public int insertSurvey(Survey surveyBoard) {
		System.out.println("insertTargetBoard called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(surveyBoard);
			Serializable id = session.getIdentifier(surveyBoard);
			status_code = (Integer) id;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
			// logger.error("Expection occured " + e.getMessage());
			// status_code = -1;
		}
		return status_code;
	}

	@Override
	public boolean insertSurveyRating(SurveyResult surveyResult) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(surveyResult);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public List<SurveyVO> getSurveyDetails() {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append(
					"select s.id as id,s.status as status, s.state as state,  s.name as name,s.description as description,s.startDate as startDate,s.endDate as endDate,");
			builder.append(
					" sd.group.id as groupId ,sd.group.name as group ,sd.department.id as departmentId,sd.department.name as department, sd.division.id as divisionId,sd.division.name as division  from ");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(Division.class.getName() + " AS div ,");
			builder.append(Department.class.getName() + " AS dep ,");
			builder.append(Group.class.getName() + " AS gp ,");
			builder.append(Surveydetails.class.getName() + " AS sd ");

			builder.append("where sd.survey.id=s.id and  ");
			builder.append("div.id=sd.division.id and ");
			builder.append("dep.id=sd.department.id and ");
			builder.append("gp.id=sd.group.id  and s.status=:status");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			List<SurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class)).list();
			logger.debug("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public Survey getRowById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Survey surveyBd = (Survey) session.get(Survey.class, id);
			return surveyBd;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateRow(Survey survey) {
		logger.info("updateRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(survey);
			Serializable id = session.getIdentifier(survey);
			// session.close();
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			System.out.println("in the update row catch");
			throw new MyException(e.getMessage(), e);
			// logger.error("Expection occured " + e.getMessage());
			// status_code = -1;
		}
		// return status_code;
		// return 0;

	}

	@Override
	public SurveyVO getSurveyDetailsById(int id) {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append(
					"select s.id as id,s.status as status, s.state as state,  s.name as name,s.description as description,s.startDate as startDate,s.endDate as endDate,");
			builder.append(
					" sd.group.id as groupId ,sd.group.name as group ,sd.department.id as departmentId,sd.department.name as department, sd.division.id as divisionId,sd.division.name as division  from ");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(Division.class.getName() + " AS div ,");
			builder.append(Department.class.getName() + " AS dep ,");
			builder.append(Group.class.getName() + " AS gp ,");
			builder.append(Surveydetails.class.getName() + " AS sd ");

			builder.append("where sd.survey.id=s.id and  ");
			builder.append("div.id=sd.division.id and ");
			builder.append("dep.id=sd.department.id and ");
			builder.append("gp.id=sd.group.id and s.id=:id ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			SurveyVO caList = (SurveyVO) query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class))
					.uniqueResult();
			logger.debug("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public boolean updateSurveyStatusBySurveyId(int sqid) {
		boolean flag = false;
		try {
			// String hql = "UPDATE "+TargetQuestions.class.getName()+" set
			// status = :status " + "WHERE id = :qtnId";

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(Survey.class.getName());
			builder.append(" set status = :status , state = :state, modified=:modified WHERE id = :sqid");

			Query query2 = session.createQuery(builder.toString());

			query2.setParameter("status", "INACTIVE");
			query2.setParameter("state", "CLOSE");
			query2.setParameter("modified", new Date());
			query2.setParameter("sqid", sqid);

			int result = query2.executeUpdate();
			if (result > 0) {
				flag = true;
			}
			// System.out.println("Rows affected: " + result);

		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public boolean checkSurvey(String surveyname) {
		logger.info("checkSurvey called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + Survey.class.getName() + " where name = :name");
			query.setParameter("name", surveyname);
			@SuppressWarnings("unchecked")
			List<Survey> survyList = query.list();
			if (survyList.size() > 0) {
				logger.info("survey  exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkSurveyIdByDepDivGrp(int divId, int depId, int grpId) {
		logger.info("checkSurvey called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + Surveydetails.class.getName() + "  as sd , "
					+ Survey.class.getName()
					+ " as s  where sd.survey.id=s.id and divisonId =:divisonId and groupId=:groupId and departmentId=:departmentId and  s.state='OPEN'");
			query.setParameter("divisonId", divId);
			query.setParameter("groupId", grpId);
			query.setParameter("departmentId", depId);

			@SuppressWarnings("unchecked")
			List<Survey> survyList = query.list();
			if (survyList.size() > 0) {
				logger.info("survey  exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertSurveyEmployee(SurveyEmployee surveyEmployee) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(surveyEmployee);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public boolean checkEmployeeQuestion(int id) {
		logger.info("checkSurvey called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  se.id as id, se.survey.id as surveyId, se.employee.id as empId  from ");
			builder.append(SurveyQuestion.class.getName() + " AS sq ,");
			builder.append(SurveySurveyqstn.class.getName() + " as ss, ");
			builder.append(Employee.class.getName() + " as e , ");

			builder.append(SurveyEmployee.class.getName() + " as se , ");
			builder.append(Survey.class.getName() + " as s ");

			builder.append("where  se.employee.id=:id  and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<SurveyEmployee> survyList = query2.list();
			if (survyList.size() > 0) {
				logger.info("survey employee  exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<SurveyVO> getSurveyBdByLnmngrIdAndDate(String empIds, String toDate, String fromDate) {
		logger.info("getTargetBdByLnmngrId called");
		Date utilDate1 = null;
		Date utilDate2 = null;
		try {

			utilDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(toDate);
			utilDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(fromDate);
			Session session = sessionFactory.getCurrentSession();

			StringBuffer builder = new StringBuffer();

			builder.append("select  s.id as id,  s.name as name   from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(SurveyEmployee.class.getName() + " as se ");

			builder.append(" where se.survey.id=s.id and ");
			builder.append(" se.employee.id=e.id and ");
			builder.append("se.employee.id in (" + empIds + ")  and s.created BETWEEN :startDate AND :endDate ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("startDate", utilDate1);
			query2.setParameter("endDate", utilDate2);

			@SuppressWarnings("unchecked")
			List<SurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class)).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public SurveyVO getGrpDepDevsnBySurveyId(int sid) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();

			builder.append("select sd.id as id ,  sd.department.id as departmentId , ");

			builder.append("sd.division.id as divisionId, sd.group.id as groupId from ");

			builder.append(Surveydetails.class.getName() + " AS sd ,");
			builder.append(Survey.class.getName() + " AS s ,");

			builder.append(Group.class.getName() + " As grp ,");
			builder.append(Department.class.getName() + " as dep , ");
			builder.append(Division.class.getName() + " as div ");

			builder.append("where  sd.survey.id=s.id and ");
			builder.append(" sd.group.id=grp.id and ");
			builder.append("sd.department.id=dep.id and ");
			builder.append("sd.division.id=div.id and ");
			// builder.append("sd.employee.id=e.id and ");
			builder.append("sd.survey.id=:id");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", sid);

			SurveyVO surveyVos = (SurveyVO) query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class))
					.uniqueResult();
			return surveyVos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<EmployeeVO> getSurveyEmpBySurveyId(int id) {
		logger.info("getSurveyEmpBySurveyId called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select se.survey.id as id, se.employee.id as id from ");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(SurveyEmployee.class.getName() + " AS se ");

			builder.append("where s.id=se.survey.id and  ");
			builder.append("se.employee.id=e.id and se.survey.id=:id");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<EmployeeVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();
			logger.debug("id is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<SurveyVO> getSurveyListByDepDivGrp(String toDate, String fromDate,int depId, int divId, int grpId) {
		logger.info("getSurveyIdByDepDivGrp called ");
		
		Date utilDate1 = null;
		Date utilDate2 = null;
		try {

			utilDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(toDate);
			utilDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(fromDate);
			
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select s.id as id, s.name as name from ");
			builder.append(Survey.class.getName() + " AS s ,");
			builder.append(Division.class.getName() + " AS div ,");
			builder.append(Department.class.getName() + " AS dep ,");
			builder.append(Group.class.getName() + " AS gp ,");
			builder.append(Surveydetails.class.getName() + " AS sd ");

			builder.append("where sd.survey.id=s.id and  ");
			builder.append("sd.division.id=div.id and ");
			builder.append("sd.department.id=dep.id and ");
			builder.append("sd.group.id=gp.id and sd.division.id=:divId and ");
			builder.append("sd.department.id=:depId and ");
			builder.append("sd.group.id=:grpId  and sd.created between :startDate and :endDate and s.status=:status ");

			// System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("divId", divId);
			query2.setParameter("depId", depId);
			query2.setParameter("grpId", grpId);
			query2.setDate("startDate", utilDate1);
			query2.setDate("endDate", utilDate2);
			query2.setParameter("status", Constants.ACTIVE);
			

			@SuppressWarnings("unchecked")
			List<SurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(SurveyVO.class)).list();

			System.out.println("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
@Override
public boolean insertSurveySubmission(SurveySubmission submission) {
	try {
		Session session = sessionFactory.getCurrentSession();
		session.save(submission);
		logger.info("saved ");
		return true;
	} catch (HibernateException e) {
		e.printStackTrace();

		return false;

	}
}
}
