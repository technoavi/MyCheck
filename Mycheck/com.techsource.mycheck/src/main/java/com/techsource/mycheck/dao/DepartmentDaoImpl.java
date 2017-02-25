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

import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.domain.DepartmentDivision;
import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.DepartmentVO;

@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {
	private final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int insertDepartment(Department Department) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Department> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department getRowById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Department department = (Department) session.get(Department.class, id);
		return department;
	}

	@Override
	public int updateRow(Department Department) {
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
	public Department getRowByDepartment(String name) {
		logger.info("getRowByName called ::"+name);
		//System.out.print("getRowByName called ::"+role);
		Department department=null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String sql_string="from " + Department.class.getName() + " where name=:name";
			System.out.println(sql_string);
			Query query = session.createQuery(sql_string);
			query.setParameter("name", name);
			department = (Department) query.uniqueResult();
			//System.out.print("role2 called ::"+role2.getName());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public List<DepartmentVO> getListByDivision(String id) {
		logger.info("getListByDivision called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select d.id as id,d.name as name from ");
			builder.append(Department.class.getName() + " AS d ,");
			builder.append(DepartmentDivision.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.department.id  and d.status=:status and dd.division.id=:id");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("status", Constants.ACTIVE);
			query2.setParameter("id", Integer.parseInt(id));
			@SuppressWarnings("unchecked")
			List<DepartmentVO> caList = query2.setResultTransformer(Transformers.aliasToBean(DepartmentVO.class))
					.list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
