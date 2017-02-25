package com.techsource.mycheck.vo;

import java.util.Map;

public class RequestCommonSurveyRating {
	private int empId;
	private int  commonSurveyId;
	 private Map<Integer, Integer> commonSurveyQuestion;
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
	 * @return the commonSurveyId
	 */
	public int getCommonSurveyId() {
		return commonSurveyId;
	}
	/**
	 * @param commonSurveyId the commonSurveyId to set
	 */
	public void setCommonSurveyId(int commonSurveyId) {
		this.commonSurveyId = commonSurveyId;
	}
	/**
	 * @return the commonSurveyQuestion
	 */
	public Map<Integer, Integer> getCommonSurveyQuestion() {
		return commonSurveyQuestion;
	}
	/**
	 * @param commonSurveyQuestion the commonSurveyQuestion to set
	 */
	public void setCommonSurveyQuestion(Map<Integer, Integer> commonSurveyQuestion) {
		this.commonSurveyQuestion = commonSurveyQuestion;
	}

	 
}
