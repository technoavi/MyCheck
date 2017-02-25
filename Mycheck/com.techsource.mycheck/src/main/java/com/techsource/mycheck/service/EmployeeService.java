package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.vo.EmployeeVO;

public interface EmployeeService {
	public String insertEmployee(Employee employee, String role, String department, String division);

	public int insertEmployee(Employee employee);

	public int forgotPassword(String username);

	public List<EmployeeVO> insertBulkEmployee(List<EmployeeVO> employeeVOs);

	public List<EmployeeVO> insertBulkEmployeesFromFile(List<EmployeeVO> employeeVOs);

	public String registerUser(String email, String password);

	public EmployeeVO loginAuthentication(String email, String password);

	public EmployeeVO getEmployeeByEmail(String email);

	public EmployeeVO getEmployeeById(int id);

	public boolean deleteEmployeeById(int id);

	public List<EmployeeVO> getEmployeeList();

	public boolean changePassword(String username, String currentPassword, String newPasswd);

	public int updateEmployee(Employee employee, String roleId, String departmentId, String divisionId, String groupId);

	public List<EmployeeVO> getEmployeeByLineManagerId(int lnmId);

	public List<EmployeeVO> getgrpDepDevsnByLinemngrId(int id);

	public List<EmployeeVO> getEmpByEmpId(int empId, int depId, int divId, int groupId, boolean flag);

	public List<EmployeeVO> getLinemanagergpByDivDepId(int divId, int depId);

	public List<EmployeeVO> getEmpByDivDepIdGrpId(int divId, int depId, int grpId);
	
	//just for testing
	public boolean updateUserName(int id, String userName, String password);

}
