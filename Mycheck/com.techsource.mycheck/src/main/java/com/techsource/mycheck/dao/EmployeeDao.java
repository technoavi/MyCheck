package com.techsource.mycheck.dao;

import java.util.List;

import org.hibernate.Session;

import com.techsource.mycheck.domain.EmpLogin;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.vo.EmployeeVO;

public interface EmployeeDao {
	public int insertEmployee(Employee employee);

	public boolean checkEmailExists(String email);

	public String registerUser(String email, String password);

	public EmployeeVO loginAuthentication(String currentPassword, String password);

	public EmployeeVO getEmployeeByEmail(String email);

	public EmployeeVO getEmployeeDetailsByUsername(String username);

	public int updateEmployee(Employee employee);

	public String getPasswordByUsername(String username);

	public Employee getById(int id);

	public List<EmployeeVO> getEmployeeList();

	public EmployeeVO getEmployeeDetailsById(int id);

	public EmpLogin loginDetails(int id);

	public boolean updateLoginDetails(int id, String passwd);

	public EmpLogin insertEmployeeLoginDetails(EmpLogin empLogin);

	public int deleteEmployeeById(int id);

	public boolean checkEmpById(int empId);

	public List<EmployeeVO> getEmployeeByLineManagerId(int lnmId);

	public EmployeeVO getgrpDepDevsnByLinemngrId(int lnMgrId);

	public EmployeeVO getEmployeeDetailsByIdAndQtr(int id, int qtrId);

	public List<EmployeeVO> getEmpByEmpId(int empId, int depId, int divId, int groupId, boolean flag);

	public List<EmployeeVO> getLinemanagergpByDivDepId(int divId, int depId);

	public List<EmployeeVO> getEmpByDivDepIdGrpId(int divId, int depId, int grpId);

	public	List<EmployeeVO> totalEmployees();

	
	//just for testing
	public boolean updateUserName(int id, String userName, String password);

}
