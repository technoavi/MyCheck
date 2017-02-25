package com.techsource.mycheck.vo;

import java.util.List;

public class RequestTargetBoard {

	private int empId;
	private int quaterId;
	private TargetBoardVO targetBoard;
	private List<TargetQuestionVO> targetQuestions;

	public RequestTargetBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getQuaterId() {
		return quaterId;
	}

	public void setQuaterId(int quaterId) {
		this.quaterId = quaterId;
	}

	public TargetBoardVO getTargetBoard() {
		return targetBoard;
	}

	public void setTargetBoard(TargetBoardVO targetBoard) {
		this.targetBoard = targetBoard;
	}

	public List<TargetQuestionVO> getTargetQuestions() {
		return targetQuestions;
	}

	public void setTargetQuestions(List<TargetQuestionVO> targetQuestions) {
		this.targetQuestions = targetQuestions;
	}

}
