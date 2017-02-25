/**
 * 
 */
package com.techsource.mycheck.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techsource.mycheck.domain.LongTermGoal;
import com.techsource.mycheck.domain.LongTermGoalEmp;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.LongTermGoalVO;

/**
 * @author Avinash Srivastava
 *
 *         20-Oct-2016
 */
@Repository("longtermGoalDao")
public class LongtermGoalDaoImpl implements LongtermGoalDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertLongtermGoal(LongTermGoal longTermGoal) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(longTermGoal);

			Serializable id = session.getIdentifier(longTermGoal);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.techsource.mycheck.dao.LongtermGoalDao#updateLongTermGoal(com.
	 * techsource.mycheck.domain.LongTermGoal)
	 */
	@Override
	public boolean updateLongTermGoal(LongTermGoal longTermGoal) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(longTermGoal);

			Serializable id = session.getIdentifier(longTermGoal);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.techsource.mycheck.dao.LongtermGoalDao#getRowById(int)
	 */
	@Override
	public LongTermGoal getRowById(int id) {
		try {
			System.out.println("LongTermGoal id" + id);
			Session session = sessionFactory.getCurrentSession();
			LongTermGoal longTermGoal = (LongTermGoal) session.get(LongTermGoal.class, id);
			return longTermGoal;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.techsource.mycheck.dao.LongtermGoalDao#deleteLongTermGoalById(int)
	 */
	@Override
	public boolean deleteLongTermGoalById(int id) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			LongTermGoal longTermGoal = (LongTermGoal) session.load(LongTermGoal.class, id);
			if (longTermGoal != null) {

				session.delete(longTermGoal);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.techsource.mycheck.dao.LongtermGoalDao#getlongtermGoalByEmpId(int)
	 */
	@Override
	public List<LongTermGoalVO> getlongtermGoalByEmpId(int eId) {
		// logger.info("getTargetQstnByEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select  lg.id as id, lg.name as name , lg.description as description, lg.startDate as startDate, lg.endDate as endDate  from ");

			builder.append(LongTermGoalEmp.class.getName() + " AS lge ,");
			builder.append(LongTermGoal.class.getName() + " as lg ");
			builder.append("where  lge.longTermGoal.id=lg.id  and lge.employee.id=:eid  order by lg.created ");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", eId);
			@SuppressWarnings("unchecked")
			List<LongTermGoalVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(LongTermGoalVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.techsource.mycheck.dao.LongtermGoalDao#getLongTermGoalBygoalId(int)
	 */
	@Override
	public LongTermGoalVO getLongTermGoalBygoalId(int gId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select  lg.id as id, lg.name as name , lg.description as description, lg.startDate as startDate, lg.endDate as endDate  from ");

			builder.append(LongTermGoalEmp.class.getName() + " AS lge ,");
			builder.append(LongTermGoal.class.getName() + " as lg ");
			builder.append("where  lge.longTermGoal.id=lg.id  and lge.longTermGoal.id=:gId  ");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("gId", gId);
			@SuppressWarnings("unchecked")
			LongTermGoalVO targetVOList = (LongTermGoalVO) query2.setResultTransformer(Transformers.aliasToBean(LongTermGoalVO.class))
					.uniqueResult();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
}
