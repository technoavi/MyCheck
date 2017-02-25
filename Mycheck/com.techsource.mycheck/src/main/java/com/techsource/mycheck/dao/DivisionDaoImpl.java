package com.techsource.mycheck.dao;

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

import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.DivisionVO;

@Repository("divisionDao")
public class DivisionDaoImpl implements DivisionDao {
	private final Logger logger = LoggerFactory.getLogger(DivisionDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insertDivision(Division division) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DivisionVO> getList() {
		logger.info("getList called");
		try {

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select d.id as id,d.name as name from ");
			builder.append(Division.class.getName());
			builder.append(" d where status=:status");
			Query query = session.createQuery(builder.toString());
			query.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			List<DivisionVO> caList = query.setResultTransformer(Transformers.aliasToBean(DivisionVO.class)).list();
			return caList;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public Division getRowById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Division department = (Division) session.get(Division.class, id);
		return department;
	}

	@Override
	public int updateRow(Division division) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public Division getRowByDivision(String division) {
		logger.info("getRowByName called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + Division.class.getName() + " where name=:division");
			query.setParameter("division", division);
			Division department2 = (Division) query.uniqueResult();
			return department2;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
