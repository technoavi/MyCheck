package com.techsource.mycheck.vo;

import java.util.List;

public class EmailGroupVO {
	private int id;
	private String name;
	private String status;
	private String department;
	private String businessdivision;
    private int departmentId;
    private int businessdivisionId;
	private List<EmployeeInfo> employeeInfos;
	private List<LineManagerVO> data ;
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the lineManagerVOs
	 */
	public List<LineManagerVO> getData() {
		return data;
	}

	/**
	 * @param lineManagerVOs the lineManagerVOs to set
	 */
	public void setData(List<LineManagerVO> lineManagerVOs) {
		this.data = lineManagerVOs;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the businessdivision
	 */
	public String getBusinessdivision() {
		return businessdivision;
	}

	/**
	 * @param businessdivision the businessdivision to set
	 */
	public void setBusinessdivision(String businessdivision) {
		this.businessdivision = businessdivision;
	}

	/**
	 * @return the departmentId
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the businessdivisionId
	 */
	public int getBusinessdivisionId() {
		return businessdivisionId;
	}

	/**
	 * @param businessdivisionId the businessdivisionId to set
	 */
	public void setBusinessdivisionId(int businessdivisionId) {
		this.businessdivisionId = businessdivisionId;
	}

	/**
	 * @return the employeeInfos
	 */
	public List<EmployeeInfo> getEmployeeInfos() {
		return employeeInfos;
	}

	/**
	 * @param employeeInfos the employeeInfos to set
	 */
	public void setEmployeeInfos(List<EmployeeInfo> employeeInfos) {
		this.employeeInfos = employeeInfos;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
}
