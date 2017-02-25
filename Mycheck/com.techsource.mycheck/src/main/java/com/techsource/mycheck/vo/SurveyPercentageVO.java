package com.techsource.mycheck.vo;

import java.util.ArrayList;
import java.util.List;


public class SurveyPercentageVO {

	private int surveyId;
	private int totalEmployees;
	private int participateEmployees;
	private String overallPercentage;
	private List<CommonSurveyQuestionVO> surveyQuestions = new ArrayList<CommonSurveyQuestionVO>();

	/**
	 * @return the surveyId
	 */
	public int getSurveyId() {
		return surveyId;
	}

	/**
	 * @param surveyId
	 *            the surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * @return the totalEmployees
	 */
	public int getTotalEmployees() {
		return totalEmployees;
	}

	/**
	 * @param totalEmployees
	 *            the totalEmployees to set
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
	 * @param participateEmployees
	 *            the participateEmployees to set
	 */
	public void setParticipateEmployees(int participateEmployees) {
		this.participateEmployees = participateEmployees;
	}

	/**
	 * @return the overallPercentage
	 */
	public String getOverallPercentage() {
		return overallPercentage;
	}

	/**
	 * @param overallPercentage
	 *            the overallPercentage to set
	 */
	public void setOverallPercentage(String overallPercentage) {
		this.overallPercentage = overallPercentage;
	}

	/**
	 * @return the surveyQuestions
	 */
	public List<CommonSurveyQuestionVO> getSurveyQuestions() {
		return surveyQuestions;
	}

	/**
	 * @param surveyQuestions
	 *            the surveyQuestions to set
	 */
	public void setSurveyQuestions(List<CommonSurveyQuestionVO> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

}
