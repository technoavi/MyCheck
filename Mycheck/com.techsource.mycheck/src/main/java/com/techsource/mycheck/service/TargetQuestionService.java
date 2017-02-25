package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.vo.TargetQuestionVO;

public interface TargetQuestionService {

	public boolean insertTargetQuestion(TargetQuestions targetQuestion);

	public boolean updateTargetQuestion(TargetQuestions targetQuestion);
 public boolean updateTargetQstnStatus(int qstnId,String status);

	public List<TargetQuestionVO> getList();

	public List<TargetQuestionVO> getListByTargetBoardId(String targetBoardId);

	public TargetQuestionVO getRowById(int id);

	public int updateRow(TargetQuestionVO targetQuestion);

	public int deleteRow(int id);

	public List<TargetQuestionVO> getTargetQstnbyEmpId(int id);

	public List<TargetQuestionVO> getTargetQstnbyTargetId(int id);

	public List<TargetQuestionVO> getTargetQstnByQtrId(int id);

	public List<TargetQuestionVO> getquestionsByTgtIdId(int tgtBdId);

	public boolean checkQstnIdExists(int qstId);

	public List<TargetQuestions> addTargetquestions(TargetQuestionVO targetQuestionVO);

	public boolean deleteQuestionById(int qstnId);

	public List<TargetQuestionVO> getTargetQstnByLnmngrId(String lnId,boolean flag);
	public List<TargetQuestionVO> getTargetQstnByEmpIdAndQtrId(String lnId, String qtrId,boolean flag) ;

}
