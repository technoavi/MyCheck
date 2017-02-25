package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.CommonSurveyQuestions;
import com.techsource.mycheck.service.CommonSurveyQuestionEmployeeRelationVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;

public interface CommonSurveyQuestionDao {

	public boolean insertCommonSurveyQuestion(CommonSurveyQuestions commonSurveyQuestion);

	public CommonSurveyQuestions getRowById(int id);
	
	public boolean updateRow(CommonSurveyQuestions surveyQuestion) ;
	
	public List<CommonSurveyQuestionVO> getCommonSurveryQuestionsBySurveyId(int id);
	
	
	public boolean deleteCommonSurveyQuestionByQstnId(int id);
	
	public boolean addCommonSurveyQuestion(CommonSurveyQuestions question) ;
	
	public boolean updateCommonSurveyQuestionByCommonSurveyId(int id, String name);

	public List<CommonSurveyQuestionEmployeeRelationVO> getRatingsCommonSurveyQuestionId(int id);
	
}


