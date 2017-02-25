package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.EmailgpEmployee;

public interface EmailGroupEmployeeDao {
	public boolean updateEmailGroupEmployee(EmailgpEmployee emailgpEmployee);

	public List<Object[]> getEmpByEmailGroupId(int empGroupId);

	public EmailgpEmployee getRowById(int id);
	
	public EmailgpEmployee getRowByEmailGrpIdEmpId(int emailGrpId,int empId);

	public int save(EmailgpEmployee object);
	public boolean deleteEmailGroupByEmailGrpId(int id);

}
