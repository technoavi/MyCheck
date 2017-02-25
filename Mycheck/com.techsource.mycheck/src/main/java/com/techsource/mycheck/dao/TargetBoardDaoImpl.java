package com.techsource.mycheck.dao;

import java.io.Serializable;
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

import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Quaters;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.domain.TargetboardTargetquestions;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Repository("targetBoardDao")
public class TargetBoardDaoImpl implements TargetBoardDao {
	private final Logger logger = LoggerFactory.getLogger(TargetBoardDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int insertTargetBoard(TargetBoard targetBoard) {
		logger.info("insertTargetBoard called");
		System.out.println("insertTargetBoard called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(targetBoard);
			Serializable id = session.getIdentifier(targetBoard);
			status_code = (Integer) id;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
			// logger.error("Expection occured " + e.getMessage());
			// status_code = -1;
		}
		return status_code;
	}

	@Override
	public List<TargetBoardVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetBoard getRowById(int id) {
		try {
			System.out.println("TargetBoard id" + id);
			Session session = sessionFactory.getCurrentSession();
			TargetBoard targetbd = (TargetBoard) session.get(TargetBoard.class, id);
			return targetbd;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateRow(TargetBoard targetBoard) {
		logger.info("updateRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(targetBoard);
			Serializable id = session.getIdentifier(targetBoard);
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
		//return status_code;
		//return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TargetBoardVO> getListByEmployeeId(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TargetBoardVO> getTargetByEmpIdAndQtrId(int empId, int qtrId) {
		logger.info("getTargetByEmpIdAndQtrId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id,d.name as name, d.state as state, d.description as description  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid and dd.quaters.id=:qid");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empId);
			query2.setParameter("qid", qtrId);
			@SuppressWarnings("unchecked")
			List<TargetBoardVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TargetBoardVO getTargetByEmpIdAndQtrIds(int empId, int qtrId) {
		logger.info("getTargetByEmpIdAndQtrIds called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id,d.name as name, d.description as description  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid and dd.quaters.id=:qid");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empId);
			query2.setParameter("qid", qtrId);

			@SuppressWarnings("unchecked")
			TargetBoardVO targetVOList = (TargetBoardVO) query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).uniqueResult();
			System.out.println(targetVOList);
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TargetBoardVO> getTargetbyEmpIdAndYearAndQtr(int empid, String year, String qtr) {
		logger.info("getYearsbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id,d.name as name, d.description as description  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(Quaters.class.getName() + " AS q,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append(
					"where  d.id.id=dd.targetBoard.id and dd.employee.id=:eid and dd.quaters.year=:year and dd.quaters.name=:qtr");
			// builder.append("where d.id.id=dd.quaters.id and
			// dd.employee.id=:id and d.year=:year");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empid);
			query2.setParameter("year", year);
			query2.setParameter("qtr", qtr);
			@SuppressWarnings("unchecked")
			List<TargetBoardVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TargetBoardVO getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(int qtrId, int tgtBdId) {
		logger.info("getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id,  d.name as name,d.description as description  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id and dd.quaters.id=:qtr and dd.targetBoard.id=:tgtBdId");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("qtr", qtrId);
			query2.setParameter("tgtBdId", tgtBdId);

			@SuppressWarnings("unchecked")
			TargetBoardVO targetVOList = (TargetBoardVO) query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).uniqueResult();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpQuaterTargetboard> getCopiedQuarterSourceDetails(int empId, int quarterId) {
		// TODO Auto-generated method stub
		logger.info("getYearsbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();

			builder.append("from EmpQuaterTargetboard where employee.id=:empId and quaters.id=:qtr");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("qtr", quarterId);
			query2.setParameter("empId", empId);

			return query2.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void persistObject(Object object) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().merge(object);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean updateTargetBd(TargetBoard targetBoard) {
		logger.info("updateRow called");
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(targetBoard);
			Serializable id = session.getIdentifier(targetBoard);
			flag = true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public List<TargetBoardVO> getTargetBdByLnmngrId(String employeeIds) {
		logger.info("getTargetBdByLnmngrId called");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select  d.id as id,  d.name as name   from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(TargetBoard.class.getName() + " AS d,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as eqt ");

			builder.append(" where eqt.targetBoard.id=d.id and ");
			builder.append(" eqt.employee.id=e.id and ");
			builder.append("eqt.employee.id in (" + employeeIds + ")  order by d.modified desc ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameterl("employeeIds",employeeIds);
			@SuppressWarnings("unchecked")
			List<TargetBoardVO> caList = query2.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class))
					.setMaxResults(10).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<TargetBoardVO> getTargetByEmpId(int empId) {
		logger.info("getTargetByEmpIdAndQtrId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id, d.name as name  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid order by d.modified desc");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empId);
			@SuppressWarnings("unchecked")
			List<TargetBoardVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public List<TargetBoard> getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(int empId, int qtrId) {
		logger.info("getYearsbyEmpId called");
		try {
			// based on quarter id i need to get target board name,targetboard
			// desc. and questions[in question name,question desc. and status]..
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append("select tb.id as id, tb.name as name, tb.description as description, tq.id as id ,");
			builder.append("tq.name as name, tq.description as description  from ");
			builder.append(TargetBoard.class.getName() + " AS tb ,");
			builder.append(TargetQuestions.class.getName() + " as tq , ");
			builder.append(TargetboardTargetquestions.class.getName() + " as tbtq , ");
			builder.append(EmpQuaterTargetboard.class.getName() + " as eqt ");

			builder.append("where tbtq.targetQuestions.id=tq.id and tbtq.targetBoard.id=tb.id and ");
			builder.append(" eqt.targetBoard.id=tb.id and ");
			builder.append("  eqt.employee.id=:empId and ");
			builder.append("eqt.quaters.id=:qtrId");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("empId", empId);
			query2.setParameter("qtrId", qtrId);
			@SuppressWarnings("unchecked")
			List<TargetBoard> caList = query2.setResultTransformer(Transformers.aliasToBean(TargetBoard.class))
					.list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<TargetBoardVO> getRecentTargetBdByEmpId(int empId) {
		logger.info("getTargetBdByEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id, d.name as name  from ");
			builder.append(TargetBoard.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid and d.state='OPEN'  order by d.modified desc");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empId);
			@SuppressWarnings("unchecked")
			List<TargetBoardVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetBoardVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

/*	@Override
	public List<TargetQuestionVO> getTargetQstnByEmpId(int empId) {
		logger.info("getTargetQstnByEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id, d.name as name  from ");
			builder.append(TargetQuestions.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid and d.state='OPEN'order by d.modified desc");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", empId);
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> targetVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class)).list();
			return targetVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}*/

	
}
