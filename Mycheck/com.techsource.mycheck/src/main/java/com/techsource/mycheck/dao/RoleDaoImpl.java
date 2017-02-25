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

import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.RoleVO;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	private final Logger logger = LoggerFactory.getLogger(RoleDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insertRole(Role Role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RoleVO> getList() {
		logger.info("getList called");
		try {

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select r.id as id,r.name as name from ");
			builder.append(Role.class.getName());
			builder.append(" r where status=:status");
			Query query = session.createQuery(builder.toString());
			query.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			List<RoleVO> caList = query.setResultTransformer(Transformers.aliasToBean(RoleVO.class)).list();
			return caList;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public Role getRowById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Role role = (Role) session.get(Role.class, id);
		return role;
	}

	@Override
	public int updateRow(Role Role) {
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
	public Role getRowByRole(String role) {
		logger.info("getRowByName called ::"+role);
		//System.out.print("getRowByName called ::"+role);
		Role role2=null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String sql_string="from " + Role.class.getName() + " where name=:role";
			System.out.println(sql_string);
			Query query = session.createQuery(sql_string);
			query.setParameter("role", role);
			role2 = (Role) query.uniqueResult();
			//System.out.print("role2 called ::"+role2.getName());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return role2;
	}

}
