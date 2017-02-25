package com.techsource.mycheck.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Quaters;
import com.techsource.mycheck.vo.QuarterVO;

@Repository("quarterDao")
public class QuarterDaoImpl implements QuarterDao {

	private final Logger logger = LoggerFactory.getLogger(QuarterDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int insertQuater(Quaters quater) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<QuarterVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quaters getRowById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Quaters role = (Quaters) session.get(Quaters.class, id);
			return role;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateRow(Quaters quater) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<QuarterVO> getListByEmployeeId(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuarterVO> getYearsbyEmpId(int id,String state) {
		logger.info("getYearsbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select  d.id as id, d.year as year, d.name as name from ");
			builder.append(Quaters.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd, ");
			builder.append(Employee.class.getName() + " as e ");
			// builder.append("where d.employee.id=dd.employee.id");
			builder.append("where d.id=dd.quaters.id");
			builder.append(" and e.id=dd.employee.id");
			builder.append(" and  dd.employee.id=:eid and dd.state=:state group by dd.quaters.id");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("eid", id);
			query2.setParameter("state", state);
			@SuppressWarnings("unchecked")
			List<QuarterVO> caList = query2.setResultTransformer(Transformers.aliasToBean(QuarterVO.class)).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<QuarterVO> getYearAndQtrbyEmpId(int id, String year) {
		logger.info("getYearAndQtrbyEmpId called");
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select d.id as id, d.year as year, d.name as name  from ");
			builder.append(Quaters.class.getName() + " AS d ,");
			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
			// builder.append("where d.id=dd.targetBoard.id and
			// dd.employee.id=:eid and dd.quater.id=:qid");
			// builder.append("where d.id.id=dd.targetBoard.id and
			// dd.employee.id=:eid and dd.quaters.year=:year");
			builder.append("where  d.id.id=dd.quaters.id   and dd.employee.id=:id and d.year=:year");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("year", year);
			@SuppressWarnings("unchecked")
			List<QuarterVO> quaterVO2 = query2.setResultTransformer(Transformers.aliasToBean(QuarterVO.class)).list();
			return quaterVO2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	 public Quaters getNextQuarterDetails(int empId, boolean flag) { // TODO
	                 // needs to
	                 // provide
	  // complete status for
	  // Quarter ex:-
	  // OPEN/COMPLETED
	  logger.info("getNextQuarterDetails called");
	  Query query2 = null;
	  Session session = null;
	  try {
	   session = sessionFactory.getCurrentSession();
	   StringBuilder builder = new StringBuilder();
	   if (flag) {
	    builder.append("from Quaters as d where d.id >( SELECT max(quaters.id) FROM EmpQuaterTargetboard  where employee.id=:empId) order by d.id DESC");
	    query2 = session.createQuery(builder.toString());
	    query2.setParameter("empId", empId);
	    //System.out.println("query2.list() size ::"+query2.list().size());
	    List<Quaters> quaters=query2.list();
	    if(quaters!=null){
	     if(quaters.size()>0)
	     return (Quaters) quaters.get(0);
	    }
	   } else {
	    Quaters oldest =
	         (Quaters) session.createCriteria(Quaters.class)
	         .addOrder(Order.desc("id"))
	         .setMaxResults(1)
	         .uniqueResult();
	    
	    //builder.append("SELECT max(q.id) FROM Quaters as q");
	    return oldest;
	   }

	   // builder.append(Quaters.class.getName() + " AS d ,");
	   // builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");

	  // logger.info("msg called");
	   // QuarterVO quarterVO=(QuarterVO)
	   // 
	query2.setResultTransformer(Transformers.aliasToBean(QuarterVO.class)).list
	().get(0);
	   
	  } catch (IndexOutOfBoundsException e) {
	   logger.info("Exception occurred " + e);
	   e.printStackTrace();
	   return null;
	  }
	  return null;
	 }



	

	@Override
	public boolean getQuartersExistsByEmpId(int id) {
		logger.info("getQuaterByEmpId called");
			
			try {
				Session session = sessionFactory.getCurrentSession();
				Query query = (Query) session.createQuery("from " + EmpQuaterTargetboard.class.getName()  + " as etb where etb.employee.id = :eid");
				query.setParameter("eid", id);
				@SuppressWarnings("unchecked")
				List<Quaters> listProducts = query.list();
				if (listProducts.size() > 0) {
					logger.info("email exists");
					return true;
				}
			} catch (HibernateException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			//return false;
			return false;
	}
			
//			Session session = sessionFactory.getCurrentSession();
//			StringBuilder builder = new StringBuilder();
//			builder.append("from ");
//			//builder.append(Quaters.class.getName() + " AS d ,");
//			builder.append(EmpQuaterTargetboard.class.getName() + " as dd ");
//			//builder.append(Employee.class.getName() + " as e ");
//			// builder.append("where d.employee.id=dd.employee.id");
//			//builder.append("where d.id=dd.quaters.id");
//			//builder.append(" and e.id=dd.employee.id");
//			builder.append(" where  dd.employee.id=:eid");
//			System.out.println("query ::"+builder.toString());
//			Query query2 = session.createQuery(builder.toString());
//			query2.setParameter("eid", id);
//			@SuppressWarnings("unchecked")
//			List<Quaters> quaters = (List<Quaters>) query2.list();
//			if (quaters != null) {
//				if(quaters.size()>0){
//					return true;
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return false;
	//}
}

