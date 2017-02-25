package com.techsource.mycheck.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyEmployee;
import com.techsource.mycheck.domain.CommonSurveyQuestionEmployeeRelation;
import com.techsource.mycheck.domain.CommonSurveySubmission;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.EmployeeVO;

@Repository("commonSurveyDao")
public class CommonSurveyDaoImpl implements CommonSurveyDao {

	private final Logger logger = LoggerFactory.getLogger(CommonSurveyDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int insertCommonSurveyBoard(CommonSurvey commonsurveyBoard) {

		System.out.println("insertTargetBoard called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(commonsurveyBoard);
			Serializable id = session.getIdentifier(commonsurveyBoard);
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
	public CommonSurvey getRowById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			CommonSurvey csurveyBd = (CommonSurvey) session.get(CommonSurvey.class, id);
			return csurveyBd;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateRow(CommonSurvey commonsurvey) {
		logger.info("updateRow called");
		
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(commonsurvey);
			Serializable id = session.getIdentifier(commonsurvey);
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
	public List<CommonSurveyVO> getCommonSurveyList() {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append(
					"select s.id as id,s.status as status, s.state as state,  s.name as name,s.description as description,s.startDate as startDate,s.endDate as endDate  ");
			builder.append("  from  ");
			builder.append(CommonSurvey.class.getName() + " AS s ");

			builder.append("where  s.status=:status ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			List<CommonSurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.list();
			logger.debug("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkCommonSurveyNameExist(String name) {
		logger.info("checkCommonSurveyNameExist called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + CommonSurvey.class.getName() + " where name = :name");
			query.setParameter("name", name);
			@SuppressWarnings("unchecked")
			List<Survey> survyList = query.list();
			if (survyList.size() > 0) {
				logger.info("Commonsurvey  exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CommonSurveyVO getCommonSurveyDetailsById(int id) {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append(
					"select s.id as id,s.status as status, s.state as state,  s.name as name,s.description as description,s.startDate as startDate,s.endDate as endDate ");
			builder.append(" from ");
			builder.append(CommonSurvey.class.getName() + " AS s  ");

			builder.append("where  s.id=:id ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			CommonSurveyVO caList = (CommonSurveyVO) query2
					.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class)).uniqueResult();
			logger.debug("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public boolean updateCommonSurveyStatusByCommonSurveyId(int id) {
		boolean flag = false;
		try {
			// String hql = "UPDATE "+TargetQuestions.class.getName()+" set
			// status = :status " + "WHERE id = :qtnId";

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(CommonSurvey.class.getName());
			builder.append(" set status = :status , state = :state, modified=:modified WHERE id = :csqid");

			Query query2 = session.createQuery(builder.toString());

			query2.setParameter("status", "INACTIVE");
			query2.setParameter("state", "CLOSE");
			query2.setParameter("modified", new Date());
			query2.setParameter("csqid", id);

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
	public List<CommonSurveyVO> getCommonSurveyByEmpId(int id) {
		logger.info("getSurveyIdByDepDivGrp called ");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select s.id as id, s.name as name from ");
			builder.append(CommonSurvey.class.getName() + " AS s ,");
			builder.append(CommonSurveyEmployee.class.getName() + " AS se ");

			builder.append("where se.commonSurvey.id=s.id   ");
			// builder.append(" and se.employee.id=:id ");
			// builder.append(" and s.status=:status ");

			// System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameter("id", id);
			// query2.setParameter("status", Constants.ACTIVE);

			@SuppressWarnings("unchecked")
			List<CommonSurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.list();

			System.out.println("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public boolean insertCommonSurveyRating(CommonSurveyQuestionEmployeeRelation commonRating) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(commonRating);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public boolean insertCommonSurveyEmployee(CommonSurveyEmployee commonSurveyEmployee) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(commonSurveyEmployee);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public boolean updateCommonSurveyEmployee(CommonSurveyEmployee commonSurveyEmployee) {
		logger.info("updateRow called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(commonSurveyEmployee);
			Serializable id = session.getIdentifier(commonSurveyEmployee);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			System.out.println("in the update row catch");
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public boolean checkCommonSurveyEmployeeParticipatedByEmpyId(int eid) {
		logger.info("checkCommonSurveyEmployeeParticipatedBySurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  se.id as id, se.commonSurvey.id as commonSurveyId, se.employee.id as empId  from ");
			builder.append(CommonSurvey.class.getName() + " AS s ,");
			builder.append(CommonSurveyEmployee.class.getName() + " as se , ");
			builder.append(Employee.class.getName() + " as e  ");

			builder.append("where  se.employee.id=:id  and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", eid);
			@SuppressWarnings("unchecked")
			List<CommonSurveyEmployee> survyList = query2.list();
			if (survyList.size() > 0) {
				logger.info("CommonSurveyEmployee  exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CommonSurveyVO> getCommonSurveyByDate(String sdate, String edate) {
		logger.info("getSurveyIdByDepDivGrp called ");
		Date utilDate1 = null;
		Date utilDate2 = null;
		try {
			utilDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(sdate);
			utilDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(edate);

			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select s.id as id, s.name as name from ");
			builder.append(CommonSurvey.class.getName() + " AS s ");

			builder.append("where  s.startDate between :sDate and :eDate and s.status=:status ");

			// System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());

			query2.setDate("sDate", utilDate1);
			query2.setDate("eDate", utilDate2);
			query2.setParameter("status", Constants.ACTIVE);

			@SuppressWarnings("unchecked")
			List<CommonSurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.list();

			System.out.println("total value is " + caList);
		return (List<CommonSurveyVO>) (null==caList ? "": caList);
		//	return  (caList==null ? "": caList);
			//return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<EmployeeVO> getCommonSurveyEmpBySurveyId(int id) {
		logger.info("getCommonSurveyEmpBySurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select distinct  se.employee.id as id  from ");
			builder.append(CommonSurvey.class.getName() + " AS s ,");
			builder.append(CommonSurveyEmployee.class.getName() + " as se , ");
			builder.append(Employee.class.getName() + " as e  ");

			builder.append(
					"where s.id=se.commonSurvey.id  and se.employee.id=e.id and se.commonSurvey.id=:id and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<EmployeeVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();

			System.out.println("total emp===value is " + caList);
			return  (List<EmployeeVO>) (caList==null ? "": caList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<CommonSurveyVO> getCommonSurveysByEmpId(int id) {
		logger.info("getCommonSurveysByEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select   se.employee.id as id  from ");
			builder.append(CommonSurvey.class.getName() + " AS s ,");
			builder.append(CommonSurveyEmployee.class.getName() + " as se , ");
			builder.append(Employee.class.getName() + " as e  ");

			builder.append(
					"where s.id=se.commonSurvey.id and se.commonSurvey.id=:id and se.employee.id=e.id and se.employee.id NOT IN (:id)  and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<CommonSurveyVO> caList = query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.list();

			System.out.println("total emp===value is " + caList);
			return  (List<CommonSurveyVO>) (caList==null ? "": caList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<CommonSurveyVO> getCommonSurveysByEmpyIdAlreadyparticipated(int eid) {
		logger.info("checkCommonSurveyEmployeeParticipatedBySurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  se.id as id, se.commonSurvey.id as id, se.employee.id as empId  from ");
			builder.append(CommonSurvey.class.getName() + " AS s ,");
			builder.append(CommonSurveyEmployee.class.getName() + " as se , ");
			builder.append(Employee.class.getName() + " as e  ");

			builder.append(
					"where    se.employee.id=e.id and se.commonSurvey.id=s.id and se.employee.id=:id  and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", eid);
			@SuppressWarnings("unchecked")
			List<CommonSurveyVO> survyList = query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.list();

			logger.info("CommonSurveyEmployee  exists");
			return  (List<CommonSurveyVO>) (survyList==null ? "": survyList);

		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public CommonSurveyVO getCommonSurveyByNotParticipated(int id) {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select distinct s.id as id,  s.name as name  ");
			builder.append("  from  ");
			builder.append(CommonSurvey.class.getName() + " AS s , ");
			builder.append(CommonSurveyEmployee.class.getName() + " AS se, ");
			builder.append(Employee.class.getName() + " as e  ");

			builder.append("where se.employee.id=e.id and se.commonSurvey.id=s.id and s.id!=:id and s.status=:status ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			CommonSurveyVO caList = (CommonSurveyVO)query2.setResultTransformer(Transformers.aliasToBean(CommonSurveyVO.class))
					.uniqueResult();
			logger.debug("total value is " + caList);
			return (CommonSurveyVO) (caList==null ? "": caList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	} 
	@Override
	public boolean insertCommonSurveySubmission(CommonSurveySubmission commonSurveySubmission) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(commonSurveySubmission);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}
}
