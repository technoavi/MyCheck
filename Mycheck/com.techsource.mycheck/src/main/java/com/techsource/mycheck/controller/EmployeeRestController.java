package com.techsource.mycheck.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.service.EmployeeService;
import com.techsource.mycheck.service.TargetQuestionService;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.InsertEmployeeResponse;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Controller
@RequestMapping("/employee")
public class EmployeeRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TargetQuestionService targetQuestionService;

	@RequestMapping(value = "/getEmployeeList.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getEmployeeList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getEmployeeList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<EmployeeVO> employeeVOs = employeeService.getEmployeeList();
			String data = "{\"count\":" + employeeVOs.size() + ",\"data\":" + new Gson().toJson(employeeVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateEmployee.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateEmployee(@RequestParam("id") String id, @RequestParam("fname") String fname,
			@RequestParam("lname") String lname, @RequestParam("email") String email,
			@RequestParam("phone") String phone, @RequestParam("roleId") String roleId,
			@RequestParam("address") String address, @RequestParam("status") String status,
			@RequestParam("departmentId") String departmentId, @RequestParam("divisionId") String divisionId,
			@RequestParam("groupId") String groupId, @RequestParam("empId") String empId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("updateEmployee  started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(id));
			employee.setFname(fname);
			employee.setLname(lname);
			employee.setEmail(email);
			employee.setPhone(phone);
			employee.setEmpId(empId);
			employee.setAddress(address);
			employee.setStatus(status);
			// employee.setCreated(new Date());
			employee.setModified(new Date());
			// employee.setDeleted(new Date());

			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String responseData = Constants.EMPLOYEE_UPDATED_FAILURE;
			String statusCode = Constants.STATUS_CODE_FAILED;

			int i = employeeService.updateEmployee(employee, roleId, departmentId, divisionId, groupId);
			switch (i) {
			case 1:
				// update success info
				statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
				statusCode = Constants.STATUS_CODE_SUCCESS;
				responseData = Constants.EMPLOYEE_UPDATED_SUCCESS;
				break;
			case -1:
				responseData = Constants.EMPLOYEE_EMAIL_EXISTS;
				break;

			}

			result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
					+ "\",\"ResponseData\":\"" + responseData + "\"}";

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/****
	 * Not using the below method
	 * 
	 * @param fname
	 * @param lname
	 * @param email
	 * @param phone
	 * @param role
	 * @param address
	 * @param department
	 * @param division
	 * @param empId
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/addEmployee.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addEmployee(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("role") String role,
			@RequestParam("address") String address, @RequestParam("department") String department,
			@RequestParam("division") String division, @RequestParam("empId") String empId, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("addEmployee method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			Employee employee = new Employee();
			employee.setFname(fname);
			employee.setLname(lname);
			employee.setEmail(email);
			employee.setPhone(phone);
			employee.setEmpId(empId);
			employee.setAddress(address);
			employee.setStatus(Constants.ACTIVE);
			employee.setCreated(new Date());
			employee.setModified(new Date());
			employee.setDeleted(new Date());

			String responseData = employeeService.insertEmployee(employee, role, department, division);
			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String statusCode = Constants.STATUS_CODE_FAILED;
			if (responseData != null) {
				if (responseData.equals(Constants.EMPLOYEE_CREATED_SUCCESS)) {
					statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
					statusCode = Constants.STATUS_CODE_SUCCESS;
				} else if (responseData.equals(Constants.EMPLOYEE_EMAIL_EXISTS)) {
					statusCode = Constants.STATUS_CODE_FAILED;
				}
			}
			result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
					+ "\",\"ResponseData\":\"" + responseData + "\"}";

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/****
	 * Add bulk employess
	 * 
	 * @param jsonData
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/addBulkEmployees.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addBulkEmployees(@RequestParam("jsonData") String jsonData, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("addBulkEmployees method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<List<EmployeeVO>>() {
			}.getType();
			List<EmployeeVO> employeeVOs = new Gson().fromJson(jsonData, type);

			String responseData = null;
			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String statusCode = Constants.STATUS_CODE_FAILED;

			List<EmployeeVO> responsemap = employeeService.insertBulkEmployee(employeeVOs);
			if (responsemap.size() > 0) {
				statusCode = Constants.STATUS_CODE_SUCCESS;
				statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
				Type type2 = new TypeToken<List<InsertEmployeeResponse>>() {
				}.getType();
				responseData = gson.toJson(responsemap, type2);
			}

			result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
					+ "\",\"ResponseData\":" + responseData + "}";
			System.out.println(result);
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/****
	 * Employee login authentication
	 * 
	 * @param email
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/loginEmployee.htm", method = { RequestMethod.POST })
	public @ResponseBody String loginEmployee(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		logger.info("loginEmployee  started");

		// http://localhost:8080/finance-mis/user/loginUser.htm?email=RAJ.P912@GMAIL.COM&password=123
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			EmployeeVO employeeVO = employeeService.loginAuthentication(email, password);
			if (employeeVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVO) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_LOGIN_FAILURE + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/*****
	 * getEmployeeByEmail
	 * 
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEmployeeByEmail.htm", method = { RequestMethod.POST })
	public @ResponseBody String getEmployeeByEmail(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("loginEmployee  started");

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			EmployeeVO employeeVO = employeeService.getEmployeeByEmail(email);
			if (employeeVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVO) + "}";
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

	/*****
	 * getEmployeeById
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEmployeeById.htm", method = { RequestMethod.POST })
	public @ResponseBody String getEmployeeById(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getEmployeeById  started");

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			EmployeeVO employeeVO = employeeService.getEmployeeById(Integer.parseInt(id));
			if (employeeVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(employeeVO) + "}";
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

	/*****
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteEmployeeById.htm", method = { RequestMethod.POST })
	public @ResponseBody String deleteEmployeeById(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getEmployeeById  started");

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			boolean flag = employeeService.deleteEmployeeById(Integer.parseInt(id));
			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Employee Deleted Successfully.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/*****
	 * getEmployeeByEmail
	 * 
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword.htm", method = { RequestMethod.POST })
	public @ResponseBody String forgotPassword(@RequestParam("username") String username, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("loginEmployee  started");

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			int i = employeeService.forgotPassword(username);
			if (i > 0) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Mail Sent Successfully.\"}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"Some thing went wrong.Please check..!\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	// Max uploaded file size (here it is 20 MB)

	@RequestMapping(value = "/bulkEmployeesFromFile.htm", method = RequestMethod.POST)
	public @ResponseBody String bulkEmployeesFromFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "is_file", required = false) boolean isFile) {
		// Get name of uploaded file.

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			Gson gson = new GsonBuilder().create();

			String responseData = null;
			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String statusCode = Constants.STATUS_CODE_FAILED;

			String fileData = "";
			if (!isFile) {
				statusMessage = "failed";
				result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
						+ "\",\"ResponseData\":\"The specified file is not attached or is_file is false.\"}";
			} else {
				String contextName = request.getServletContext().getServletContextName();
				String realPath = request.getServletContext().getRealPath("/");
				realPath = realPath.substring(0, realPath.indexOf(contextName)) + "ROOT/files/";
				String urlpath = "http://" + request.getServerName() + ":" + request.getServerPort();
				MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multiPartRequest.getFile("file");
				byte[] bytes = file.getBytes();

				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss");
				// Append time stamp before the file name
				String fileName = dateFormat.format(date) + "-" + file.getOriginalFilename();
				fileData = urlpath + "/files/" + fileName;
				System.out.println(realPath + " -- " + urlpath + " --- " + fileData);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(realPath + fileName)));
				stream.write(bytes);
				stream.close();
				Workbook workbook = this.getWorkbook(realPath + fileName);
				if (workbook != null) {
					List<EmployeeVO> employeeVOs = this.readEmployeeVOsFromExcelFile(workbook);
					System.out.println("employeeVOs ::" + employeeVOs.size());
					List<EmployeeVO> responsemap = employeeService.insertBulkEmployeesFromFile(employeeVOs);
					if (responsemap.size() > 0) {
						statusCode = Constants.STATUS_CODE_SUCCESS;
						statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
						Type type2 = new TypeToken<List<InsertEmployeeResponse>>() {
						}.getType();
						responseData = gson.toJson(responsemap, type2);
					}

					result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
							+ "\",\"ResponseData\":" + responseData + "}";
					System.out.println(result);
				} else {
					result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
							+ "\",\"ResponseData\":\"The specified file is not Excel file\"}";
				}

			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}

	private Workbook getWorkbook(String excelFilePath) throws IOException {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(excelFilePath));
			if (excelFilePath.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (excelFilePath.endsWith("xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				// throw new IllegalArgumentException("The specified file is not
				// Excel file");
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return workbook;

	}

	private List<EmployeeVO> readEmployeeVOsFromExcelFile(Workbook workbook) throws IOException {
		List<EmployeeVO> listEmployeeVOs = new ArrayList<>();
		try {

			int numberOfSheets = workbook.getNumberOfSheets();
			DataFormatter objDefaultFormat = new DataFormatter();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> iterator = sheet.iterator();

				while (iterator.hasNext()) {
					Row nextRow = iterator.next();
					System.out.println("nextRow ::" + nextRow.getRowNum());
					if (nextRow.getRowNum() > 0) {
						// {"empId":"123","name":"jyo","email":"jyo33@gmail.com","phone":"9010209531","role":"EMPLOYEE","address":"H-No:13-3-123,Hyderabad","group":"Group1","department":"JAVA",}
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						EmployeeVO employeeVO = new EmployeeVO();

						while (cellIterator.hasNext()) {
							Cell nextCell = cellIterator.next();

							int columnIndex = nextCell.getColumnIndex();
							// System.out.println("columnIndex ::" +
							// columnIndex);
							switch (columnIndex) {
							case 0:
								String id = objDefaultFormat.formatCellValue(nextCell);
								// System.out.println("id ::"+id);
								employeeVO.setEmpId(id);
								break;
							case 1:
								// Fname
								employeeVO.setFname((String) getCellValue(nextCell));
								break;
							case 2:
								// Lname
								employeeVO.setLname((String) getCellValue(nextCell));
								break;
							case 3:
								//
								employeeVO.setEmail((String) getCellValue(nextCell));
								break;
							case 4:
								String phone = objDefaultFormat.formatCellValue(nextCell);
								employeeVO.setPhone(String.valueOf(phone));
								break;
							case 5:
								employeeVO.setRole((String) getCellValue(nextCell));
								break;

							case 6:
								employeeVO.setAddress((String) getCellValue(nextCell));
								break;
							case 7:
								// System.out.println("excel data ::"+(String)
								// getCellValue(nextCell));
								employeeVO.setGroup((String) getCellValue(nextCell));
								break;

							case 8:
								// System.out.println("excel data ::"+(String)
								// getCellValue(nextCell));
								employeeVO.setDepartment((String) getCellValue(nextCell));
								break;
							case 9:
								// System.out.println("excel data ::"+(String)
								// getCellValue(nextCell));
								employeeVO.setDivision((String) getCellValue(nextCell));
								break;

							}

						}
						listEmployeeVOs.add(employeeVO);
					}
				}
			}

			// workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listEmployeeVOs;
		} finally {
			if (workbook != null) {
				((FilterOutputStream) workbook).close();
			}
		}

		return listEmployeeVOs;
	}

	@RequestMapping(value = "/changePassword.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePassword(@RequestParam("username") String username,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went Wrong, Please provide valid details.\"}";
		try {
			boolean flg = employeeService.changePassword(username, currentPassword, newPassword);
			if (flg) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Password is changed sucessfully.\"}";

			}
			System.out.println("employees " + flg);
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}

	@RequestMapping(value = "/getQstnPrcntgByEmpIdAndqtrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getQstnPrcntgByEmpIdAndqtrId(@RequestParam("empId") String empId, @RequestParam("qtrId") String qtrId,HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		int percentage = 0;

		try {
			List<TargetQuestionVO> targetQuestions = targetQuestionService.getTargetQstnByEmpIdAndQtrId(empId, qtrId,false);

			if (targetQuestions != null) {
				System.out.println("targetQuestions size ::" + targetQuestions.size());
				int count = 0;
				for (TargetQuestionVO targetQuestionVO : targetQuestions) {
					System.out.println(targetQuestionVO.getId() + " --- " + targetQuestionVO.getStatus());
					if (targetQuestionVO.getStatus() == 1) {
						count++;
					}
				}
				percentage = (100 * count / targetQuestions.size());

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
	
	
	//just for testing
	@RequestMapping(value = "/updateUserName.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateUserName(@RequestParam("id") String id,
			@RequestParam("userName") String userName,@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went Wrong, Please provide valid details.\"}";
		try {
			boolean flg = employeeService.updateUserName(Integer.parseInt(id), userName,password);
			if (flg) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Username is changed sucessfully.\"}";

				System.out.println("employees account " + flg);
			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

}
