package com.techsource.mycheck.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.dao.SurveyDao;
import com.techsource.mycheck.service.EmployeeService;
import com.techsource.mycheck.service.SurveyBoardService;
import com.techsource.mycheck.service.SurveyQuestionService;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.EmployeeInfo;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.LineManagerVO;
import com.techsource.mycheck.vo.RequestSurveyBoard;
import com.techsource.mycheck.vo.RequestSurveyRating;
import com.techsource.mycheck.vo.SurveyPercentageVO;
import com.techsource.mycheck.vo.SurveySubmissionVO;
import com.techsource.mycheck.vo.SurveyVO;

@Controller
@RequestMapping("/survey")
public class SurveyRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(SurveyRestController.class);
	@Autowired
	SurveyBoardService surveyBoardService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	SurveyQuestionService surveyQuestionService;
 
   
	@RequestMapping(value = "/createSurvey.htm", method = RequestMethod.POST)
	public @ResponseBody String createSurvey(@RequestParam("surveyData") String surveyData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("into createsurvey()");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestSurveyBoard requestSurveyBoard = this.prepareJSonObject(surveyData, false);
			if (requestSurveyBoard != null) {
				boolean flag = surveyBoardService.insertsurveyBoard(requestSurveyBoard);
				logger.debug("flag " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Survey Created successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateSurvey.htm", method = RequestMethod.POST)
	public @ResponseBody String updateSurvey(@RequestParam("surveyData") String surveyData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertTargetBoard method started");
		System.out.println("updated json ::" + surveyData);
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestSurveyBoard requestSurveyBoard = this.prepareJSonObject(surveyData, true);
			if (requestSurveyBoard != null) {
				boolean flag = surveyBoardService.updateSurveyBoard(requestSurveyBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Target questions updated successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;

	}
	List<String>  list=null;

	@RequestMapping(value = "/getSurveyList.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurvey(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<SurveyVO> surveyVOs = surveyBoardService.getSurveyList();
			String data = "{\"count\":" + surveyVOs.size() + ",\"data\":" + new Gson().toJson(surveyVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	/*for checking the employye qstnh  list exist in survey or nt
	 * @RequestMapping(value = "/getSurveyList1.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurvey1(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = surveyDao.checkEmployeeQuestion(Integer.parseInt(id));
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + flag + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}*/
	@RequestMapping(value = "/checkSurveyName.htm", method = RequestMethod.POST)
	public @ResponseBody String checkSurveyName(@RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = surveyBoardService.checkSurveyNameExist(name);
			if (flag) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Survey already Exist Please try other!!! .\"}";
			}
			else {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Ok.\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}
	@RequestMapping(value = "/updateQuestionBySurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String updateQuestionBySurveyId(@RequestParam("id") String id,
			@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		boolean flag = false;
		try {
			flag = surveyQuestionService.updateQuestionBySurveyId(Integer.parseInt(id), name);
			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Survey questions added successfully.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateSurveyStatusBySurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String updateSurveyStatusBySurveyId(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		boolean flag = false;
		try {
			flag = surveyBoardService.updateSurveyStatusBySurveyId(Integer.parseInt(id));
			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"SurveyBoard Status updated successfully.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/deleteSurveyQuestionsByQstnId.htm", method = RequestMethod.POST)
	public @ResponseBody String deleteSurveyQuestionsByQstnId(@RequestParam("qstnId") String qstnId, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("deleteSurveyQuestions method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = surveyQuestionService.deleteQuestionById(Integer.parseInt(qstnId));
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + flag + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/addSurveyQuestions.htm", method = RequestMethod.POST)
	public @ResponseBody String addSurveyQuestions(@RequestParam("surveyData") String surveyData,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";

		try {
			RequestSurveyBoard requestSurveyBoard = this.surveyQstnJSonObject1(surveyData);
			if (requestSurveyBoard != null) {
				boolean flag = surveyQuestionService.addSurveyQuestion(requestSurveyBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Survey questions added successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}

	@RequestMapping(value = "/getSurveyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurveyDetailsById(@RequestParam("surveyId") String surveyId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			RequestSurveyBoard requestSurveyBoard = new RequestSurveyBoard();
			SurveyVO surveyVOs = surveyBoardService.getSurveyDetailsById(Integer.parseInt(surveyId));
			if (surveyVOs != null) {
				requestSurveyBoard.setSurvey(surveyVOs);
				
				List<CommonSurveyQuestionVO> surveyQuestions = surveyQuestionService.getSurveryQuestionsBySurveyId(surveyVOs.getId());
				if (surveyQuestions.size() > 0) {
					requestSurveyBoard.setSurveyQuestions(surveyQuestions);
				}
			}

			String data = "{\"count\":1,\"data\":" + new Gson().toJson(requestSurveyBoard) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getSurveyQuestionById.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurveyQuestionById(@RequestParam("surveyId") String surveyId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			// SurveyVO surveyVOs =
			// surveyBoardService.getSurveyDetailsById(Integer.parseInt(surveyId));
			List<CommonSurveyQuestionVO> questionVOs = surveyQuestionService
					.getSurveryQuestionsBySurveyId(Integer.parseInt(surveyId));
			String data = "{\"count\":" + questionVOs.size() + ",\"data\":" + new Gson().toJson(questionVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	public RequestSurveyBoard surveyQstnJSonObject1(String jsonData) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestSurveyBoard reqSurveyBd = new RequestSurveyBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			reqSurveyBd.setEmpId(eId);

			JSONObject jsonObject2 = jsonObject.getJSONObject("surveyBoard");
			SurveyVO surveyVO = new SurveyVO();
			int targetId = jsonObject2.getInt("id");
			surveyVO.setId(targetId);
			reqSurveyBd.setSurvey(surveyVO);
			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("surveyQuestions");
			List<CommonSurveyQuestionVO> datas = new LinkedList<CommonSurveyQuestionVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				CommonSurveyQuestionVO surveyQstns = new CommonSurveyQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				surveyQstns.setId(jsonObject3.getInt("id"));
				String q_name = jsonObject3.get("name").toString();
				surveyQstns.setName(q_name);
				Integer rating = jsonObject3.getInt("rating");
				// surveyQstns.setDescription(jsonObject3.getString("description"));
				surveyQstns.setRating(rating);
				/*
				 * Object object = jsonObject3.get("status"); int statusFlag =
				 * 0;
				 * 
				 * if (object instanceof Boolean) { Boolean status =
				 * jsonObject3.getBoolean("status"); if (status) { statusFlag =
				 * 1; } System.out.println("Boolean called"); } else if (object
				 * instanceof Integer) { int status =
				 * jsonObject3.getInt("status"); if (status == 1) { statusFlag =
				 * 1; } System.out.println("Integer called"); }
				 * System.out.println("statusFlag ::" + statusFlag);
				 */
				// surveyQstns.setStatus((byte) statusFlag);
				datas.add(surveyQstns);

			}
			reqSurveyBd.setSurveyQuestions(datas);
			return reqSurveyBd;
		} catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public RequestSurveyBoard prepareJSonObject(String jsonData, boolean updateFlag) throws ParseException {
		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestSurveyBoard reqsurveyBoard = new RequestSurveyBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			reqsurveyBoard.setEmpId(eId);
			System.out.println("id is " + eId);

			JSONObject jsonObject2 = jsonObject.getJSONObject("surveyBoard");
			SurveyVO surveyVO = new SurveyVO();
			String name = jsonObject2.get("name").toString();
			surveyVO.setName(name);
			if (updateFlag) {
				int surveyid = jsonObject2.getInt("id");
				surveyVO.setId(surveyid);
			}
			int departmentId = Integer.parseInt(jsonObject2.get("department").toString());
			reqsurveyBoard.setDepartment(departmentId);

			int divId = Integer.parseInt(jsonObject2.get("businessDivision").toString());
			reqsurveyBoard.setBusinessDivision(divId);

			int gpId = Integer.parseInt(jsonObject2.get("group").toString());
			reqsurveyBoard.setGroup(gpId);
			String description = jsonObject2.get("description").toString();
			surveyVO.setDescription(description);
			String startdate = jsonObject2.get("startdate").toString();

			surveyVO.setStartDate(df.parse(startdate));
			String enddate = jsonObject2.get("enddate").toString();
			// DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			surveyVO.setEndDate(df.parse(enddate));
			surveyVO.setState("OPEN");
			reqsurveyBoard.setSurvey(surveyVO);

			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("surveyQuestions");
			List<CommonSurveyQuestionVO> datas = new LinkedList<CommonSurveyQuestionVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				CommonSurveyQuestionVO surveyQuestions = new CommonSurveyQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				surveyQuestions.setId(jsonObject3.getInt("id"));
				int rating = jsonObject3.getInt("rating");
				surveyQuestions.setRating(rating);
				String q_name = jsonObject3.get("name").toString();
				surveyQuestions.setName(q_name);
				// surveyQuestions.setStatus("OPEN");
				/*
				 * Object object = jsonObject3.get("status"); int statusFlag =
				 * 0;
				 * 
				 * if (object instanceof Boolean) { Boolean status =
				 * jsonObject3.getBoolean("status"); if (status) { statusFlag =
				 * 1; } System.out.println("Boolean called"); } else if (object
				 * instanceof Integer) { int status =
				 * jsonObject3.getInt("status"); if (status == 1) { statusFlag =
				 * 1; } System.out.println("Integer called"); }
				 * System.out.println("statusFlag ::" + statusFlag);
				 * surveyQuestions.setStatus(statusFlag);
				 */
				datas.add(surveyQuestions);

			}
			reqsurveyBoard.setSurveyQuestions(datas);
			return reqsurveyBoard;
		} catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	

	

	@RequestMapping(value = "/getLineManagerGroups.htm", method = RequestMethod.POST)
	public @ResponseBody String getLineManagerGroups(@RequestParam("divId") String divId,
			@RequestParam("depId") String depId, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<EmployeeVO> LmemployeeVOs = employeeService.getLinemanagergpByDivDepId(Integer.parseInt(divId),
					Integer.parseInt(depId));
			List<LineManagerVO> lineManagers=new ArrayList<LineManagerVO>();
			if (LmemployeeVOs.size() > 0) {

				for (EmployeeVO linemanagerVO : LmemployeeVOs) {

					LineManagerVO lineManager = new LineManagerVO();
					lineManager.setId(linemanagerVO.getId());
					lineManager.setName(linemanagerVO.getFname() + " " + linemanagerVO.getLname());
					List<EmployeeInfo> employeeInfos=new ArrayList<EmployeeInfo>();
					List<EmployeeVO> ememployeeVOs = employeeService.getgrpDepDevsnByLinemngrId(linemanagerVO.getId());
					for (EmployeeVO employeeVO : ememployeeVOs) {
						EmployeeInfo employeeInfo = new EmployeeInfo();
						employeeInfo.setId(employeeVO.getId());
						employeeInfo.setName(employeeVO.getFname() + " " + employeeVO.getLname());
						employeeInfo.setState("false");
						employeeInfos.add(employeeInfo);

					}
					if(employeeInfos.size()>0){
						lineManager.setEmployeeInfos(employeeInfos);
					}
					lineManagers.add(lineManager);

				}
			}

			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(lineManagers) + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getSurveyQuestionByEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurveyQuestionByEmpId(@RequestParam("empId") String empId,
			HttpServletRequest request, HttpServletResponse response) 
 {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		//int surveyId=1;
		try {
			int surveyId=	surveyQuestionService.getSurverIdByEmpId(Integer.parseInt(empId));
			System.out.println("survey id "+surveyId);
			List<CommonSurveyQuestionVO> questionVOs = surveyQuestionService.getSurveryQuestionsByEmpId(Integer.parseInt(empId));
			
			if(questionVOs!=null){
		result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\": { \"empId\":" + empId + 
					 ",\"surveyId\":" + surveyId +",\"surveyQuestions\":" + new Gson().toJson(questionVOs) + "} }";
		}
			
			else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"  You have already participated in the survey. \"}";
			}
			
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\" You do not have the Survey Questions. Check with Admin. \"}";
		}

		return result;

	}
	@RequestMapping(value = "/surveyRating.htm", method = RequestMethod.POST)
	public @ResponseBody String surveyRating(@RequestParam("surveyData") String surveyData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {

			RequestSurveyRating requestSurveyRating = this.prepareJSonObjectForSurveyRating(surveyData);
			if (requestSurveyRating != null) {

				boolean flag = surveyBoardService.insertSurveyRating(requestSurveyRating);
				logger.debug("flag " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"SurveyRating inserted successfully.\"}";
				}
			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;
		
}
		
		public RequestSurveyRating prepareJSonObjectForSurveyRating(String jsonData) {
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				RequestSurveyRating requestSurveyrating = new RequestSurveyRating();

				int eId = Integer.parseInt(jsonObject.get("empId").toString());
				requestSurveyrating.setEmpId(eId);
				int sId = Integer.parseInt(jsonObject.get("surveyId").toString());
				requestSurveyrating.setSurveyId(sId);

				
				Map<Integer, Integer> map= new HashMap<Integer, Integer>();
				JSONArray jsonArray = jsonObject.getJSONArray("surveyQuestions");
				for (int i = 0; i < jsonArray.length(); i++) {
					
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					
					//Map<Integer, String >map2 = new HashMap<Integer, String>();
					int q_name = jsonObject2.getInt("rating");
					map.put(jsonObject2.getInt("id"), q_name);
				}
				requestSurveyrating.setSurveyQuestion(map);
				return requestSurveyrating;
			} catch (NumberFormatException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		//getting all the survey based on limenmangerId  and to and from date
		@RequestMapping(value = "/getSurveyByLineMngrIdAndDate.htm", method = RequestMethod.POST)
		public @ResponseBody String getSurveyByLineMngrIdAndDate(@RequestParam("lineManagerId") String lineManagerId,
				@RequestParam("toDate") String toDate,@RequestParam("fromDate") String fromDate,
				HttpServletRequest request, HttpServletResponse response) {
			logger.info("getSurveyByLineMngrIdAndDate method started");
			String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
			try {
				List<SurveyVO> surveyVOs = surveyBoardService.getSurveyByLineMngrIdAndDate(Integer.parseInt(lineManagerId), toDate, fromDate);
				
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(surveyVOs) + "}";
				
				}catch (Exception e) {
					result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
					}
			return result;
				
	}
		//getting survey avg based on survey id
		@RequestMapping(value = "/getSurveyQuestionPercentageBySurveyId.htm", method = RequestMethod.POST)
		public @ResponseBody String getSurveyQuestionPercentageBySurveyId(@RequestParam("sid") String sid,
				HttpServletRequest request, HttpServletResponse response) {
			logger.info("getSurveyByLineMngrIdAndDate method started");
			String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
			try {
				SurveyPercentageVO surveyQuestions = surveyQuestionService.getSurveryQuestionsPercentBySurveyId(Integer.parseInt(sid));				
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(surveyQuestions) + "}";
				
				}catch (Exception e) {
					result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
					}
			return result;
				
	}
		//closing survey based on survey id
				@RequestMapping(value = "/closingSurveyBySurveyId.htm", method = RequestMethod.POST)
				public @ResponseBody String closingSurveyBySurveyId(@RequestParam("sid") String sid,
						HttpServletRequest request, HttpServletResponse response) {
					logger.info("closingSurveyBySurveyId method started");
					String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
					try {
						boolean flag = surveyBoardService.closingSurveyBySurveyId(Integer.parseInt(sid));				
						result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Survey submitted successfully.\"}";

						}catch (Exception e) {
							result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
							+ "\"}";
							}
					return result;
						
			}
		}
