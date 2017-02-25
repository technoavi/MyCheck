package com.techsource.mycheck.service;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.DepartmentDao;
import com.techsource.mycheck.dao.DivisionDao;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.GroupDao;
import com.techsource.mycheck.dao.RoleDao;
import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.domain.EmpLogin;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.EmployeeDepartment;
import com.techsource.mycheck.domain.EmployeeDivision;
import com.techsource.mycheck.domain.EmployeeGroup;
import com.techsource.mycheck.domain.EmployeeRole;
import com.techsource.mycheck.domain.Group;
import com.techsource.mycheck.domain.Role;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MyException;
import com.techsource.mycheck.utility.SendMail;
import com.techsource.mycheck.utility.Utility;
import com.techsource.mycheck.vo.EmployeeVO;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private DivisionDao divisionDao;
	@Autowired
	private GroupDao groupDao;

	/******
	 * insertEmployee
	 * 
	 * @param employee
	 * @param role
	 * @param department
	 * @param division
	 * @return
	 */
	@Override
	@Transactional
	public String insertEmployee(Employee employee, String role, String department, String division) {
		logger.info("insertRow called");
		try {

			boolean email_exists = employeeDao.checkEmailExists(employee.getEmail());
			if (!email_exists) {

				boolean rolesFlag = false;
				boolean deptFlag = false;
				boolean divFlag = false;

				/** ------------ role info started-------------- */
				// get role by id
				Role dbRole = roleDao.getRowByRole(role);
				if (dbRole != null) {
					// System.out.println("dbRole ::" + dbRole.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeRole employeeRole = new EmployeeRole();
					employeeRole.setRole(dbRole);

					employeeRole.setCreated(new Date());
					employeeRole.setModified(new Date());
					employeeRole.setDeleted(new Date());

					Set<EmployeeRole> roles = new HashSet<EmployeeRole>();
					roles.add(employeeRole);

					employee.setEmployeeRoles(roles);
					employeeRole.setEmployee(employee);
					employeeRole.setRole(dbRole);
					rolesFlag = true;
				}

				// /** ------------ department info started-------------- */
				//
				Department dbDepartment = departmentDao.getRowByDepartment(department);
				if (dbDepartment != null) {
					// System.out.println("dbDepartment ::" +
					// dbDepartment.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeDepartment employeeDepartment = new EmployeeDepartment();
					employeeDepartment.setDepartment(dbDepartment);
					employeeDepartment.setCreated(new Date());
					employeeDepartment.setModified(new Date());
					employeeDepartment.setDeleted(new Date());

					Set<EmployeeDepartment> empDepts = new HashSet<EmployeeDepartment>();
					empDepts.add(employeeDepartment);

					employee.setEmployeeDepartments(empDepts);
					employeeDepartment.setEmployee(employee);
					employeeDepartment.setDepartment(dbDepartment);
					deptFlag = true;
				}

				// /** ------------ department info started-------------- */
				//
				Division dbDivision = divisionDao.getRowByDivision(division);
				if (dbDivision != null) {
					// System.out.println("dbDivision ::" +
					// dbDivision.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeDivision employeeDivision = new EmployeeDivision();
					employeeDivision.setDivision(dbDivision);

					employeeDivision.setCreated(new Date());
					employeeDivision.setUpdated(new Date());
					employeeDivision.setDeleted(new Date());

					Set<EmployeeDivision> empDepts = new HashSet<EmployeeDivision>();
					empDepts.add(employeeDivision);

					employee.setEmployeeDivisions(empDepts);
					employeeDivision.setEmployee(employee);
					employeeDivision.setDivision(dbDivision);
					divFlag = true;
				}

				/** ------------ role info end-------------- */
				if (rolesFlag & deptFlag & divFlag) {
					// if(rolesFlag ){
					int i = employeeDao.insertEmployee(employee);
					if (i > 0) {

						EmpLogin empLogin = new EmpLogin();
						empLogin.setUsername(employee.getEmail());
						empLogin.setPassword(Utility.INSTANCE.getRandomPassword());
						empLogin.setStatus(Constants.ACTIVE);
						empLogin.setCreated(new Date());
						empLogin.setModified(new Date());
						empLogin.setDeleted(new Date());

						Set<EmpLogin> empLogins = new HashSet<EmpLogin>();
						empLogins.add(empLogin);

						employee.setEmpLogins(empLogins);
						empLogin.setEmployee(employee);
						EmpLogin empLogin2 = employeeDao.insertEmployeeLoginDetails(empLogin);
						if (empLogin2.getId() > 0) {
							// send email
							ExecutorService executor = Executors.newFixedThreadPool(1);
							Runnable worker = new SendMail(employee.getEmail(), empLogin2.getUsername(),
									empLogin2.getPassword(), "You are Registred with MYCHECK.");
							executor.execute(worker);
							executor.shutdown();

							return Constants.EMPLOYEE_CREATED_SUCCESS;
						}

					}
				} else {
					return "Role or Division or Department not found.Please check";
				}

			} else {
				// throw new MyException("Email exists,Please check ..!");
				return Constants.EMPLOYEE_EMAIL_EXISTS;
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
		return null;

	}

	private String singleEmployee(Employee employee, int roleId, int departmentId, int divisionId, int groupId) {
		logger.info("insertRow called");
		try {
			boolean email_exists = employeeDao.checkEmailExists(employee.getEmail());
			if (!email_exists) {
				boolean rolesFlag = false;
				boolean deptFlag = false;
				boolean divFlag = false;
				boolean groupFlag = false;
				/** ------------ role info started-------------- */
				// get role by id
				Role dbRole = roleDao.getRowById(roleId);
				if (dbRole != null) {
					// System.out.println("dbRole ::" + dbRole.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeRole employeeRole = new EmployeeRole();
					employeeRole.setRole(dbRole);
					employeeRole.setCreated(new Date());
					employeeRole.setModified(new Date());
					employeeRole.setDeleted(new Date());
					Set<EmployeeRole> roles = new HashSet<EmployeeRole>();
					roles.add(employeeRole);

					employee.setEmployeeRoles(roles);
					employeeRole.setEmployee(employee);
					employeeRole.setRole(dbRole);
					rolesFlag = true;
				}

				/** ------------ department info started-------------- */
				// System.out.println("dbDepartment name ::" + department);
				Department dbDepartment = departmentDao.getRowById(departmentId);

				if (dbDepartment != null) {
					employee.setModified(new Date());
					// System.out.println("dbDepartment ::" +
					// dbDepartment.getName());
					// assign role to employee
					EmployeeDepartment employeeDepartment = new EmployeeDepartment();
					employeeDepartment.setDepartment(dbDepartment);
					employeeDepartment.setCreated(new Date());
					employeeDepartment.setModified(new Date());
					employeeDepartment.setDeleted(new Date());
					Set<EmployeeDepartment> roles = new HashSet<EmployeeDepartment>();
					roles.add(employeeDepartment);

					employee.setEmployeeDepartments(roles);
					employeeDepartment.setEmployee(employee);
					employeeDepartment.setDepartment(dbDepartment);
					deptFlag = true;
				} else {
					// System.out.println("called");
				}

				// /** ------------ department info started-------------- */
				//
				Division dbDivision = divisionDao.getRowById(divisionId);
				if (dbDivision != null) {
					// System.out.println("dbDivision ::" +
					// dbDivision.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeDivision employeeDivision = new EmployeeDivision();
					employeeDivision.setDivision(dbDivision);
					employeeDivision.setCreated(new Date());
					employeeDivision.setUpdated(new Date());
					employeeDivision.setDeleted(new Date());
					Set<EmployeeDivision> empDepts = new HashSet<EmployeeDivision>();
					empDepts.add(employeeDivision);

					employee.setEmployeeDivisions(empDepts);

					employeeDivision.setEmployee(employee);
					employeeDivision.setDivision(dbDivision);
					divFlag = true;
				}

				// /** ------------ Group info started-------------- */
				//
				Group dbGroup = groupDao.getRowById(groupId);

				if (dbGroup != null) {
					// System.out.println("dbDivision ::" +
					// dbDivision.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeGroup employeeGroup = new EmployeeGroup();
					employeeGroup.setGroup(dbGroup);
					// employeeDivision.setCreated(new Date());
					// employeeDivision.setUpdated(new Date());
					// employeeDivision.setDeleted(new Date());
					Set<EmployeeGroup> empGroups = new HashSet<EmployeeGroup>();
					empGroups.add(employeeGroup);

					employee.setEmployeeGroups(empGroups);

					employeeGroup.setEmployee(employee);
					employeeGroup.setGroup(dbGroup);
					groupFlag = true;
				}

				if (rolesFlag & deptFlag & divFlag & groupFlag) {
					// if(rolesFlag ){
					// System.out.println("flag ::" + true);
					int i = employeeDao.insertEmployee(employee);
					if (i > 0) {
						EmpLogin empLogin = new EmpLogin();
						empLogin.setUsername(employee.getEmail());
						empLogin.setPassword(Utility.INSTANCE.getRandomPassword());
						empLogin.setStatus(Constants.ACTIVE);
						empLogin.setCreated(new Date());
						empLogin.setModified(new Date());
						empLogin.setDeleted(new Date());

						Set<EmpLogin> empLogins = new HashSet<EmpLogin>();
						empLogins.add(empLogin);

						employee.setEmpLogins(empLogins);
						empLogin.setEmployee(employee);
						EmpLogin empLogin2 = employeeDao.insertEmployeeLoginDetails(empLogin);
						if (empLogin2.getId() > 0) {
							// send email
							ExecutorService executor = Executors.newFixedThreadPool(1);
							Runnable worker = new SendMail(employee.getEmail(), empLogin2.getUsername(),
									empLogin2.getPassword(), "You are Registred with MYCHECK.");
							executor.execute(worker);
							executor.shutdown();

							return String.valueOf(i);
						}

					}
				} else {
					return "0";
				}
				// return String.valueOf(this.insertEmployee(employee));

			} else {
				// throw new MyException("Email exists,Please check ..!");
				return "0";
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			// throw new MyException(e.getMessage(), e);
			e.printStackTrace();
			return "0";
		}
		return "0";

	}

	private String insertSingleEmployeeFromFile(Employee employee, String role, String department, String division,
			String group) {
		logger.info("insertSingleEmployeeFromFile called");
		try {
			boolean email_exists = employeeDao.checkEmailExists(employee.getEmail());
			if (!email_exists) {
				boolean rolesFlag = false;
				boolean deptFlag = false;
				boolean divFlag = false;
				boolean groupFlag = false;
				/** ------------ role info started-------------- */
				// get role by id
				Role dbRole = roleDao.getRowByRole(role);
				if (dbRole != null) {
					// System.out.println("dbRole ::" + dbRole.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeRole employeeRole = new EmployeeRole();
					employeeRole.setRole(dbRole);
					employeeRole.setCreated(new Date());
					employeeRole.setModified(new Date());
					employeeRole.setDeleted(new Date());
					Set<EmployeeRole> roles = new HashSet<EmployeeRole>();
					roles.add(employeeRole);

					employee.setEmployeeRoles(roles);
					employeeRole.setEmployee(employee);
					employeeRole.setRole(dbRole);
					rolesFlag = true;
				}

				/** ------------ department info started-------------- */
				// System.out.println("dbDepartment name ::" + department);
				Department dbDepartment = departmentDao.getRowByDepartment(department);

				if (dbDepartment != null) {
					employee.setModified(new Date());
					EmployeeDepartment employeeDepartment = new EmployeeDepartment();
					employeeDepartment.setDepartment(dbDepartment);
					employeeDepartment.setCreated(new Date());
					employeeDepartment.setModified(new Date());
					employeeDepartment.setDeleted(new Date());
					Set<EmployeeDepartment> roles = new HashSet<EmployeeDepartment>();
					roles.add(employeeDepartment);

					employee.setEmployeeDepartments(roles);
					employeeDepartment.setEmployee(employee);
					employeeDepartment.setDepartment(dbDepartment);
					deptFlag = true;
				} else {
					// System.out.println("called");
				}

				// /** ------------ department info started-------------- */
				//
				Division dbDivision = divisionDao.getRowByDivision(division);
				if (dbDivision != null) {
					// System.out.println("dbDivision ::" +
					// dbDivision.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeDivision employeeDivision = new EmployeeDivision();
					employeeDivision.setDivision(dbDivision);
					employeeDivision.setCreated(new Date());
					employeeDivision.setUpdated(new Date());
					employeeDivision.setDeleted(new Date());
					Set<EmployeeDivision> empDepts = new HashSet<EmployeeDivision>();
					empDepts.add(employeeDivision);

					employee.setEmployeeDivisions(empDepts);

					employeeDivision.setEmployee(employee);
					employeeDivision.setDivision(dbDivision);
					divFlag = true;
				}

				// /** ------------ Group info started-------------- */
				//
				Group dbGroup = groupDao.getRowByGroup(group);

				if (dbGroup != null) {
					// System.out.println("dbDivision ::" +
					// dbDivision.getName());
					employee.setModified(new Date());
					// assign role to employee
					EmployeeGroup employeeGroup = new EmployeeGroup();
					employeeGroup.setGroup(dbGroup);
					// employeeDivision.setCreated(new Date());
					// employeeDivision.setUpdated(new Date());
					// employeeDivision.setDeleted(new Date());
					Set<EmployeeGroup> empGroups = new HashSet<EmployeeGroup>();
					empGroups.add(employeeGroup);

					employee.setEmployeeGroups(empGroups);

					employeeGroup.setEmployee(employee);
					employeeGroup.setGroup(dbGroup);
					groupFlag = true;
				}

				if (rolesFlag & deptFlag & divFlag & groupFlag) {
					// if(rolesFlag ){
					// System.out.println("flag ::" + true);
					int i = employeeDao.insertEmployee(employee);
					if (i > 0) {
						EmpLogin empLogin = new EmpLogin();
						empLogin.setUsername(employee.getEmail());
						empLogin.setPassword(Utility.INSTANCE.getRandomPassword());
						empLogin.setStatus(Constants.ACTIVE);
						empLogin.setCreated(new Date());
						empLogin.setModified(new Date());
						empLogin.setDeleted(new Date());

						Set<EmpLogin> empLogins = new HashSet<EmpLogin>();
						empLogins.add(empLogin);

						employee.setEmpLogins(empLogins);
						empLogin.setEmployee(employee);
						EmpLogin empLogin2 = employeeDao.insertEmployeeLoginDetails(empLogin);
						if (empLogin2.getId() > 0) {
							// send email
							ExecutorService executor = Executors.newFixedThreadPool(1);
							Runnable worker = new SendMail(employee.getEmail(), empLogin2.getUsername(),
									empLogin2.getPassword(), "You are Registred with MYCHECK.");
							executor.execute(worker);
							executor.shutdown();

							return String.valueOf(i);
						}

					}
				} else {
					return "0";
				}
				// return String.valueOf(this.insertEmployee(employee));

			} else {
				// throw new MyException("Email exists,Please check ..!");
				return "0";
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			// throw new MyException(e.getMessage(), e);
			e.printStackTrace();
			return "0";
		}
		return "0";

	}

	@Override
	public String registerUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	/****
	 * insertBulkEmployee
	 * 
	 * @param employeeVOs
	 * @return
	 */
	@Override
	@Transactional
	public List<EmployeeVO> insertBulkEmployee(List<EmployeeVO> employeeVOs) {
		List<EmployeeVO> responseList = new LinkedList<EmployeeVO>();
		try {
			for (EmployeeVO employeeVO : employeeVOs) {
				Employee employee = new Employee();
				employee.setFname(employeeVO.getFname());
				employee.setLname(employeeVO.getLname());
				employee.setEmail(employeeVO.getEmail());
				employee.setPhone(employeeVO.getPhone());
				employee.setEmpId(employeeVO.getEmpId());
				employee.setAddress(employeeVO.getAddress());
				employee.setStatus(Constants.ACTIVE);
				employee.setCreated(new Date());
				employee.setModified(new Date());
				employee.setDeleted(new Date());
				// InsertEmployeeResponse employeeResponse=new
				// InsertEmployeeResponse();
				// employees.add(employee);
				// employeeResponse.setEmail(employee.getEmail());
				int id = Integer.parseInt(this.singleEmployee(employee, employeeVO.getRoleId(),
						employeeVO.getDepartmentId(), employeeVO.getDivisionId(), employeeVO.getGroupId()));
				employeeVO.setId(id);
				responseList.add(employeeVO);
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			// TODO rollback all
			e.printStackTrace();
		}

		return responseList;
	}

	@Override
	public int insertEmployee(Employee employee) {
		return employeeDao.insertEmployee(employee);
	}

	/****
	 * loginAuthentication
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Override
	@Transactional
	public EmployeeVO loginAuthentication(String email, String password) {
		return employeeDao.loginAuthentication(email, password);
	}

	/****
	 * getEmployeeByEmail
	 * 
	 * @param email
	 * @return
	 */
	@Override
	@Transactional
	public EmployeeVO getEmployeeByEmail(String email) {
		logger.info("getEmployeeByEmail called");
		return employeeDao.getEmployeeByEmail(email);
	}

	/****
	 * updateEmployee
	 * 
	 * @param employee
	 * @param roleId
	 * @param departmentId
	 * @param divisionId
	 * @return
	 */
	@Override
	@Transactional
	public int updateEmployee(Employee employee, String roleId, String departmentId, String divisionId,
			String groupId) {
		logger.info("updateEmployee called");
		// System.out.println("employee " + employee.getId());
		try {
			Role dbRole = roleDao.getRowById(Integer.parseInt(roleId));

			Employee db_employee = employeeDao.getById(employee.getId());
			if (db_employee != null) {
				// System.out.println("db object found");
			} else {
				// System.out.println("db object not found");
			}

			if (!db_employee.getEmail().equals(employee.getEmail())) {
				if (employeeDao.checkEmailExists(employee.getEmail())) {
					return -1;

				}
			}

			Set<EmployeeRole> employeeRoles = db_employee.getEmployeeRoles();
			EmployeeRole employeeRole = null;
			boolean role_flag = false;
			for (EmployeeRole employeeRole2 : employeeRoles) {
				if (!employeeRole2.getRole().equals(dbRole)) {
					employeeRole = employeeRole2;
					role_flag = true;

				}
			}

			if (role_flag) {
				employeeRoles.remove(employeeRole);
				// Role dbRole = roleDao.getRowByRole(role);
				if (dbRole != null) {
					// assign role to employee
					// EmployeeRole employeeRole2 = new EmployeeRole();
					dbRole.setModified(new Date());
					employeeRole.setRole(dbRole);
					employeeRoles.add(employeeRole);
					db_employee.setEmployeeRoles(employeeRoles);
					employeeRole.setEmployee(db_employee);
					employeeRole.setRole(dbRole);
				}
			}

			/** ------------ department info started-------------- */
			Department dbDepartment = departmentDao.getRowById(Integer.parseInt(departmentId));
			Set<EmployeeDepartment> employeeDepts = db_employee.getEmployeeDepartments();
			EmployeeDepartment employeeDept = null;
			boolean dept_flag = false;
			for (EmployeeDepartment employeeDept2 : employeeDepts) {
				if (!employeeDept2.getDepartment().equals(dbDepartment)) {
					employeeDept = employeeDept2;
					dept_flag = true;

				}
			}

			if (dept_flag) {
				employeeDepts.remove(employeeDept);

				if (dbDepartment != null) {
					// assign role to employee
					// EmployeeRole employeeRole2 = new EmployeeRole();
					dbDepartment.setModified(new Date());
					employeeDept.setDepartment(dbDepartment);
					employeeDepts.add(employeeDept);
					db_employee.setEmployeeDepartments(employeeDepts);
					employeeDept.setEmployee(db_employee);
					employeeDept.setDepartment(dbDepartment);
				}
			}

			/** ------------ Division info started-------------- */
			Division dbDivision = divisionDao.getRowById(Integer.parseInt(divisionId));

			Set<EmployeeDivision> employeeDivisions = db_employee.getEmployeeDivisions();

			// System.out.println("employeeDivisions size" +
			// employeeDivisions.size());
			EmployeeDivision employeeDiv = null;
			boolean div_flag = false;
			for (EmployeeDivision employeeDiv2 : employeeDivisions) {
				if (!employeeDiv2.getDivision().equals(dbDivision)) {
					employeeDiv = employeeDiv2;
					div_flag = true;
					// System.out.println("not equal");
				} else {
					// System.out.println(" equal");
				}
			}

			if (div_flag) {
				// System.out.println("div_flag div_flag" + div_flag);
				employeeDivisions.remove(employeeDiv);

				if (dbDivision != null) {
					dbDivision.setModified(new Date());
					employeeDiv.setDivision(dbDivision);

					employeeDivisions.add(employeeDiv);
					db_employee.setEmployeeDivisions(employeeDivisions);

					employeeDiv.setEmployee(db_employee);
					employeeDiv.setDivision(dbDivision);
				}
			}

			/** ------------ Group info started-------------- */
			Group dbGroup = groupDao.getRowById(Integer.parseInt(groupId));
			Set<EmployeeGroup> employeeGroups = db_employee.getEmployeeGroups();
			EmployeeGroup employeeGroup = null;
			boolean group_flag = false;
			for (EmployeeGroup employeeGroup2 : employeeGroups) {
				if (!employeeGroup2.getGroup().equals(dbGroup)) {
					employeeGroup = employeeGroup2;
					group_flag = true;

				}
			}

			if (group_flag) {
				employeeGroups.remove(employeeGroup);

				if (dbGroup != null) {
					// assign role to employee
					// EmployeeRole employeeRole2 = new EmployeeRole();
					dbGroup.setModified(new Date());
					// employeeGroup.setGroup(dbGroup);
					employeeGroups.add(employeeGroup);
					db_employee.setEmployeeGroups(employeeGroups);
					employeeGroup.setEmployee(db_employee);
					employeeGroup.setGroup(dbGroup);
				}
			}

			db_employee.setEmail(employee.getEmail());
			db_employee.setFname(employee.getFname());
			db_employee.setLname(employee.getLname());
			db_employee.setEmpId(employee.getEmpId());
			db_employee.setPhone(employee.getPhone());
			db_employee.setStatus(employee.getStatus());
			db_employee.setAddress(employee.getAddress());

			db_employee.setModified(new Date());
			employeeDao.updateEmployee(db_employee);
			return 1;

		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
	}

	/****
	 * forgotPassword
	 * 
	 * @param username
	 * @return
	 */
	@Override
	@Transactional
	public int forgotPassword(String username) {
		logger.info("forgotPassword called");
		try {
			EmployeeVO employeeVO = employeeDao.getEmployeeDetailsByUsername(username);
			if (employeeVO != null) {

				String password = employeeDao.getPasswordByUsername(username);
				if (!password.equals("") && password != null) {
					ExecutorService executor = Executors.newFixedThreadPool(1);
					// for (int i = 0; i < 10; i++) {
					Runnable worker = new SendMail(employeeVO.getEmail(), username, password, "Forgot Password");
					executor.execute(worker);
					// }
					executor.shutdown();
				}

				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception raised Here ====== >\n" + e);
			e.printStackTrace();
			return 0;
		}
	}

	/****
	 * getEmployeeList
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public List<EmployeeVO> getEmployeeList() {
		logger.info("getEmployeeList called");
		return employeeDao.getEmployeeList();
	}

	/*****
	 * getEmployeeById
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public EmployeeVO getEmployeeById(int id) {
		logger.info("getEmployeeById called");
		return employeeDao.getEmployeeDetailsById(id);
	}

	/*****
	 * 
	 * @param username
	 * @param currentPassword
	 * @param newPasswd
	 * @return
	 */
	@Override
	@Transactional
	public boolean changePassword(String username, String currentPassword, String newPasswd) {
		logger.info("changePassword called");
		boolean flag = false;
		try {
			EmployeeVO employeeVO = employeeDao.loginAuthentication(username, currentPassword);
			if (employeeVO != null) {
				int id = employeeVO.getId();
				flag = employeeDao.updateLoginDetails(id, newPasswd);
				if (flag) {
					ExecutorService executor = Executors.newFixedThreadPool(1);
					Runnable worker = new SendMail(employeeVO.getEmail(), username, newPasswd, "Password Changed");
					executor.execute(worker);
					executor.shutdown();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception raised Here ====== >\n" + e);
			e.printStackTrace();
		}
		return flag;
	}

	/****
	 * deleteEmployeeById
	 * 
	 * @param id
	 * @return
	 */

	@Override
	@Transactional
	public boolean deleteEmployeeById(int id) {
		logger.info("deleteEmployeeById called");
		try {
			int i = employeeDao.deleteEmployeeById(id);
			if (i > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*****
	 * insertBulkEmployeesFromFile
	 */
	@Override
	@Transactional
	public List<EmployeeVO> insertBulkEmployeesFromFile(List<EmployeeVO> employeeVOs) {
		List<EmployeeVO> responseList = new LinkedList<EmployeeVO>();
		try {
			for (EmployeeVO employeeVO : employeeVOs) {
				Employee employee = new Employee();
				employee.setFname(employeeVO.getFname());
				employee.setLname(employeeVO.getLname());
				employee.setEmail(employeeVO.getEmail());
				employee.setPhone(employeeVO.getPhone());
				employee.setEmpId(employeeVO.getEmpId());
				employee.setAddress(employeeVO.getAddress());
				employee.setStatus(Constants.ACTIVE);
				employee.setCreated(new Date());
				employee.setModified(new Date());
				employee.setDeleted(new Date());
				// InsertEmployeeResponse employeeResponse=new
				// InsertEmployeeResponse();
				// employees.add(employee);
				// employeeResponse.setEmail(employee.getEmail());
				int id = Integer.parseInt(this.insertSingleEmployeeFromFile(employee, employeeVO.getRole(),
						employeeVO.getDepartment(), employeeVO.getDivision(), employeeVO.getGroup()));
				employeeVO.setId(id);
				responseList.add(employeeVO);
			}
		} catch (Exception e) {
			logger.error("Exception raised Here ====== >\n" + e);
			// TODO rollback all
			e.printStackTrace();
		}

		return responseList;
	}

	@Override
	@Transactional
	public List<EmployeeVO> getEmployeeByLineManagerId(int lnmId) {
		logger.info("getEmployeeById called");
		return employeeDao.getEmployeeByLineManagerId(lnmId);
	}

	@Override
	@Transactional
	public List<EmployeeVO> getgrpDepDevsnByLinemngrId(int id) {
		try {
			EmployeeVO employeeVOs=employeeDao.getEmployeeDetailsById(id);
			if(employeeVOs!=null){
				System.out.println(employeeVOs.getFname());
				return employeeDao.getEmpByEmpId(employeeVOs.getId(), employeeVOs.getDepartmentId(), employeeVOs.getDivisionId(), employeeVOs.getGroupId(),true);
			}else{
				System.out.println("else called");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}

	@Override
	@Transactional
	public List<EmployeeVO> getEmpByEmpId(int empId,int depId,int divId,int groupId, boolean flag) {
		// TODO Auto-generated method stub
		return employeeDao.getEmpByEmpId(empId, depId, divId, groupId, flag);
	}
	@Override
	@Transactional
	public List<EmployeeVO> getLinemanagergpByDivDepId(int divId, int depId) {
		// TODO Auto-generated method stub
		return employeeDao.getLinemanagergpByDivDepId( divId,  depId);
	}
	@Override
	@Transactional
	public List<EmployeeVO> getEmpByDivDepIdGrpId(int divId, int depId,int grpId) {
		// TODO Auto-generated method stub
		return employeeDao.getEmpByDivDepIdGrpId( divId,  depId,grpId);
	}
	
	
	
/* (non-Javadoc)
 * @see com.techsource.mycheck.service.EmployeeService#updateUserName(int, java.lang.String)
 * just for testing purpose nt to use further
 */
@Override
@Transactional
public boolean updateUserName(int id, String userName ,String password) {
	// TODO Auto-generated method stub
	return employeeDao.updateUserName( id,  userName, password);
	
}
}
