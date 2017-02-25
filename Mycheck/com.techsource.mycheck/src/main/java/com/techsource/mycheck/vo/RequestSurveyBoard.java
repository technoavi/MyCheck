package com.techsource.mycheck.vo;

import java.util.List;

import com.techsource.mycheck.domain.Survey;

public class RequestSurveyBoard {

	private int empId;
	private int group;
	private int businessDivision;
	private int department;
	private SurveyVO survey;
	private List<CommonSurveyQuestionVO> surveyQuestions;
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
	/**
	 * @return the group
	 */
	public int getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(int group) {
		this.group = group;
	}
	/**
	 * @return the businessDivision
	 */
	public int getBusinessDivision() {
		return businessDivision;
	}
	/**
	 * @param businessDivision the businessDivision to set
	 */
	public void setBusinessDivision(int businessDivision) {
		this.businessDivision = businessDivision;
	}
	/**
	 * @return the department
	 */
	public int getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(int department) {
		this.department = department;
	}
	/**
	 * @return the survey
	 */
	public SurveyVO getSurvey() {
		return survey;
	}
	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(SurveyVO survey) {
		this.survey = survey;
	}
	/**
	 * @return the surveyQuestions
	 */
	public List<CommonSurveyQuestionVO> getSurveyQuestions() {
		return surveyQuestions;
	}
	/**
	 * @param surveyQuestions the surveyQuestions to set
	 */
	public void setSurveyQuestions(List<CommonSurveyQuestionVO> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestSurveyBoard [empId=" + empId + ", group=" + group + ", businessDivision=" + businessDivision
				+ ", department=" + department + ", survey=" + survey + ", surveyQuestions=" + surveyQuestions + "]";
	}
	

}
