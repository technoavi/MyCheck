package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Survey;
import com.techsource.mycheck.domain.SurveyEmployee;
import com.techsource.mycheck.domain.SurveyResult;
import com.techsource.mycheck.domain.SurveySubmission;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.SurveyVO;

public interface SurveyDao {
	public int insertSurvey(Survey surveyBoard);

	public List<SurveyVO> getSurveyDetails();

	public SurveyVO getSurveyDetailsById(int id);

	public Survey getRowById(int id);

	public boolean updateRow(Survey survey);

	public boolean updateSurveyStatusBySurveyId(int id);

	public SurveyVO getSurveyIdByDepDivGrp(int depId, int divId, int grpId);

	public List<SurveyVO> getSurveyListByDepDivGrp(String toDate, String fromDate, int depId, int divId, int grpId);

	public boolean insertSurveyRating(SurveyResult surveyResult);

	public boolean checkSurvey(String surveyname);

	public boolean checkSurveyIdByDepDivGrp(int divId, int depId, int grpId);

	public boolean insertSurveyEmployee(SurveyEmployee surveyEmployee);

	public boolean checkEmployeeQuestion(int id);

	public List<SurveyVO> getSurveyBdByLnmngrIdAndDate(String empIds, String toDate, String fromDate);

	public SurveyVO getGrpDepDevsnBySurveyId(int id);

	public List<EmployeeVO> getSurveyEmpBySurveyId(int id);

	public boolean insertSurveySubmission(SurveySubmission submission);

}
