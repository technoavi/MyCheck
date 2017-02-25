package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.vo.RequestTargetBoard;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

public interface TargetBoardService {

	public boolean insertTargetBoard(RequestTargetBoard requestTargetBoard);

	// public List<TargetBoardVO> getListByYear(String year);

	public List<TargetBoardVO> getList();

	public List<TargetBoardVO> getListByEmployeeId(String empId);

	public TargetBoardVO getRowById(int id);

	public int updateRow(TargetBoardVO targetBoard);

	public int deleteRow(int id);

	public List<TargetBoardVO> getTargetByEmpIdAndQtrId(int empid, int qtrId);

	public List<TargetBoardVO> getTargetbyEmpIdAndYearAndQtr(int empid, String year, String qtr);

	public TargetBoardVO getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(int qtrId, int tgtBdId);

	public String copyTargetBoardNextQuarter(int empId, int qtrId);

	public boolean updateTargetQuestions(RequestTargetBoard requestTargetBoard);

	public boolean updateTargetQuestionsByTgtId(RequestTargetBoard requestTargetBoard);

	public boolean archiveSelectedTargetBoardByEmpIdAndQtrId(int empId, int qtrId);

	public List<TargetBoardVO> getTargetBdByLnmngrId(int lnId);

	public List<TargetBoardVO> getRecentTargetBdByEmpId(int empId);

	public List<TargetBoard> getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(int empId, int qtrId);



}
