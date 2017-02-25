package com.techsource.mycheck.vo;

import java.util.Map;

public class RequestSurveyRating {
	private int empId;
	private int  surveyId;
	 private Map<Integer, Integer>  surveyQuestion;
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
	 * @return the surveyQuestion
	 */
	/**
	 * @return the surveyQuestion
	 */
	public Map<Integer, Integer> getSurveyQuestion() {
		return surveyQuestion;
	}
	/**
	 * @param surveyQuestion the surveyQuestion to set
	 */
	public void setSurveyQuestion(Map<Integer, Integer> surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}
	
	 
}
