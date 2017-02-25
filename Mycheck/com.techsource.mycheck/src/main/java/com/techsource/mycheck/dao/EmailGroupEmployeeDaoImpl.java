package com.techsource.mycheck.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techsource.mycheck.domain.EmailGroup;
import com.techsource.mycheck.domain.EmailgpEmployee;
import com.techsource.mycheck.domain.EmpLogin;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;

@Repository("emailGroupEmployeeDao")
public class EmailGroupEmployeeDaoImpl implements EmailGroupEmployeeDao {
	private final Logger logger = LoggerFactory.getLogger(EmailGroupEmployeeDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean updateEmailGroupEmployee(EmailgpEmployee emailgpEmployee) {
		logger.info("updateRow called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(emailgpEmployee);
			Serializable id = session.getIdentifier(emailgpEmployee);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			System.out.println("in the update row catch");
			throw new MyException(e.getMessage(), e);
		}

	}

	@Override
	public List<Object[]> getEmpByEmailGroupId(int empGroupId) {
		logger.info("getEmployeesListById called");

		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select ege.employee.id as id,ege.state as state  from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmailgpEmployee.class.getName() + " as ege ");
			builder.append("where  ege.employee.id=e.id and ege.emailGroup.id=:id ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", empGroupId);
			// query2.setParameter("status", Constants.ACTIVE);and
			// ege.employee.status=:status

			@SuppressWarnings("unchecked")
			List<Object[]> caList = query2.list();
			return caList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public EmailgpEmployee getRowById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			EmailgpEmployee surveyBd = (EmailgpEmployee) session.get(EmailgpEmployee.class, id);
			return surveyBd;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int save(EmailgpEmployee object) {
		System.out.println("insert called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(object);
			Serializable id = session.getIdentifier(object);
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
	public EmailgpEmployee getRowByEmailGrpIdEmpId(int emailGrpId, int empId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("from ");

			builder.append(EmailgpEmployee.class.getName() + " as ege ");
			builder.append("where  ege.employee.id=:empId and ege.emailGroup.id=:emailGrpId ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("empId", empId);
			query2.setParameter("emailGrpId", emailGrpId);
			// query2.setParameter("status", Constants.ACTIVE);and
			// ege.employee.status=:status

			@SuppressWarnings("unchecked")
			EmailgpEmployee caList = (EmailgpEmployee) query2.uniqueResult();
			return caList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}
	@Override
	public boolean deleteEmailGroupByEmailGrpId(int id) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"update " + EmailGroup.class.getName() + " set status = :status where id = :id ");
			query.setString("status", Constants.INACTIVE);
			query.setString("id", String.valueOf(id));
			int rowCount = query.executeUpdate();
			if (rowCount > 0) {
				flag = true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

}
