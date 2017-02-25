package com.techsource.mycheck.service;

import java.util.List;


import com.techsource.mycheck.vo.CommonSurveyPercentageVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.RequestCommonSurveyBoard;
import com.techsource.mycheck.vo.RequestCommonSurveyRating;

public interface CommonSurveyQuestionService {
	List<CommonSurveyQuestionVO> getCommonSurveryQuestionsBySurveyId(int id);

	public boolean deleteQuestionByQstnId(int id);

	public boolean updateCommonSurveyStatusByCommonSurveyId(int id);

	public boolean addCommonSurveyQuestion(RequestCommonSurveyBoard requestSurveyBoard);

	public boolean updateCommonSurveyQuestionByCommonSurveyId(int id, String name);

	List<CommonSurveyQuestionVO> getCommonSurveyQuestionBySurveyId(int id, int eid);

	public boolean insertCommonSurveyRating(RequestCommonSurveyRating requestSurveyRating);

	public CommonSurveyPercentageVO getCommonSurveryQuestionsPercentBySurveyId(int id);

}
