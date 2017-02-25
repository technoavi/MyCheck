package com.techsource.mycheck.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techsource.mycheck.domain.EmailGroup;
import com.techsource.mycheck.domain.EmailRelation;
import com.techsource.mycheck.domain.EmailgpEmployee;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.EmailGroup;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.EmailGroupVO;
import com.techsource.mycheck.vo.EmployeeInfo;
import com.techsource.mycheck.vo.EmployeeVO;

@Repository("emailGroupDao")
public class EmailGroupDaoImpl implements EmailGroupDao {

	private final Logger logger = LoggerFactory.getLogger(TargetQuestionDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertEmailGroup(EmailGroup emailGroup) {
		logger.info("insertTargetQuestions called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(emailGroup);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			return false;

		}

	}

	@Override
	public List<EmailGroupVO> getEmailGroupList() {
		logger.info("getSurveyDetails called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select eg.id as id, eg.name as name from ");
			builder.append(EmailGroup.class.getName() + " AS eg  where eg.status=:status");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			//Map<Integer, String> data = new LinkedHashMap<Integer, String>();

			List<EmailGroupVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmailGroupVO.class))
					.list();
			logger.debug("total value is " + caList);
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public EmailGroupVO getEmailGroupDetailsById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getDivAndDeptById(int emailGroupId) {
		logger.info("getDivAndDeptById called ");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select egr.division.id , egr.department.id from ");
			builder.append(EmailRelation.class.getName() + " AS egr ");
			builder.append("where egr.emailGroup.id=:emailGroupId ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("emailGroupId", emailGroupId);
			@SuppressWarnings("unchecked")

			Object[] ids = (Object[]) query2.uniqueResult();
			// for (Object[] db_object : ids) {
			//
			// int id=Integer.parseInt(db_object[0].toString());
			// String name=db_object[1].toString();
			// data.put(id,name);
			// }

			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<EmployeeInfo> getEmployeesListById(int id) {
		logger.info("getEmployeesListById called");

		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select ege.employee.id as id,ege.employee.fname as name,ege.state as state from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmailgpEmployee.class.getName() + " as ege ");
			builder.append("where  ege.employee.id=e.id and ege.emailGroup.id=:id and ege.employee.status=:status");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("status", Constants.ACTIVE);

			@SuppressWarnings("unchecked")
			List<EmployeeInfo> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeInfo.class))
					.list();
			return caList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
		// return employeeVOs;
	}

	@Override
	public List<String> getEmployeeEmailsByEmpGrpId(int id) {
		logger.info("getEmpByEmpGrpId called");
		List<String> maillist = new ArrayList<String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select   e.email as email from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmailgpEmployee.class.getName() + " as ege ");
			builder.append("where  ege.employee.id=e.id and ege.emailGroup.id=:id ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<EmployeeVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();
			StringBuffer buffer = new StringBuffer();

			for (EmployeeVO employeeVO : caList) {

				maillist.add(employeeVO.getEmail());
			}

			return maillist;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}
	@Override
	public boolean updateEmailGroup(EmailGroup emailGroup) {
		logger.info("insertTargetQuestions called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(emailGroup);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			return false;

		}

	}
	@Override
	public EmailGroup getRowById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			EmailGroup emailgroup = (EmailGroup) session.get(EmailGroup.class, id);
			return emailgroup;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean checkEmailGroupExit(String name) {
		logger.info("checkEmailGroupExit called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + EmailGroup.class.getName() + " where name = :name");
			query.setParameter("name", name);
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
	public List<String> getEmployeeEmailsByEmpId(int id) {
		logger.info("getEmployeeEmailsByEmpId called");
		List<String> maillist = new ArrayList<String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select   e.email as email from ");
			builder.append(Employee.class.getName() + " AS e ");
//			builder.append(EmailgpEmployee.class.getName() + " as ege ");
			builder.append("where  e.id=:id and e.status=:status ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("status", Constants.ACTIVE);

			@SuppressWarnings("unchecked")
			List<EmployeeVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();
			StringBuffer buffer = new StringBuffer();

			for (EmployeeVO employeeVO : caList) {

				maillist.add(employeeVO.getEmail());
			}

			return maillist;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}
}
