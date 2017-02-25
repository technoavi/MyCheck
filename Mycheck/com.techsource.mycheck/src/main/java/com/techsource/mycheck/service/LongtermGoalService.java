package com.techsource.mycheck.service;

import java.util.List;

import com.techsource.mycheck.vo.LongTermGoalVO;

public interface LongtermGoalService {
	public boolean insertLongtermGoal(LongTermGoalVO  longTermGoal);
	
	public boolean updateLongTermGoal(LongTermGoalVO goalVO);
	
	public boolean deleteLongTermGoalById(int  id);
	
	public List<LongTermGoalVO> getlongtermGoalByEmpId(int eId);
	public LongTermGoalVO getLongTermGoalBygoalId(int gId);
}
