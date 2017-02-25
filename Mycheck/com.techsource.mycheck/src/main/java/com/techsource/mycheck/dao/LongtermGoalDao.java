/**
 * 
 */
package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.LongTermGoal;
import com.techsource.mycheck.vo.LongTermGoalVO;

/**
 * @author Avinash Srivastava
 *
 *         20-Oct-2016
 */
public interface LongtermGoalDao {

	public boolean insertLongtermGoal(LongTermGoal longTermGoal);

	public boolean updateLongTermGoal(LongTermGoal longTermGoal);

	public LongTermGoal getRowById(int id);

	public boolean deleteLongTermGoalById(int id);

	public List<LongTermGoalVO> getlongtermGoalByEmpId(int eId);

	public LongTermGoalVO getLongTermGoalBygoalId(int gId);

}
