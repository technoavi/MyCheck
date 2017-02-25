package com.techsource.mycheck.dao;

import java.io.Serializable;
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

import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.SurveyEmployee;
import com.techsource.mycheck.domain.SurveyQuestion;
import com.techsource.mycheck.domain.SurveyResult;
import com.techsource.mycheck.domain.SurveySurveyqstn;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.SurveyResultVO;

@Repository("surveyQuestionDao")
public class SurveyQuestionDaoImpl implements SurveyQuestionDao {
	private final Logger logger = LoggerFactory.getLogger(SurveyQuestionDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertSurveyQuestion(SurveyQuestion surveytQuestion) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(surveytQuestion);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public SurveyQuestion getRowById(int id) {
		try {
			System.out.println("TargetBoard id" + id);
			Session session = sessionFactory.getCurrentSession();
			SurveyQuestion surveyQstn = (SurveyQuestion) session.get(SurveyQuestion.class, id);
			return surveyQstn;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateRow(SurveyQuestion surveyQuestion) {
		logger.info("updateRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(surveyQuestion);
			Serializable id = session.getIdentifier(surveyQuestion);
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
	public boolean deleteQuestionById(int qstnId) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			SurveyQuestion surveyqstn = (SurveyQuestion) session.load(SurveyQuestion.class, qstnId);
			if (surveyqstn != null) {
				// targetqstn.setStatus(Byte.valueOf("0"));
				session.delete(surveyqstn);
				flag = true;
			} else {

				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);

		}
		return flag;
	}

	@Override
	public boolean addSurveyQuestion(SurveyQuestion question) {
		logger.info("insertTargetQuestions called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(question);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			return false;

		}
	}

	@Override
	public boolean updateQuestionBySurveyId(int sqid, String name) {
		boolean flag = false;
		try {
			// String hql = "UPDATE "+TargetQuestions.class.getName()+" set
			// status = :status " + "WHERE id = :qtnId";

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(SurveyQuestion.class.getName());
			builder.append(" set name = :name , modified=:modified WHERE id = :sqid");

			Query query2 = session.createQuery(builder.toString());

			query2.setParameter("name", name);
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
	public List<CommonSurveyQuestionVO> getSurveryQuestionsBySurveyId(int id) {
		logger.info("getTargetQstnbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  sq.id as id, sq.name as name, sq.rating as rating  from ");
			builder.append(SurveyQuestion.class.getName() + " AS sq ,");
			builder.append(SurveySurveyqstn.class.getName() + " as ss, ");
			builder.append(Survey.class.getName() + " as s ");

			builder.append("where  s.id=ss.survey.id and sq.id=ss.surveyQuestion.id  and ss.survey.id=:sid  ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("sid", id);
			@SuppressWarnings("unchecked")
			List<CommonSurveyQuestionVO> targetQuestionVOList = query2
					.setResultTransformer(Transformers.aliasToBean(CommonSurveyQuestionVO.class)).list();
			return targetQuestionVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CommonSurveyQuestionVO> getSurveryQuestionsByEmpId(int id) {
		logger.info("getSurveryQuestionsByEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  sq.id as id, sq.name as name, sq.rating as rating  from ");
			builder.append(SurveyQuestion.class.getName() + " AS sq ,");
			builder.append(SurveySurveyqstn.class.getName() + " as ss, ");
			builder.append(SurveyEmployee.class.getName() + " as se , ");
			builder.append(Survey.class.getName() + " as s ");

			builder.append(
					"where  s.id=ss.survey.id and sq.id=ss.surveyQuestion.id  and ss.survey.id=:sid and se.surveyId=se.survey.id and se.status='CLOSE' ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("sid", id);
			@SuppressWarnings("unchecked")
			List<CommonSurveyQuestionVO> targetQuestionVOList = query2
					.setResultTransformer(Transformers.aliasToBean(CommonSurveyQuestionVO.class)).list();
			return targetQuestionVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SurveyResultVO> getRatingsSurveyQuestionId(int sqid) {
		logger.info("getSurveryRatingsSurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			// builder.append("select sr.survey.id as surveyId ,
			// sr.surveyQuestion.id as surveyQuestionId, sr.employee.id as
			// empId, sr.rating as rating from ");
			// builder.append(SurveyResult.class.getName() + " AS sr ,");
			// builder.append(Employee.class.getName() + " AS e ,");
			// builder.append(SurveyQuestion.class.getName() + " as q , ");
			// builder.append(Survey.class.getName() + " as s ");
			//
			// builder.append("where sr.survey.id=s.id and
			// sr.surveyQuestion.id=q.id and sr.employee.id=e.id and
			// sr.survey.id=:sid ");
                                                                                                                                                                                                                                                                                                
			builder.append(
					"select sr.survey.id as surveyId,sr.surveyQuestion.id as id,sr.surveyQuestion.name as name , sr.employee.id as empId, sr.rating as rating  from ");
			builder.append(SurveyResult.class.getName() + " AS sr ,");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(SurveyQuestion.class.getName() + " as q , ");
			builder.append(Survey.class.getName() + " as s ");
			builder.append(
					"where  sr.survey.id=s.id and sr.surveyQuestion.id=q.id and sr.employee.id=e.id and sr.surveyQuestion.id=:sqid  ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("sqid", sqid);
			@SuppressWarnings("unchecked")
			List<SurveyResultVO> targetQuestionVOList = query2
					.setResultTransformer(Transformers.aliasToBean(SurveyResultVO.class)).list();
			/*
			 * for (SurveyResultVO surveyResultVO : targetQuestionVOList) {
			 * System.out.println("sid:"+surveyResultVO.getSurveyId());
			 * System.out.println("empId:"+surveyResultVO.getEmpId());
			 * System.out.println("qstnId:"+surveyResultVO.getSurveyQuestionId()
			 * ); System.out.println("rating:"+surveyResultVO.getRating()); }
			 */
			return targetQuestionVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
