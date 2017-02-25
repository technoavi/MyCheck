package com.techsource.mycheck.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.service.EmployeeService;
import com.techsource.mycheck.service.TargetBoardService;
import com.techsource.mycheck.service.TargetQuestionService;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.utility.SendMailWithAttachmnt;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestTargetBoard;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

@Controller
@RequestMapping("/lineManager")
public class LineManagerRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(LineManagerRestController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TargetBoardService targetBoardService;

	@Autowired
	TargetQuestionService targetQuestionService;

	@RequestMapping(value = "/getEmpByLnmngrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getEmpByLnmngrId(@RequestParam("lnmgrId") String lnmgrId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<EmployeeVO> employeeVOs = (List<EmployeeVO>) employeeService
					.getgrpDepDevsnByLinemngrId(Integer.parseInt(lnmgrId));
			if (employeeVOs != null && employeeVOs.size() > 0) {

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVOs) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Line Manager doesnt exist. Please check.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getTargetByLnmngrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetByLnmngrId(@RequestParam("lnmgrId") String lnmgrId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetBoardVO> employeeVOs = targetBoardService.getTargetBdByLnmngrId(Integer.parseInt(lnmgrId));
			if (employeeVOs != null) {

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVOs) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getRecentTargetByEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getRecentTargetByEmpId(@RequestParam("empId") String empId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetBoardVO> employeeVOs = targetBoardService.getRecentTargetBdByEmpId(Integer.parseInt(empId));
			if (employeeVOs != null) {

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVOs) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getQstnPrcntgByLnmngrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getQstnPrcntgByLnmngrId(@RequestParam("lnmgrId") String lnmgrId,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		int percentage = 0;

		try {
			List<TargetQuestionVO> targetQuestions = targetQuestionService.getTargetQstnByLnmngrId(lnmgrId, true);

			if (targetQuestions != null) {
				System.out.println("targetQuestions size ::" + targetQuestions.size());
				int count = 0;
				for (TargetQuestionVO targetQuestionVO : targetQuestions) {
					System.out.println(targetQuestionVO.getId() + " --- " + targetQuestionVO.getStatus());
					if (targetQuestionVO.getStatus() == 1) {
						count++;
					}
				}
				percentage = (10 * count / targetQuestions.size());

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"" + percentage
						+ "\"}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + percentage + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;

	}

	public List<RequestTargetBoard> getReportsByEmpIdAndqtrId(String empId, String qtrId) {

		List<TargetBoardVO> targetBoardVO = targetBoardService.getTargetByEmpIdAndQtrId(Integer.parseInt(empId),
				Integer.parseInt(qtrId));
		List<RequestTargetBoard> requestTargetBoards = new ArrayList<RequestTargetBoard>();
		if (targetBoardVO != null) {
			int qtrids = 0;

			for (TargetBoardVO targetBoardVO2 : targetBoardVO) {
				qtrids = targetBoardVO2.getId();
				List<TargetQuestionVO> targetQuestionVO3 = targetQuestionService.getTargetQstnbyTargetId(qtrids);

				if (targetQuestionVO3.size() > 0) {
					RequestTargetBoard requestTargetBoard = new RequestTargetBoard();
					requestTargetBoard.setEmpId(Integer.parseInt(empId));
					requestTargetBoard.setQuaterId(Integer.parseInt(qtrId));
					requestTargetBoard.setTargetBoard(targetBoardVO2);
					requestTargetBoard.setTargetQuestions(targetQuestionVO3);
					requestTargetBoards.add(requestTargetBoard);
				}
			}

		}
		return (List<RequestTargetBoard>) requestTargetBoards;
	}

	/*
	 * InputStream jasperStream = (InputStream)
	 * this.getClass().getResourceAsStream("/jasperreports/HelloWorld1.jasper");
	 * Map<String,Object> params = new HashMap<>(); JasperReport jasperReport =
	 * (JasperReport) JRLoader.loadObject(jasperStream); JasperPrint jasperPrint
	 * = JasperFillManager.fillReport(jasperReport, params, new
	 * JREmptyDataSource());
	 * 
	 * response.setContentType("application/x-pdf");
	 * response.setHeader("Content-disposition",
	 * "inline; filename=helloWorldReport.pdf");
	 * 
	 * final ServletOutputStream outStream = response.getOutputStream();
	 * JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	 */

	/*
	 * @Autowired protected DataSource localDataSource;
	 * 
	 * @RequestMapping(value = "/generateReprots.htm", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String getRpt1(@RequestParam("too") String
	 * too, @RequestParam("cname") String cname,HttpServletRequest request,
	 * HttpServletResponse response) throws JRException, IOException,
	 * SQLException {
	 * logger.debug("--------------generate PDF report----------"); String
	 * result=null; java.sql.Connection con = localDataSource.getConnection();
	 * try {
	 * 
	 * String path =
	 * "/home/avinash/Documents/mars2workspace/mycheck/src/main/resources/Employee.jrxml";
	 * FileInputStream input = new FileInputStream(new File(path));
	 * 
	 * JasperDesign jasperDesign = JRXmlLoader.load(input);
	 * 
	 * System.out.println("Compiling Report Designs"); JasperReport jasperReport
	 * = JasperCompileManager.compileReport(jasperDesign); String
	 * fileName="Employee"; File reportFile = new File(
	 * request.getSession().getServletContext().getRealPath("/jasper/" +
	 * fileName + ".jasper"));
	 * 
	 * if (!reportFile.exists()) {
	 * JasperCompileManager.compileReportToFile(request.getSession().
	 * getServletContext().getRealPath ("/jasper/" + fileName +
	 * ".jrxml"),request.getSession().getServletContext().getRealPath("/jasper/"
	 * + fileName + ".jasper")); } JasperReport jasperReport = (JasperReport)
	 * JRLoader.loadObjectFromFile(reportFile.getPath());
	 * 
	 * 
	 * System.out.println("Creating JasperPrint Object"); Map<String, Object>
	 * parameters = new HashMap<String, Object>(); parameters.put("name",
	 * "PDF JasperReport");
	 * 
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
	 * parameters, con ); // System.out.println("con is "+con.getCatalog()); //
	 * JasperViewer jasperViewer = new JasperViewer(jasperPrint); //
	 * jasperViewer.setVisible(true);
	 * 
	 * 
	 * 
	 * 
	 * File f = new File(request.getSession().getServletContext().getRealPath(
	 * "/WEB-INF/jasper/MyCheckReport.pdf")); f.createNewFile();
	 * System.out.println("pat is for "+f.getAbsolutePath()); // Exporting the
	 * report FileOutputStream output = new FileOutputStream(f);
	 * 
	 * JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	 * System.out.println("Report Generation Complete");
	 * SendMailWithAttachmnt.sendMailWithAttachmnt(too, cname,f, request);
	 * 
	 * result =
	 * "{\"StatusCode\":\"200\",\"StatusMessage\":\"Success\",\"ResponseData\":\"Report Generated\"}"
	 * ;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); result =
	 * "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\""
	 * + e.getMessage() + "\"}"; } return result; }// generatePdfReport
	 */
	@Autowired
	protected DataSource localDataSource;

	@RequestMapping(value = "/generateReprots.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRpt1(@RequestParam("too") String too, @RequestParam("cname") String cname,
			@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		logger.info("--------------generate PDF report----------");
		String result = null;
		ClassLoader loader = null;
		File file = null;
		File f = null;
		FileOutputStream output=null;
		JasperDesign jasperDesign = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		try {

			java.sql.Connection con = localDataSource.getConnection();
			logger.info("getting the conection frm datasource");
		

			loader = Thread.currentThread().getContextClassLoader();
			file = new File(loader.getResource("Employee.jrxml").getFile());

			jasperDesign = JRXmlLoader.load(file);

			logger.info("Compiling Report Designs");
			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			logger.info("Creating JasperPrint Object");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", name);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			logger.info("con is " + con.getCatalog());
			// JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			// jasperViewer.setVisible(true);

			f = new File(request.getSession().getServletContext().getRealPath("/WEB-INF/jasper/MyCheckReport.pdf"));
			f.createNewFile();
			// Exporting the report
			 output = new FileOutputStream(f);
			 logger.debug("the path of file is"+f);

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			logger.info("Report Generation Complete");
			SendMailWithAttachmnt.sendMailWithAttachmnt(too, cname, f, request);

			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"Success\",\"ResponseData\":\"Report Generated\"}";

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;
	}
}
