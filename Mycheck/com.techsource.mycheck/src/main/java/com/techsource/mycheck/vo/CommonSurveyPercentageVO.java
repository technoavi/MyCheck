package com.techsource.mycheck.vo;

import java.util.ArrayList;
import java.util.List;

public class CommonSurveyPercentageVO {
	private int commonSurveyId;
	private int totalEmployees;
	private int participateEmployees;
	private int overallPercentage;
	private List<CommonSurveyQuestionVO> surveyQuestions = new ArrayList<CommonSurveyQuestionVO>();
	
	public CommonSurveyPercentageVO() {
		// TODO Auto-generated constructor stub
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
	 * @return the totalEmployees
	 */
	public int getTotalEmployees() {
		return totalEmployees;
	}

	/**
	 * @param totalEmployees the totalEmployees to set
	 */
	public void setTotalEmployees(int totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	/**
	 * @return the participateEmployees
	 */
	public int getParticipateEmployees() {
		return participateEmployees;
	}

	/**
	 * @param participateEmployees the participateEmployees to set
	 */
	public void setParticipateEmployees(int participateEmployees) {
		this.participateEmployees = participateEmployees;
	}

	/**
	 * @return the overallPercentage
	 */
	public int getOverallPercentage() {
		return overallPercentage;
	}

	/**
	 * @param overallPercentage the overallPercentage to set
	 */
	public void setOverallPercentage(int overallPercentage) {
		this.overallPercentage = overallPercentage;
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
	
	
}
