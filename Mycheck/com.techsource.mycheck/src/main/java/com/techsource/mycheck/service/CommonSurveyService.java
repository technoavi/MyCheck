package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.RequestCommonSurveyBoard;

public interface CommonSurveyService {
	public boolean insertCommonSurveyBoard(RequestCommonSurveyBoard requestCommonSurveyBoard);

	public boolean updateCommonSurveyBoard(RequestCommonSurveyBoard requestCommonSurveyBoard);

	public List<CommonSurveyVO> getCommonSurveyList();

	public boolean checkCommonSurveyNameExist(String name);

	public CommonSurveyVO getCommonSurveyDetailsById(int id);

	public List<CommonSurveyVO> getCommonSurveyByEmpId(int id);

	public List<CommonSurveyVO> getCommonSurveyByDate(String sdate, String edate);
	public boolean closingCommonSurveyBySurveyId(int sid);
}
