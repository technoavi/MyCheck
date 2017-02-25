package com.techsource.mycheck.service;

import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.domain.Employee;

public class CommonSurveyQuestionEmployeeRelationVO {
	private Integer id;
	private String name;
	private CommonSurvey commonSurvey;
	private CommonSurveyQuestions commonSurveyQuestions;
	private Employee employee;
	private Integer rating;
	private int surveyId;
	private int empId;
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
	 * @return the commonSurveyQuestions
	 */
	public CommonSurveyQuestions getCommonSurveyQuestions() {
		return commonSurveyQuestions;
	}
	/**
	 * @param commonSurveyQuestions the commonSurveyQuestions to set
	 */
	public void setCommonSurveyQuestions(CommonSurveyQuestions commonSurveyQuestions) {
		this.commonSurveyQuestions = commonSurveyQuestions;
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
	/**
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	/**
	 * @return the surveyId
	 */
	public int getSurveyId() {
		return surveyId;
	}
	/**
	 * @param surveyId the surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	/**
	 * @return the empId
	 */
	public int getEmpId() {
		return empId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	


}
