package com.techsource.mycheck.vo;

import com.techsource.mycheck.domain.Department;
import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.domain.Group;
import com.techsource.mycheck.domain.Survey;

public class SurveydeatailsVO {
	private Integer id;
	private Department department;
	private Division division;
	private Group group;
	private Survey survey;
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
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}
	/**
	 * @return the division
	 */
	public Division getDivision() {
		return division;
	}
	/**
	 * @param division the division to set
	 */
	public void setDivision(Division division) {
		this.division = division;
	}
	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}
	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
}
