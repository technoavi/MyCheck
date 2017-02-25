package com.techsource.mycheck.vo;

import java.util.List;

public class LineManagerVO {
	
	private String name;
	private int id;
	List<EmployeeInfo> employees;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the employeeInfos
	 */
	public List<EmployeeInfo> getEmployees() {
		return employees;
	}

	/**
	 * @param employeeInfos
	 *            the employeeInfos to set
	 */
	public void setEmployeeInfos(List<EmployeeInfo> employees) {
		this.employees = employees;
	}

}
