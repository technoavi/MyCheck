package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.RequestSurveyBoard;
import com.techsource.mycheck.vo.SurveyPercentageVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;

public interface SurveyQuestionService {
	public boolean deleteQuestionById(int id);

	public boolean addSurveyQuestion(RequestSurveyBoard requestSurveyBoard);

	public List<CommonSurveyQuestionVO> getSurveryQuestionsBySurveyId(int id);

	public boolean updateQuestionBySurveyId(int id, String name);

	public List<CommonSurveyQuestionVO> getSurveryQuestionsByEmpId(int id);

	public int getSurverIdByEmpId(int id);

	public SurveyPercentageVO getSurveryQuestionsPercentBySurveyId(int id);
}
