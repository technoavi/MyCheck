package com.techsource.mycheck.dao;

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

import com.techsource.mycheck.domain.Group;
import com.techsource.mycheck.domain.GroupDepartment;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.GroupVO;

@Repository("groupDao")
public class GroupDaoImpl implements GroupDao {
	private final Logger logger = LoggerFactory.getLogger(GroupDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insertGroup(Group Group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Group getRowByGroup(String group) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + Group.class.getName() + " where name=:group");
			query.setParameter("group", group);
			Group department2 = (Group) query.uniqueResult();
			return department2;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GroupVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getRowById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Group group = (Group) session.get(Group.class, id);
		return group;
	}

	@Override
	public int updateRow(Group Group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GroupVO> getListByDepartment(String departmentId) {
		logger.info("getListByDivision called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select g.id as id,g.name as name from ");
			builder.append(Group.class.getName() + " AS g ,");
			builder.append(GroupDepartment.class.getName() + " as gd ");
			builder.append("where  g.id.id=gd.group.id  and g.status=:status and gd.department.id=:id");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("status", Constants.ACTIVE);
			query2.setParameter("id", Integer.parseInt(departmentId));
			@SuppressWarnings("unchecked")
			List<GroupVO> caList = query2.setResultTransformer(Transformers.aliasToBean(GroupVO.class))
					.list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


}
