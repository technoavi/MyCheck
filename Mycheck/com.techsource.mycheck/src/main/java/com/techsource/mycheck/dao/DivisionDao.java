package com.techsource.mycheck.dao;

import java.util.List;

import com.techsource.mycheck.domain.Division;
import com.techsource.mycheck.vo.DivisionVO;

public interface DivisionDao {
	public int insertDivision(Division division);

	public Division getRowByDivision(String division);

	public List<DivisionVO> getList();

	public Division getRowById(int id);

	public int updateRow(Division division);

	public int deleteRow(int id);
}
