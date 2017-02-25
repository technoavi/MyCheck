package com.techsource.mycheck.vo;

import com.techsource.mycheck.domain.Survey;

public class SurveySubmissionVO {
	private Integer id;
	private Survey survey;
	private Integer totalEmployee;
	private Integer participatedEmployee;
	private Integer unparticipatedEmp;
	private Integer participatedPerc;
	private String status;
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
	/**
	 * @return the totalEmployee
	 */
	public Integer getTotalEmployee() {
		return totalEmployee;
	}
	/**
	 * @param totalEmployee the totalEmployee to set
	 */
	public void setTotalEmployee(Integer totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	/**
	 * @return the participatedEmployee
	 */
	public Integer getParticipatedEmployee() {
		return participatedEmployee;
	}
	/**
	 * @param participatedEmployee the participatedEmployee to set
	 */
	public void setParticipatedEmployee(Integer participatedEmployee) {
		this.participatedEmployee = participatedEmployee;
	}
	/**
	 * @return the unparticipatedEmp
	 */
	public Integer getUnparticipatedEmp() {
		return unparticipatedEmp;
	}
	/**
	 * @param unparticipatedEmp the unparticipatedEmp to set
	 */
	public void setUnparticipatedEmp(Integer unparticipatedEmp) {
		this.unparticipatedEmp = unparticipatedEmp;
	}
	/**
	 * @return the participatedPerc
	 */
	public Integer getParticipatedPerc() {
		return participatedPerc;
	}
	/**
	 * @param participatedPerc the participatedPerc to set
	 */
	public void setParticipatedPerc(Integer participatedPerc) {
		this.participatedPerc = participatedPerc;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
