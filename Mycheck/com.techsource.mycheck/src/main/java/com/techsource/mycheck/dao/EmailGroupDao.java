package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.EmailGroup;
import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.vo.EmailGroupVO;
import com.techsource.mycheck.vo.EmployeeInfo;
import com.techsource.mycheck.vo.EmployeeVO;

public interface EmailGroupDao {
	public EmailGroup getRowById(int id);

	public boolean insertEmailGroup(EmailGroup emailGroup);

	public List<EmailGroupVO> getEmailGroupList();

	public EmailGroupVO getEmailGroupDetailsById(int id);

	public Object[] getDivAndDeptById(int emailGroupId);

	public List<EmployeeInfo> getEmployeesListById(int id);

	public List<String> getEmployeeEmailsByEmpGrpId(int id);
	
	public List<String> getEmployeeEmailsByEmpId(int id);

	public boolean updateEmailGroup(EmailGroup emailGroup);

	public boolean checkEmailGroupExit(String name);

}
