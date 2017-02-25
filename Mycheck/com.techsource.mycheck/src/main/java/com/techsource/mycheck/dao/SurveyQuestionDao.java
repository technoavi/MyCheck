package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.SurveyQuestion;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.SurveyResultVO;

public interface SurveyQuestionDao {

	public boolean insertSurveyQuestion(SurveyQuestion surveytQuestion);

	public SurveyQuestion getRowById(int id);

	public boolean updateRow(SurveyQuestion surveyQuestion);

	public boolean deleteQuestionById(int qstnId);

	public boolean addSurveyQuestion(SurveyQuestion surveyQuestion);

	public List<CommonSurveyQuestionVO> getSurveryQuestionsBySurveyId(int id);

	public boolean updateQuestionBySurveyId(int sid, String name);

	public List<CommonSurveyQuestionVO> getSurveryQuestionsByEmpId(int id);

	public List<SurveyResultVO> getRatingsSurveyQuestionId(int id);
}
