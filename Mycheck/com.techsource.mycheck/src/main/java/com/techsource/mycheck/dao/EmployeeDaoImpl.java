package com.techsource.mycheck.dao;

import java.io.Serializable;
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

import com.techsource.mycheck.domain.EmpLogin;
import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.EmployeeDepartment;
import com.techsource.mycheck.domain.EmployeeDivision;
import com.techsource.mycheck.domain.EmployeeGroup;
import com.techsource.mycheck.domain.EmployeeRole;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.vo.EmployeeVO;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

	private final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int insertEmployee(Employee employee) {
		logger.info("insertRow called");
		System.out.println("insertRow called");
		int status_code = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(employee);
			Serializable id = session.getIdentifier(employee);
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
	public boolean checkEmailExists(String email) {
		logger.info("checkEmailExists called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + Employee.class.getName() + " where email = :email");
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<Employee> listProducts = query.list();
			if (listProducts.size() > 0) {
				logger.info("email exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean checkEmpById(int empId) {
		logger.info("checkEmailExists called");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + Employee.class.getName() + " where empId = :empId");
			query.setParameter("empId", empId);
			@SuppressWarnings("unchecked")
			List<Employee> listEmp = query.list();
			if (listEmp.size() > 0) {
				logger.info("employee exists");
				return true;
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public String registerUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeVO loginAuthentication(String username, String password) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");

			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmpLogin.class.getName() + " as el, ");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv ");

			builder.append("where  el.employee.id=e.id and ");
			builder.append("er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("el.username=:username and el.password=:password and e.status=:status");
			// System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("username", username);
			query2.setParameter("password", password);
			query2.setParameter("status", Constants.ACTIVE);
			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.uniqueResult();
			System.out.println("g " + employeeVO);
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public EmployeeVO getEmployeeByEmail(String email) {
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");

			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv ");

			builder.append("where er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("e.email=:email and e.status=:status");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("email", email);
			query2.setParameter("status", Constants.ACTIVE);
			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.uniqueResult();
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public int updateEmployee(Employee employee) {
		try {
			Session session = sessionFactory.getCurrentSession();
			// Transaction tx = session.beginTransaction();
			session.saveOrUpdate(employee);
			// tx.commit();
			// session.beginTransaction().commit();
			Serializable id = session.getIdentifier(employee);
			// session.close();
			return (Integer) id;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public EmployeeVO getEmployeeDetailsById(int id) {
		logger.info("getById called ::" + id);
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");
			builder.append("e.id=:id and e.status=:status");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("status", Constants.ACTIVE);
			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.uniqueResult();
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	// for emp
	@Override
	public EmployeeVO getEmployeeDetailsByIdAndQtr(int id, int qtrId) {
		logger.info("getById called ::" + id);
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmpQuaterTargetboard.class.getName() + " as eqt, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");
			builder.append(" e.id=:id  and eqt.employee.id=e.id and ");
			builder.append(" eqt.quaters.id=:qtrId and e.status=:status");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", id);
			query2.setParameter("qtrId", qtrId);
			query2.setParameter("status", Constants.ACTIVE);
			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public Employee getById(int id) {
		logger.info("getById called ::" + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session
					.createQuery("from " + Employee.class.getName() + " where id = :id and status=:status");
			query.setParameter("id", id);
			query.setParameter("status", Constants.ACTIVE);
			Employee employee = (Employee) query.uniqueResult();
			return employee;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public EmpLogin insertEmployeeLoginDetails(EmpLogin empLogin) {
		logger.info("insertRow called");
		System.out.println("insertRow called");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(empLogin);

		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);

		}
		return empLogin;
	}

	@Override
	public EmployeeVO getEmployeeDetailsByUsername(String username) {

		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmpLogin.class.getName() + " as el, ");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where  el.employee.id=e.id and ");
			builder.append("er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");
			builder.append("el.username=:username and e.status=:status");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("username", username);
			query2.setParameter("status", Constants.ACTIVE);
			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.uniqueResult();
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public String getPasswordByUsername(String username) {
		logger.info("getPasswordByUsername called ::" + username);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session
					.createQuery("from " + EmpLogin.class.getName() + " where username = :username");
			query.setParameter("username", username);
			EmpLogin emplLogin = (EmpLogin) query.uniqueResult();
			return emplLogin.getPassword();
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return username;
	}

	@Override
	public List<EmployeeVO> getEmployeeList() {
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");
			builder.append("e.status=:status");

			System.out.println("query ::" + builder.toString());
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameter("email", email);
			query2.setParameter("status", Constants.ACTIVE);
			@SuppressWarnings("unchecked")
			List<EmployeeVO> caList = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();
			return caList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public EmpLogin loginDetails(int id) {
		EmpLogin emplogin = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = (Query) session.createQuery("from " + EmpLogin.class.getName() + " where id = :id");
			query.setParameter("id", id);
			emplogin = (EmpLogin) query.uniqueResult();
			return emplogin;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return emplogin;
	}

	@Override
	public boolean updateLoginDetails(int id, String passwd) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"update " + EmpLogin.class.getName() + " set password = :password where emp_id = :emp_id ");
			query.setString("password", passwd);
			query.setString("emp_id", String.valueOf(id));
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

	@Override
	public int deleteEmployeeById(int id) {
		logger.info("deleteEmployeeById called");
		int flag = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Employee employee = (Employee) session.load(Employee.class, id);
			employee.setStatus("INACTIVE");
			session.update(employee);
			flag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			// throw new MyException(e.getMessage(),e);
			return 0;
		}
		return flag;
	}

	@Override
	public List<EmployeeVO> getEmployeeByLineManagerId(int lnmId) {
		logger.info("getById called ::" + lnmId);
		try {
			Session session = sessionFactory.getCurrentSession();

			StringBuilder builder = new StringBuilder();
			builder.append("select e.id as id,e.fname as fname , e.empId as empId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er ");
			builder.append("where er.employee.id=e.id and er.role.name NOT IN ( 'ADMIN', 'CEO' ) and ");
			builder.append(" er.role.id=:id ");
			String str = "ADMIN";
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameter("name", str);
			query2.setParameter("id", lnmId);
			List<EmployeeVO> employeeVO = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public EmployeeVO getgrpDepDevsnByLinemngrId(int empId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select e.id as id ,  ed.department.id as departmentId , ");

			builder.append("ediv.division.id as divisionId, eg.group.id as groupId from ");

			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeGroup.class.getName() + " As eg ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv ");
			/*
			 * SELECT eg.group_id,ed.dep_id,ediv.div_id FROM
			 * mycheck.employee_group as eg left join mycheck.employee as e on
			 * e.id=eg.emp_id left join mycheck.employee_department as ed on
			 * e.id=ed.emp_id left join mycheck.employee_division as ediv on
			 * e.id=ediv.emp_id where e.id=3;
			 */

			builder.append("where  eg.employee.id=e.id and ");
			builder.append("er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("e.id=:id");
			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("id", empId);

			EmployeeVO employeeVO = (EmployeeVO) query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.uniqueResult();
			System.out.println("emp grp id: " + employeeVO.getId());
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}

	}

	@Override
	public List<EmployeeVO> getEmpByEmpId(int empId, int depId, int divId, int groupId, boolean flag) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append(
					"er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where  eg.employee.id=e.id and ");
			builder.append("er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");

			builder.append("eg.group.id=:groupId and ");
			builder.append("ed.department.id=:depId and ");
			builder.append("ediv.division.id=:divId and ");
			if (flag) {
				builder.append("er.role.name!=:name ");
				// String name="LINE MANAGER";
			} else {
				builder.append("e.id=:id ");
			}
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameter("id", empId);
			query2.setParameter("depId", depId);
			query2.setParameter("divId", divId);
			query2.setParameter("groupId", groupId);
			if (flag) {
				query2.setParameter("name", "LINE MANAGER");
			} else {
				query2.setParameter("id", empId);
			}

			List<EmployeeVO> employeeVO = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			System.out.println("g " + employeeVO);
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	@Override
	public List<EmployeeVO> getLinemanagergpByDivDepId(int divId, int depId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id, e.fname as fname, e.lname as lname,");
			builder.append(
					"er.role.name as role, er.role.id as roleId, ed.department.id as departmentId,"
					+ "ed.department.name as department,");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er , ");
			builder.append(EmployeeDepartment.class.getName() + " as ed , ");
			builder.append(EmployeeDivision.class.getName() + " as ediv ");

			builder.append("where er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("ed.department.id=:depId and ");
			builder.append("ediv.division.id=:divId and ");
			builder.append("er.role.name=:name ");

			Query query2 = session.createQuery(builder.toString());
			query2.setParameter("depId", depId);
			query2.setParameter("divId", divId);
			query2.setParameter("name", "LINE MANAGER");

			List<EmployeeVO> employeeVO = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			System.out.println("g " + employeeVO);
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
	@Override
	public List<EmployeeVO> getEmpByDivDepIdGrpId(int divId, int depId,int grpId) {
//		try {
//			Session session = sessionFactory.getCurrentSession();
//			StringBuilder builder = new StringBuilder();
//			builder.append(
//					"select e.id as id, e.fname as fname, e.lname as lname,");
//			builder.append(
//					"er.role.name as role, er.role.id as roleId, ed.department.id as departmentId,"
//					+ "ed.department.name as department,");
//			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
//			
//			builder.append(Employee.class.getName() + " AS e ,");
//			builder.append(EmployeeRole.class.getName() + " as er , ");
//			builder.append(EmployeeDepartment.class.getName() + " as ed , ");
//			builder.append(EmployeeDivision.class.getName() + " as ediv , ");
//			builder.append(EmployeeGroup.class.getName() + " as eg ");
//
//			builder.append("where er.employee.id=e.id and ");
//			builder.append("ed.employee.id=e.id and ");
//			builder.append("ediv.employee.id=e.id and ");
//			builder.append("eg.employee.id=e.id and ");
//			builder.append("ed.department.id=:depId and ");
//			builder.append("ediv.division.id=:divId and ");
//			builder.append("eg.group.id=:grpId and ");
//			builder.append("er.role.name not in('LINE MANAGER','CEO','ADMIN')");
//
//			Query query2 = session.createQuery(builder.toString());
//			query2.setParameter("depId", depId);
//			query2.setParameter("divId", divId);
//			query2.setParameter("grpId", grpId);
//			
//			
//
//			List<EmployeeVO> employeeVO =query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class)).list();
//			System.out.println("g " + employeeVO.size());
//			return employeeVO;
		
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append(
					"select e.id as id,e.fname as fname,e.lname as lname,e.email as email,e.phone as phone,e.address as address,e.empId as empId,");
			builder.append("er.role.name as role,er.role.id as roleId, ed.department.id as departmentId,ed.department.name as department,");
			builder.append("eg.group.name as group,eg.group.id as groupId, ");
			builder.append("ediv.division.name as division,ediv.division.id as divisionId from ");
			builder.append(Employee.class.getName() + " AS e ,");
			builder.append(EmployeeRole.class.getName() + " as er, ");
			builder.append(EmployeeDepartment.class.getName() + " as ed, ");
			builder.append(EmployeeDivision.class.getName() + " as ediv, ");
			builder.append(EmployeeGroup.class.getName() + " as eg ");

			builder.append("where  eg.employee.id=e.id and ");
			builder.append("er.employee.id=e.id and ");
			builder.append("ed.employee.id=e.id and ");
			builder.append("ediv.employee.id=e.id and ");
			builder.append("eg.employee.id=e.id and ");

			builder.append("eg.group.id=:groupId and ");
			builder.append("ed.department.id=:depId and ");
			builder.append("ediv.division.id=:divId ");
			
			System.out.println("query ::>>>>>>>>>>>>>>"+builder.toString());
			Query query2 = session.createQuery(builder.toString());
			// query2.setParameter("id", empId);
			query2.setParameter("groupId", grpId);
			query2.setParameter("depId", depId);
			query2.setParameter("divId", divId);
			

			@SuppressWarnings("unchecked")
			List<EmployeeVO> employeeVO = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			System.out.println("g " + employeeVO);
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
	@Override
	public List<EmployeeVO> totalEmployees() {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder builder = new StringBuilder();
			builder.append("select e.id as id from ");
			builder.append(Employee.class.getName() + " AS e ");


		
			Query query2 = session.createQuery(builder.toString());
		

			List<EmployeeVO> employeeVO = query2.setResultTransformer(Transformers.aliasToBean(EmployeeVO.class))
					.list();
			System.out.println("g " + employeeVO.size());
			return employeeVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.techsource.mycheck.dao.EmployeeDao#updateUserName(int, java.lang.String)
	 */
	//just for testing
	@Override
//	@Transactional
	public boolean updateUserName(int id, String userName, String password) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			
			StringBuilder builder = new StringBuilder();
			builder.append(" update  ");
			builder.append(EmpLogin.class.getName() + " AS el ");
			//builder.append(Employee.class.getName() + " AS e ");
			builder.append(" Set  el.username = :username , el.password = :password where el.employee.id = :emp_id "); 
			//builder.append("	and e.id=el.employee.id ");

			Query query = session.createQuery(builder.toString());
			query.setString("username", userName);
			query.setString("password", password);
			query.setParameter("emp_id", id);
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
