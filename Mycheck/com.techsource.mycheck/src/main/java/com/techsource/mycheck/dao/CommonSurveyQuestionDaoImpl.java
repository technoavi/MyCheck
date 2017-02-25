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

import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyQuestionEmployeeRelation;
import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.domain.CommonSurveyQuestionsRelation;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.SurveyQuestion;
import com.techsource.mycheck.service.CommonSurveyQuestionEmployeeRelationVO;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
@Repository("commonSurveyQuestionDao")
public class CommonSurveyQuestionDaoImpl implements CommonSurveyQuestionDao {
	private final Logger logger = LoggerFactory.getLogger(CommonSurveyQuestionDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertCommonSurveyQuestion(CommonSurveyQuestions csurveytQuestion) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(csurveytQuestion);
			logger.info("saved ");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();

			return false;

		}
	}
	@Override
	public CommonSurveyQuestions getRowById(int id) {
		try {
			System.out.println("CommonSurveyQuestions id" + id);
			Session session = sessionFactory.getCurrentSession();
			CommonSurveyQuestions surveyQstn = (CommonSurveyQuestions) session.get(CommonSurveyQuestions.class, id);
			return surveyQstn;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean updateRow(CommonSurveyQuestions commonsurveyQuestion) {
		logger.info("updateRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(commonsurveyQuestion);
			Serializable id = session.getIdentifier(commonsurveyQuestion);
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
	public List<CommonSurveyQuestionVO> getCommonSurveryQuestionsBySurveyId(int id) {
		logger.info("getCommonSurveryQuestionsBySurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  sq.id as id, sq.name as name, sq.rating as rating  from ");
			builder.append(CommonSurveyQuestions.class.getName() + " AS sq ,");
			builder.append(CommonSurveyQuestionsRelation.class.getName() + " as ss, ");
			builder.append(CommonSurvey.class.getName() + " as s ");

			builder.append("where  s.id=ss.commonSurvey.id and sq.id=ss.commonSurveyQuestions.id  and ss.commonSurvey.id=:sid  ");

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
	public boolean deleteCommonSurveyQuestionByQstnId(int id) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			CommonSurveyQuestions commonsurveyqstn = (CommonSurveyQuestions) session.load(CommonSurveyQuestions.class, id);
			if (commonsurveyqstn != null) {
				// targetqstn.setStatus(Byte.valueOf("0"));
				session.delete(commonsurveyqstn);
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
	public boolean addCommonSurveyQuestion(CommonSurveyQuestions question) {
	
			logger.info("addCommonSurveyQuestion called");
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
	public boolean updateCommonSurveyQuestionByCommonSurveyId(int id, String name) {
		boolean flag = false;
		try {
			// String hql = "UPDATE "+TargetQuestions.class.getName()+" set
			// status = :status " + "WHERE id = :qtnId";

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(CommonSurveyQuestions.class.getName());
			builder.append(" set name = :name , modified=:modified WHERE id = :sqid");

			Query query2 = session.createQuery(builder.toString());

			query2.setParameter("name", name);
			query2.setParameter("modified", new Date());
			query2.setParameter("sqid", id);

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
	public List<CommonSurveyQuestionEmployeeRelationVO> getRatingsCommonSurveyQuestionId(int id) {
		logger.info("getSurveryRatingsSurveyId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			                                                                                                                                                                                                                                                      
			builder.append(
					"select sr.commonSurvey.id as surveyId,sr.commonSurveyQuestions.id as id,sr.commonSurveyQuestions.name as name , sr.employee.id as empId, sr.rating as rating  from ");
			builder.append(CommonSurveyQuestionEmployeeRelation.class.getName() + " AS sr ,");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(CommonSurveyQuestionsRelation.class.getName() + " as q , ");
			builder.append(CommonSurvey.class.getName() + " as s ");
			builder.append(
					"where  sr.commonSurvey.id=s.id and sr.commonSurveyQuestions.id=q.id and sr.employee.id=e.id and sr.commonSurveyQuestions.id=:sqid  ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("sqid", id);
			@SuppressWarnings("unchecked")
			List<CommonSurveyQuestionEmployeeRelationVO> targetQuestionVOList = query2
					.setResultTransformer(Transformers.aliasToBean(CommonSurveyQuestionEmployeeRelationVO.class)).list();
		
			return targetQuestionVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
