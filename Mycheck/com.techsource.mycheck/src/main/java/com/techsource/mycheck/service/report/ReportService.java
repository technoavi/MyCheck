package com.techsource.mycheck.service.report;

import com.google.visualization.datasource.datatable.DataTable;

public interface ReportService {
	public DataTable getTargetBoardReportByEmpId(int empId,int qtrId);
}
