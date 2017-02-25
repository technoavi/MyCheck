package com.techsource.mycheck.vo;

import java.util.List;

public class RequestEmailGroup {
	private int emailgroupId;
	private String emailGroupName;
	private int businessDivisionId;
	private int departmentId;
	private List<EmployeeInfo> employeeInfos;

	/**
	 * @return the id
	 */
	public int getEmailgroupId() {
		return emailgroupId;
	}

	/**
	 * @param id the id to set
	 */
	public void setEmailgroupId(int id) {
		this.emailgroupId = id;
	}

	/**
	 * @return the emailGroupName
	 */
	public String getEmailGroupName() {
		return emailGroupName;
	}

	/**
	 * @param emailGroupName
	 *            the emailGroupName to set
	 */
	public void setEmailGroupName(String emailGroupName) {
		this.emailGroupName = emailGroupName;
	}

	/**
	 * @return the businessDivisionId
	 */
	public int getBusinessDivisionId() {
		return businessDivisionId;
	}

	/**
	 * @param businessDivisionId
	 *            the businessDivisionId to set
	 */
	public void setBusinessDivisionId(int businessDivisionId) {
		this.businessDivisionId = businessDivisionId;
	}

	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the employeeInfos
	 */
	public List<EmployeeInfo> getEmployeeInfos() {
		return employeeInfos;
	}

	/**
	 * @param employeeInfos
	 *            the employeeInfos to set
	 */
	public void setEmployeeInfos(List<EmployeeInfo> employeeInfos) {
		this.employeeInfos = employeeInfos;
	}

}
