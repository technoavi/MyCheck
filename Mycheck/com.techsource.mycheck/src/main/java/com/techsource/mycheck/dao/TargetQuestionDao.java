package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.vo.TargetQuestionVO;

public interface TargetQuestionDao {

	public boolean insertTargetQuestions(TargetQuestions targetQuestions);

	public boolean updateTargetQuestions(TargetQuestions targetQuestionVo);
	public boolean updateStatus(int qtnId,byte status);

	public List<TargetQuestionVO> getList();

	public TargetQuestions getRowById(int id);

	public int updateRow(TargetQuestions targetQuestions);

	public int deleteRow(int id);

	public List<TargetQuestionVO> getListByTargetBoardId(String tbId);

	public List<TargetQuestionVO> getTargetQstnbyEmpId(int id);

	public List<TargetQuestionVO> getTargetQstnbyTargetId(int id);

	public List<TargetQuestionVO> getTargetQstnByQtrId(int id);

	public List<TargetQuestionVO> getquestionsByTgtIdId(int tgtBdId);

	public boolean checkQstnIdExists(int qstId);

	public boolean deleteQuestionById(int qstnId);

	public List<TargetQuestionVO> getTargetQstnByEmpIdAndQtrId(int empId, int qtrId);
	public List<TargetQuestionVO> getTargetQstnByLnmngrId(String lnId);

}
