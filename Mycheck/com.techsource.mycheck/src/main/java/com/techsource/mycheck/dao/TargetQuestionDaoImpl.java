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

import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.domain.TargetboardTargetquestions;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Repository("targetQuestionDao")
public class TargetQuestionDaoImpl implements TargetQuestionDao {

	private final Logger logger = LoggerFactory.getLogger(TargetQuestionDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean insertTargetQuestions(TargetQuestions targetQuestions) {
		logger.info("insertTargetQuestions called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(targetQuestions);
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			return false;
			
		}
		
	}

	@Override
	public List<TargetQuestionVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetQuestions getRowById(int id) {
		
		try {
			System.out.println("question id"+id);
			Session session = sessionFactory.getCurrentSession();
			TargetQuestions role = (TargetQuestions) session.get(TargetQuestions.class, id);
			return role;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateRow(TargetQuestions targetQuestions) {
		logger.info("updateRow called");
		System.out.println("updateRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(targetQuestions);
			Serializable id = session.getIdentifier(targetQuestions);
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
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TargetQuestionVO> getListByTargetBoardId(String tbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TargetQuestionVO> getTargetQstnbyEmpId(int id) {
		logger.info("getTargetQstnbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id,d.name as name  from ");
			builder.append(TargetQuestions.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			// builder.append("where d.id=dd.targetBoard.id and
			// dd.employee.id=:eid and dd.quater.id=:qid");
			builder.append("where  d.id.id=dd.targetBoard.id  and dd.employee.id=:eid ");

			/*
			 * builder.append("where  d.id=dd.quaters.id");
			 * builder.append(" and  dd.employee.id=:eid group by d.id");
			 */

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", id);
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> targetQuestionVOList = query2
					.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class)).list();
			return targetQuestionVOList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TargetQuestionVO> getTargetQstnbyTargetId(int tbId) {
		logger.info("getTargetQstnbyTargetId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  tq.id as id,tq.name as name, tq.status as status ,tq.description as description from ");
			builder.append(TargetQuestions.class.getName() + " AS tq ,");
			builder.append(TargetBoard.class.getName() + " AS tb ,");
			builder.append(TargetboardTargetquestions.class.getName() + " as tbq ");
			builder.append("where  tq.id=tbq.targetQuestions.id and tb.id=tbq.targetBoard.id and  tbq.targetBoard.id=:tbId");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("tbId", tbId);
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> targetQuestionVOList2 = query2
					.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class)).list();
			return targetQuestionVOList2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TargetQuestionVO> getTargetQstnByQtrId(int id) {
		logger.info("getTargetQstnByQtrId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id, d.name as name   from ");
			builder.append(TargetQuestions.class.getName() + " AS d ,");
			builder.append(TargetboardTargetquestions.class.getName() + " AS tb ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			builder.append("where  tb.targetQuestions.id=d.id  and ");
			builder.append("dd.targetBoard.id=tb.targetBoard.id and  dd.quaters.id=:qid");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("qid", id);
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> targetQuestionVOList2 = query2
					.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class)).list();
			return targetQuestionVOList2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TargetQuestionVO> getquestionsByTgtIdId(int tbId) {
		logger.info("getquestionsByTgtIdId called");
		try {

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  tq.id as id, tq.name as name ,tq.description as description,  tq.status as status from ");
			builder.append(TargetQuestions.class.getName() + " AS tq ,");
			builder.append(TargetboardTargetquestions.class.getName() + " AS tt ,");
			builder.append(TargetBoard.class.getName() + " as tb ");
			builder.append("where  tt.targetQuestions.id=tq.id  and ");
			builder.append("tt.targetBoard.id=tb.id and tt.targetBoard.id=:tbId");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("tbId", tbId);

			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> targetQuestionVOList2 = query2
					.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class)).list();
			return targetQuestionVOList2;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateTargetQuestions(TargetQuestions targetQuestion) {
		logger.info("updateTargetQuestions  dao called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(targetQuestion);
			Serializable id = session.getIdentifier(targetQuestion);
			status_code = (Integer) id;
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//throw new MyException(e.getMessage(), e);
			return false;
			// logger.error("Expection occured " + e.getMessage());
			// status_code = -1;
		}
	}

	@Override
	public boolean checkQstnIdExists(int  qstId) {
		logger.info("checkQstnIdExists called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + TargetQuestions.class.getName() + " where id = :qstId");
			query.setParameter("qstId", qstId);
			@SuppressWarnings("unchecked")
			List<TargetQuestions> targetQuestions = query.list();
			if (targetQuestions.size() > 0) {
				logger.info("id exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean deleteQuestionById(int qstnId) {
		boolean flag=false;
		try {
			Session session = sessionFactory.getCurrentSession();
			TargetQuestions targetqstn = (TargetQuestions) session.load(TargetQuestions.class, qstnId);
			if(targetqstn!=null){
		//	targetqstn.setStatus(Byte.valueOf("0"));
			session.delete(targetqstn);
			flag =true;
			}else{
				
				flag=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			 throw new MyException(e.getMessage(),e);
			
		}
		 return flag;
	}
// removable
	/*@Override
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
//for linemngr 
	@Override
	public List<TargetQuestionVO> getTargetQstnByLnmngrId(String lnId) {
		logger.info("getTargetQstnByLnmngrId called");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select  d.id as id,  d.name as name , d.status as status from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(TargetQuestions.class.getName() + " AS d , ");
			builder.append(TargetBoard.class.getName() + " AS tb , ");
			builder.append(TargetboardTargetquestions.class.getName() + " AS tq , ");
			builder.append(EmpQuaterTargetboard.class.getName() + " as eqt ");

			builder.append(" where tq.targetQuestions.id=d.id  and ");
			builder.append("  tq.targetBoard.id=tb.id  and ");
			builder.append("  eqt.targetBoard.id=tb.id  and ");
			builder.append(" eqt.employee.id=e.id and ");
			builder.append("eqt.employee.id in (" + lnId + ") and tb.state='CLOSE' order by d.modified desc ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> caList = query2.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class))
					.setMaxResults(10).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
	
	//for empl
	@Override
	public List<TargetQuestionVO> getTargetQstnByEmpIdAndQtrId(int empId, int qtrId) {
		logger.info("getTargetQstnByLnmngrId called");
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();

			builder.append("select  d.id as id,  d.name as name , d.status as status from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(TargetQuestions.class.getName() + " AS d , ");
			builder.append(TargetBoard.class.getName() + " AS tb , ");
			builder.append(TargetboardTargetquestions.class.getName() + " AS tq , ");
			builder.append(EmpQuaterTargetboard.class.getName() + " as eqt ");

			builder.append(" where tq.targetQuestions.id=d.id  and ");
			builder.append("  tq.targetBoard.id=tb.id  and ");
			builder.append("  eqt.targetBoard.id=tb.id  and ");
			builder.append(" eqt.employee.id=e.id and ");
			builder.append("eqt.employee.id=:empId and eqt.quaters.id=:qtrId  order by d.modified desc ");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("empId", empId);
			query2.setParameter("qtrId", qtrId);
			@SuppressWarnings("unchecked")
			List<TargetQuestionVO> caList = query2.setResultTransformer(Transformers.aliasToBean(TargetQuestionVO.class))
					.setMaxResults(10).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
	@Override
	public boolean updateStatus(int qtnId, byte status) {
		
		boolean flag=false;
		logger.info("updateStatus called");
		try {
			//String hql = "UPDATE "+TargetQuestions.class.getName()+" set status = :status "  + "WHERE id = :qtnId";

			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(TargetQuestions.class.getName());
			builder.append(" set status = :status , modified=:modified WHERE id = :qtnId");

			Query query2 = session.createQuery(builder.toString());
		
			query2.setParameter("status", status);
			query2.setParameter("modified", new Date());
			query2.setParameter("qtnId", qtnId);

			int result = query2.executeUpdate();
			if(result>0){
				flag=true;
			}
			//System.out.println("Rows affected: " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		
		
		return flag;
	}
}
