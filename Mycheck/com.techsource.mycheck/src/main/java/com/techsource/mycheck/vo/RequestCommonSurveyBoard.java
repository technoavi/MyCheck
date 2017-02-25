package com.techsource.mycheck.vo;

import java.util.List;

public class RequestCommonSurveyBoard {
	private int empId;

	private CommonSurveyVO commonsurvey;
	private List<CommonSurveyQuestionVO> commonSurveyQuestion;
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
	 * @return the commonsurvey
	 */
	public CommonSurveyVO getCommonsurvey() {
		return commonsurvey;
	}
	/**
	 * @param commonsurvey the commonsurvey to set
	 */
	public void setCommonsurvey(CommonSurveyVO commonsurvey) {
		this.commonsurvey = commonsurvey;
	}
	/**
	 * @return the commonSurveyQuestion
	 */
	public List<CommonSurveyQuestionVO> getCommonSurveyQuestion() {
		return commonSurveyQuestion;
	}
	/**
	 * @param commonSurveyQuestion the commonSurveyQuestion to set
	 */
	public void setCommonSurveyQuestion(List<CommonSurveyQuestionVO> commonSurveyQuestion) {
		this.commonSurveyQuestion = commonSurveyQuestion;
	}

	
	
	
}
