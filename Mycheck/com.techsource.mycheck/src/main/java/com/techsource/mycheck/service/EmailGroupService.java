package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.EmailGroupVO;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestEmailGroup;

public interface EmailGroupService {
	public boolean insertEmailGroup(RequestEmailGroup requestEmailGroup);

	public List<EmailGroupVO> getEmailGroupList();

	public EmailGroupVO getEmailGroupDetailsById(int id);
	public boolean sendEmailByEmpGrpId(int id,String subject,String msg);
	public boolean updateEmailGroup(RequestEmailGroup requestEmailGroup);
	public boolean deleteEmailGroupByEmailGrpId(int id);
	public boolean checkEmailGroupExit(String name);
}
