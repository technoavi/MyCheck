package com.techsource.mycheck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.techsource.mycheck.dao.EmailGroupDao;
import com.techsource.mycheck.dao.EmailGroupEmployeeDao;
import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.domain.EmailGroup;
import com.techsource.mycheck.domain.EmailRelation;
import com.techsource.mycheck.domain.EmailgpEmployee;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.MailGroup;
import com.techsource.mycheck.vo.EmailGroupVO;
import com.techsource.mycheck.vo.EmployeeInfo;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.LineManagerVO;
import com.techsource.mycheck.vo.RequestEmailGroup;

@Service("emailGroupService")
public class EmailGroupServiceImpl implements EmailGroupService {
	private final Logger logger = LoggerFactory.getLogger(EmailGroupServiceImpl.class);
	@Autowired
	private EmailGroupEmployeeDao emailGroupEmployeeDao;
	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private EmailGroupDao emailGroupDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private EmployeeService employeeService;

	@Override
	@Transactional
	public boolean insertEmailGroup(RequestEmailGroup requestEmailGroup) {
		logger.info("into insertEmailGroup method");
		try {
			// Email group info
			EmailGroup emailGroup = new EmailGroup();
			boolean flag = emailGroupDao.checkEmailGroupExit(requestEmailGroup.getEmailGroupName());
			if (!flag) {
				emailGroup.setName(requestEmailGroup.getEmailGroupName());
				emailGroup.setStatus(Constants.ACTIVE);
				emailGroup.setCreated(new Date());
				emailGroup.setModified(new Date());
				emailGroup.setDeleted(new Date());
				emailGroupDao.insertEmailGroup(emailGroup);
				// email relation info
				int businessDivisionId = requestEmailGroup.getBusinessDivisionId();
				int departmentId = requestEmailGroup.getDepartmentId();

				Division division = divisionDao.getRowById(businessDivisionId);

				Department department = departmentDao.getRowById(departmentId);

				EmailRelation emailRelation = new EmailRelation();
				emailRelation.setDepartment(department);
				emailRelation.setDivision(division);
				emailRelation.setEmailGroup(emailGroup);

				emailRelation.setCreated(new Date());
				emailRelation.setModified(new Date());
				emailRelation.setDeleted(new Date());

				Set<EmailRelation> emailRelations = new HashSet<EmailRelation>();
				emailRelations.add(emailRelation);
				department.setEmailRelations(emailRelations);
				division.setEmailRelations(emailRelations);
				emailGroup.setEmailRelations(emailRelations);

				// employee - email_group relation

				List<EmployeeInfo> employeeInfos = requestEmailGroup.getEmployeeInfos();

				Set<EmailgpEmployee> emailGroupEmployees = new HashSet<EmailgpEmployee>();

				for (EmployeeInfo employeeInfo : employeeInfos) {

					if (employeeInfo.getState().equalsIgnoreCase("TRUE")) {
						EmailgpEmployee emailGroupEmployee = new EmailgpEmployee();
						Employee employee = employeeDao.getById(employeeInfo.getId());
						emailGroupEmployee.setEmployee(employee);
						emailGroupEmployee.setEmailGroup(emailGroup);
						emailGroupEmployee.setState("TRUE");

						emailGroupEmployee.setCreated(new Date());
						emailGroupEmployee.setModified(new Date());
						emailGroupEmployee.setDeleted(new Date());

						emailGroupEmployees.add(emailGroupEmployee);

						emailGroup.setEmailgpEmployees(emailGroupEmployees);
						employee.setEmailgpEmployees(emailGroupEmployees);

					}

				}
				return true;
			} else {
				
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	//	return false;
	}

	@Override
	@Transactional
	public List<EmailGroupVO> getEmailGroupList() {
		logger.info("getEmailGroupList called");
		return emailGroupDao.getEmailGroupList();
	}

	private EmployeeInfo getById(int id, List<EmployeeInfo> employeeInfos) {

		for (EmployeeInfo employeeInfo : employeeInfos) {
			if (employeeInfo.getId() == id) {
				return employeeInfo;
			}
		}
		return null;

	}

	@Override
	@Transactional
	public boolean sendEmailByEmpGrpId(int id, String subject, String msg) {
		logger.info("getEmpByEmpGrpId called");
		boolean flag = false;
		try {
			List<String> emails = emailGroupDao.getEmployeeEmailsByEmpGrpId(id);
			if (emails.size() > 0) {
				ExecutorService executor = Executors.newFixedThreadPool(1);
				Runnable worker = new MailGroup(emails, subject, msg);
				executor.execute(worker);
				executor.shutdown();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;

	}

	@Override
	@Transactional
	public EmailGroupVO getEmailGroupDetailsById(int id) {

		try {
			EmailGroupVO emailGroupVO = new EmailGroupVO();
			// 1. get bd and dep by emailgroupId
			Object[] datas = emailGroupDao.getDivAndDeptById(id);
			int divisionId = (int) datas[0];
			int departmentId = (int) datas[1];
			emailGroupVO.setDepartmentId(departmentId);
			emailGroupVO.setBusinessdivisionId(divisionId);

			System.out.println("divisionId ::" + divisionId + " departmentId ::" + departmentId);
			// 2.get list of employees based on the email_groupId

			List<EmployeeInfo> employeeInfos = emailGroupDao.getEmployeesListById(id);
			for (EmployeeInfo employeeInfo : employeeInfos) {
				System.out.println("employeeInfo ::" + employeeInfo.getId() + "" + employeeInfo.getState());
			}

			// 3.get all employees which are related to bd and dep
			List<LineManagerVO> lineManagers = new ArrayList<LineManagerVO>();
			List<EmployeeVO> LmemployeeVOs = employeeDao.getLinemanagergpByDivDepId(divisionId, departmentId);
			if (LmemployeeVOs.size() > 0) {

				for (EmployeeVO linemanagerVO : LmemployeeVOs) {

					LineManagerVO lineManager = new LineManagerVO();
					lineManager.setId(linemanagerVO.getId());
					lineManager.setName(linemanagerVO.getFname() + " " + linemanagerVO.getLname());
					List<EmployeeInfo> employeeInfos2 = new ArrayList<EmployeeInfo>();
					List<EmployeeVO> ememployeeVOs = employeeService.getgrpDepDevsnByLinemngrId(linemanagerVO.getId());
					for (EmployeeVO employeeVO : ememployeeVOs) {
						EmployeeInfo employeeInfo = new EmployeeInfo();
						employeeInfo.setId(employeeVO.getId());
						employeeInfo.setName(employeeVO.getFname() + " " + employeeVO.getLname());
						String state = "false";
						EmployeeInfo employeeInfo2 = this.getById(employeeVO.getId(), employeeInfos);
						if (employeeInfo2 != null) {
							state = employeeInfo2.getState();
						}
						employeeInfo.setState(state);
						employeeInfos2.add(employeeInfo);

					}
					if (employeeInfos2.size() > 0) {
						lineManager.setEmployeeInfos(employeeInfos2);
					}
					lineManagers.add(lineManager);

				}
				emailGroupVO.setData(lineManagers);
				return emailGroupVO;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 4. change the state of employees based on the emailgroup
		return null;
	}

	@Override
	@Transactional
	public boolean updateEmailGroup(RequestEmailGroup requestEmailGroup) {
		try {

			List<Object[]> emailGroupEmployee = emailGroupEmployeeDao
					.getEmpByEmailGroupId(requestEmailGroup.getEmailgroupId());

			Map<Integer, String> datas = new HashMap<Integer, String>();

			for (Object[] objects : emailGroupEmployee) {

				datas.put((Integer) objects[0], objects[1].toString());

			}
			System.out.println("emailgp id::" + datas);
			System.out.println("datas size::" + datas.size());
			//
			List<EmployeeInfo> updateEmployeesInfo = new ArrayList<>();
			List<EmployeeInfo> employeeInfos = requestEmailGroup.getEmployeeInfos();
			System.out.println("employeeInfos size::" + employeeInfos.size());
			for (EmployeeInfo employeeInfo : employeeInfos) {

				if (datas.containsKey(employeeInfo.getId())) {
					String state = datas.get(employeeInfo.getId()).toString();
					if (!employeeInfo.getState().equalsIgnoreCase(state)) {
						// update the state
						// employeeInfo.setState(state);
						updateEmployeesInfo.add(employeeInfo);
					}
				} else { // new employee found from request, not present in DB
							// insert the record, if status=TRUE
					if (employeeInfo.getState().equalsIgnoreCase("TRUE")) {
						// save the employee to emailemployeegroup
						updateEmployeesInfo.add(employeeInfo);
					}
				}

			}
			System.out.println("updateEmployeesInfo::" + updateEmployeesInfo.size());
			for (EmployeeInfo employeeInfo : updateEmployeesInfo) {
				EmailgpEmployee emailgpEmployee = emailGroupEmployeeDao
						.getRowByEmailGrpIdEmpId(requestEmailGroup.getEmailgroupId(), employeeInfo.getId());
				if (emailgpEmployee != null) { // update the record
					System.out.println(
							"emailgpEmployee found " + emailgpEmployee.getId() + " === " + emailgpEmployee.getState());
					emailgpEmployee.setState(employeeInfo.getState());
					emailgpEmployee.setModified(new Date());
					emailGroupEmployeeDao.updateEmailGroupEmployee(emailgpEmployee);
				} else { // insert the record
					System.out.println("emailgpEmployee not found " + employeeInfo.getId());
					EmailgpEmployee emailgpEmployee2 = new EmailgpEmployee();

					// 1. get employee by id

					Employee employee = employeeDao.getById(employeeInfo.getId());
					// 2. get emailgroup by id

					EmailGroup emailGroup = emailGroupDao.getRowById(requestEmailGroup.getEmailgroupId());

					Set<EmailgpEmployee> emailgpEmployees = new HashSet<EmailgpEmployee>();

					emailgpEmployee2.setEmailGroup(emailGroup);
					emailgpEmployee2.setEmployee(employee);
					emailgpEmployee2.setState(employeeInfo.getState());

					emailgpEmployee2.setCreated(new Date());
					emailgpEmployee2.setModified(new Date());
					emailgpEmployee2.setDeleted(new Date());

					emailgpEmployees.add(emailgpEmployee2);

					employee.setEmailgpEmployees(emailgpEmployees);
					emailGroup.setEmailgpEmployees(emailgpEmployees);

					emailGroupEmployeeDao.save(emailgpEmployee2);

				}

			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteEmailGroupByEmailGrpId(int id) {
		// TODO Auto-generated method stub
		return emailGroupEmployeeDao.deleteEmailGroupByEmailGrpId(id);
	}

	@Override
	@Transactional
	public boolean checkEmailGroupExit(String name) {
		// TODO Auto-generated method stub
		return emailGroupDao.checkEmailGroupExit(name);
	}
}
