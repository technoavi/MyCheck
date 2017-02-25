package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.CommonSurvey;
import com.techsource.mycheck.domain.CommonSurveyEmployee;
import com.techsource.mycheck.domain.CommonSurveyQuestionEmployeeRelation;
import com.techsource.mycheck.domain.CommonSurveySubmission;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.EmployeeVO;

public interface CommonSurveyDao {

	public int insertCommonSurveyBoard(CommonSurvey surveyBoard);

	public CommonSurvey getRowById(int id);

	public boolean updateRow(CommonSurvey survey);

	public List<CommonSurveyVO> getCommonSurveyList();

	public boolean checkCommonSurveyNameExist(String name);

	public CommonSurveyVO getCommonSurveyDetailsById(int id);

	public boolean updateCommonSurveyStatusByCommonSurveyId(int id);

	public List<CommonSurveyVO> getCommonSurveyByEmpId(int id);

	public boolean insertCommonSurveyRating(CommonSurveyQuestionEmployeeRelation commonRating);

	public boolean insertCommonSurveyEmployee(CommonSurveyEmployee commonSurveyEmployee);

	public boolean updateCommonSurveyEmployee(CommonSurveyEmployee commonSurveyEmployee);

	public boolean checkCommonSurveyEmployeeParticipatedByEmpyId(int id);

	public List<CommonSurveyVO> getCommonSurveyByDate(String sdate, String edate);

	public List<EmployeeVO> getCommonSurveyEmpBySurveyId(int id);

	public List<CommonSurveyVO> getCommonSurveysByEmpId(int id);

	public List<CommonSurveyVO> getCommonSurveysByEmpyIdAlreadyparticipated(int eid);

	public CommonSurveyVO getCommonSurveyByNotParticipated(int id);

	public boolean insertCommonSurveySubmission(CommonSurveySubmission commonSurveySubmission);

}
