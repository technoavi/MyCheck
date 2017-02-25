package com.techsource.mycheck.vo;

import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.Employee;

public class CommonSurveyEmployeeVO {
	private Integer id;
	private CommonSurvey commonSurvey;
	private Employee employee;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the commonSurvey
	 */
	public CommonSurvey getCommonSurvey() {
		return commonSurvey;
	}
	/**
	 * @param commonSurvey the commonSurvey to set
	 */
	public void setCommonSurvey(CommonSurvey commonSurvey) {
		this.commonSurvey = commonSurvey;
	}
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
