package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetboardTargetquestions;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

public interface TargetBoardDao {

	public int insertTargetBoard(TargetBoard targetBoard);

	public List<TargetBoardVO> getList();

	public TargetBoard getRowById(int id);

	public boolean updateRow(TargetBoard targetBoard);

	public int deleteRow(int id);

	public List<TargetBoardVO> getListByEmployeeId(String empId);

	public List<TargetBoardVO> getTargetByEmpIdAndQtrId(int empId, int qtrId);

	public List<TargetBoardVO> getTargetbyEmpIdAndYearAndQtr(int empid, String year, String qtr);

	public TargetBoardVO getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(int qtrId, int tgtBdId);

	public List<EmpQuaterTargetboard> getCopiedQuarterSourceDetails(int empId, int quarterId);

	public void persistObject(Object object);

	public boolean updateTargetBd(TargetBoard targetBoard);

	public TargetBoardVO getTargetByEmpIdAndQtrIds(int empId, int qtrId);

	public List<TargetBoardVO> getTargetBdByLnmngrId(String lnId);

	public List<TargetBoardVO> getTargetByEmpId(int empId);

	public List<TargetBoardVO> getRecentTargetBdByEmpId(int empId);

	public List<TargetBoard> getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(int empId, int qtrId);

	//public List<TargetQuestionVO> getTargetQstnByEmpId(int empId);

}
