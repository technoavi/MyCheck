package com.techsource.mycheck.service.report;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.techsource.mycheck.dao.TargetBoardDao;
import com.techsource.mycheck.dao.TargetQuestionDao;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	@Autowired
	private TargetBoardDao targetBoardDao;

	@Autowired
	private TargetQuestionDao targetQuestionDao;

	@Override
	@Transactional
	public DataTable getTargetBoardReportByEmpId(int empId,int qtrId) {
		logger.info("getReport called");
		List<TargetBoardVO> datas = targetBoardDao.getTargetByEmpIdAndQtrId(empId,qtrId);

		Type listType = new TypeToken<List<TargetBoardVO>>() {
		}.getType();
		System.out.println(new Gson().toJson(datas, listType));

		DataTable data = new DataTable();
		ArrayList<ColumnDescription> cd = new ArrayList<ColumnDescription>();
		cd.add(new ColumnDescription("Legend", ValueType.TEXT, "Legend"));
		cd.add(new ColumnDescription("Goals", ValueType.NUMBER, "Goals"));
		cd.add(new ColumnDescription("Id", ValueType.NUMBER, "Id"));
		cd.add(new ColumnDescription("Percenatge", ValueType.NUMBER, "Percenatge"));

		data.addColumns(cd);

		// Fill the data table.
		try { 
			for (TargetBoardVO targetBoardVO : datas) {
				List<TargetQuestionVO> targetQuestions = targetQuestionDao.getquestionsByTgtIdId(targetBoardVO.getId());

				int count = 0;
				for (TargetQuestionVO targetQuestionVO : targetQuestions) {
					if (targetQuestionVO.getStatus() == 1) {
						count++;
					}
				}
				int tq= targetQuestions.size();
				data.addRowFromValues(targetBoardVO.getName(),
						tq, targetBoardVO.getId(), (100 * count / targetQuestions.size()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
