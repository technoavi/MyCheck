package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.RequestSurveyBoard;
import com.techsource.mycheck.vo.RequestSurveyRating;
import com.techsource.mycheck.vo.SurveySubmissionVO;
import com.techsource.mycheck.vo.SurveyVO;

public interface SurveyBoardService {

	public boolean insertsurveyBoard(RequestSurveyBoard requestSurveyBoard);

	public List<SurveyVO> getSurveyList();

	public SurveyVO getSurveyDetailsById(int id);

	public boolean updateSurveyBoard(RequestSurveyBoard requestSurveyBoard);

	public boolean updateSurveyStatusBySurveyId(int id);

	public boolean insertSurveyRating(RequestSurveyRating requestSurveyRating);

	public boolean checkSurveyNameExist(String name);

	public List<SurveyVO> getSurveyByLineMngrIdAndDate(int lineManagerId, String toDate, String fromDate);

	public boolean closingSurveyBySurveyId(int sid);

}
