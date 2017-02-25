package com.techsource.mycheck.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.techsource.mycheck.service.CommonSurveyQuestionService;
import com.techsource.mycheck.service.CommonSurveyService;
import com.techsource.mycheck.service.SurveyBoardServiceImpl;
import com.techsource.mycheck.vo.CommonSurveyPercentageVO;
import com.techsource.mycheck.vo.CommonSurveyQuestionVO;
import com.techsource.mycheck.vo.CommonSurveyVO;
import com.techsource.mycheck.vo.RequestCommonSurveyBoard;
import com.techsource.mycheck.vo.RequestCommonSurveyRating;
import com.techsource.mycheck.vo.RequestSurveyRating;
import com.techsource.mycheck.vo.SurveyPercentageVO;



@Controller
@RequestMapping("/commonSurvey")
public class CommonSurveyRestController {
	
	private final Logger logger = LoggerFactory.getLogger(SurveyBoardServiceImpl.class);

	@Autowired
	private CommonSurveyService commonSurveyService;
	
	@Autowired
	private CommonSurveyQuestionService commonSurveyQuestionService;
	
	
	@RequestMapping(value = "/createCommonSurvey.htm", method = RequestMethod.POST)
	public @ResponseBody String createCommonSurvey(@RequestParam ("commonsurveyData") String commonsurveyData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("createCommonSurvey callled");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestCommonSurveyBoard requestCommonSurveyBoard = this.prepareJSonObject(commonsurveyData, false);
			if (requestCommonSurveyBoard != null) {
				boolean flag = commonSurveyService.insertCommonSurveyBoard(requestCommonSurveyBoard);
				logger.debug("flag " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurvey Created successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	
	@RequestMapping(value = "/updateCommonSurvey.htm", method = RequestMethod.POST)
	public @ResponseBody String updateCommonSurvey(@RequestParam("commonSurveyData") String commonSurveyData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("updateCommonSurvey method started");
		System.out.println("updated json ::" + commonSurveyData);
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestCommonSurveyBoard requestCommonSurveyBoard = this.prepareJSonObject(commonSurveyData, true);

			if (requestCommonSurveyBoard != null) {
				boolean flag = commonSurveyService.updateCommonSurveyBoard(requestCommonSurveyBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurvey updated successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;

	}
	
	@RequestMapping(value = "/getCommonSurveyDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyDetailsById(@RequestParam("commonSurveyId")  String commonSurveyId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getCommonSurveyDetailsById method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			RequestCommonSurveyBoard requestSurveyBoard = new RequestCommonSurveyBoard();
			CommonSurveyVO surveyVOs = commonSurveyService.getCommonSurveyDetailsById(Integer.parseInt(commonSurveyId));
			if (surveyVOs != null) {
				requestSurveyBoard.setCommonsurvey(surveyVOs);
				
				List<CommonSurveyQuestionVO> surveyQuestions = commonSurveyQuestionService.getCommonSurveryQuestionsBySurveyId(surveyVOs.getId());
				if (surveyQuestions.size() > 0) {
					requestSurveyBoard.setCommonSurveyQuestion(surveyQuestions);
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
	
	@RequestMapping(value = "/getCommonSurveyList.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getCommonSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<CommonSurveyVO> commonsurveyVOs = commonSurveyService.getCommonSurveyList();
			String data = "{\"count\":" + commonsurveyVOs.size() + ",\"data\":" + new Gson().toJson(commonsurveyVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	@RequestMapping(value = "/checkCommonSurveyName.htm", method = RequestMethod.POST)
	public @ResponseBody String checkCommonSurveyName(@RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) {
		logger.info("checkCommonSurveyName method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = commonSurveyService.checkCommonSurveyNameExist(name);
			if (flag) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"CommonSurvey already Exist Please try other!!! .\"}";
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
	
	
	@RequestMapping(value = "/deleteCommonSurveyQuestionsByQstnId.htm", method = RequestMethod.POST)
	public @ResponseBody String deleteCommonSurveyQuestionsByQstnId(@RequestParam("qstnId") String qstnId, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("deleteCommonSurveyQuestionsByQstnId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = commonSurveyQuestionService.deleteQuestionByQstnId(Integer.parseInt(qstnId));
			if(flag){
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurvey Questions Deleted\"}";
			}else {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"No CommonSurvey Questions Found\"}";

				
				
			}
		
		
		
		
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	@RequestMapping(value = "/updateCommonSurveyStatusByCommonSurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String updateSurveyStatusBySurveyId(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("updateCommonSurveyStatusByCommonSurveyId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		boolean flag = false;
		try {
			flag = commonSurveyQuestionService.updateCommonSurveyStatusByCommonSurveyId(Integer.parseInt(id));
			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurveyBoard inactivated successfully.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	@RequestMapping(value = "/addCommonSurveyQuestions.htm", method = RequestMethod.POST)
	public @ResponseBody String addCommonSurveyQuestions(@RequestParam("commonSurveyData") String commonSurveyData,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("addCommonSurveyQuestions called ");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";

		try {
			RequestCommonSurveyBoard requestSurveyBoard = this.commonSurveyQstnJSonObject(commonSurveyData);
			if (requestSurveyBoard != null) {
				boolean flag = commonSurveyQuestionService.addCommonSurveyQuestion(requestSurveyBoard);
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
	
	@RequestMapping(value = "/updateCommonSurveyQuestionByCommonSurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String updateCommonSurveyQuestionByCommonSurveyId(@RequestParam("id") String id,
			@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {

		logger.info("updateCommonSurveyQuestionByCommonSurveyId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		boolean flag = false;
		try {
			flag = commonSurveyQuestionService.updateCommonSurveyQuestionByCommonSurveyId(Integer.parseInt(id), name);
			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurvey questions added successfully.\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	public RequestCommonSurveyBoard commonSurveyQstnJSonObject(String jsonData) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestCommonSurveyBoard reqSurveyBd = new RequestCommonSurveyBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			reqSurveyBd.setEmpId(eId);

			JSONObject jsonObject2 = jsonObject.getJSONObject("commonSurveyBoard");
			CommonSurveyVO surveyVO = new CommonSurveyVO();
			int targetId = jsonObject2.getInt("id");
			surveyVO.setId(targetId);
			reqSurveyBd.setCommonsurvey(surveyVO);
			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("commonSurveyQuestions");
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
			reqSurveyBd.setCommonSurveyQuestion(datas);
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

	
	public RequestCommonSurveyBoard prepareJSonObject(String jsonData, boolean updateFlag) throws ParseException {
		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestCommonSurveyBoard requestCommonSurveyBoard = new RequestCommonSurveyBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			requestCommonSurveyBoard.setEmpId(eId);
			System.out.println("id is " + eId);

			JSONObject jsonObject2 = jsonObject.getJSONObject("surveyBoard");
			CommonSurveyVO commonSurveyVO = new CommonSurveyVO();
			String name = jsonObject2.get("name").toString();
			commonSurveyVO.setName(name);
			String desc = jsonObject2.get("description").toString();
			commonSurveyVO.setDescription(desc);
			if (updateFlag) {
				int surveyid = jsonObject2.getInt("id");
				commonSurveyVO.setId(surveyid);
			}
	
			String startdate = jsonObject2.get("startDate").toString();

			commonSurveyVO.setStartDate(df.parse(startdate));
			String enddate = jsonObject2.get("endDate").toString();
			// DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			commonSurveyVO.setEndDate(df.parse(enddate));
			commonSurveyVO.setState("OPEN");
			requestCommonSurveyBoard.setCommonsurvey(commonSurveyVO);

			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("surveyQuestions");
			List<CommonSurveyQuestionVO> datas = new LinkedList<CommonSurveyQuestionVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				CommonSurveyQuestionVO commonSurveyQuestionVO = new CommonSurveyQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				commonSurveyQuestionVO.setId(jsonObject3.getInt("id"));
				int rating = jsonObject3.getInt("rating");
				commonSurveyQuestionVO.setRating(rating);
				String q_name = jsonObject3.get("name").toString();
				commonSurveyQuestionVO.setName(q_name);
			
				datas.add(commonSurveyQuestionVO);
				

			}
			requestCommonSurveyBoard.setCommonSurveyQuestion(datas);
			return requestCommonSurveyBoard;
		} 
		catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/getCommonSurveyByEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyByEmpId(@RequestParam("empId") String empId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getCommonSurveyByEmpId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<CommonSurveyVO> surveyVOs = commonSurveyService.getCommonSurveyByEmpId(Integer.parseInt(empId));
			
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(surveyVOs) + "}";
			
			}catch (Exception e) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
				+ "\"}";
				}
		return result;
			
}
	
	@RequestMapping(value = "/getCommonSurveyQuestionBySurveyIdAndEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyQuestionBySurveyId(@RequestParam("sId") String sId,
			@RequestParam("eId") String eId,HttpServletRequest request, HttpServletResponse response) 
 {
		logger.info("getCommonSurveyQuestionBySurveyIdAndEmpId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			
			List<CommonSurveyQuestionVO> questionVOs = commonSurveyQuestionService.getCommonSurveyQuestionBySurveyId(Integer.parseInt(sId),Integer.parseInt(eId));
			
			if(questionVOs!=null){

			result="{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\": { \"commonSurveyId\":" + sId + 
					",\"commonSurveyQuestions\":" + new Gson().toJson(questionVOs) + "} }";
			}
			
			else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"  Du har redan deltagit i undersökningen. \"}";
			}
			
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\" Du behöver inte ha Survey Frågor. Kontrollera med Admin. \"}";
		}

		return result;

	}
	@RequestMapping(value = "/getCommonSurveyByDate.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyByDate(@RequestParam("sdate") String sdate,
			@RequestParam("edate") String edate, HttpServletRequest request, HttpServletResponse response) {
		logger.info("getCommonSurveyByEmpId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<CommonSurveyVO> surveyVOs = commonSurveyService.getCommonSurveyByDate(sdate,edate);
			
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(surveyVOs) + "}";
			
			}catch (Exception e) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
				+ "\"}";
				}
		return result;
			
}
	
	@RequestMapping(value = "/commonSurveyRating.htm", method = RequestMethod.POST)
	public @ResponseBody String commonSurveyRating(@RequestParam("commonSurveyData") String commonSurveyData,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("commonSurveyRating method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			
			RequestCommonSurveyRating requestSurveyRating = this.prepareJSonObjectForCommonSurveyRating(commonSurveyData);
			if (requestSurveyRating != null) {
				boolean flag = commonSurveyQuestionService.insertCommonSurveyRating(requestSurveyRating);
				logger.debug("flag " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"SurveyRating inserted successfully.\"}";
				}}
			}catch (Exception e) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
				+ "\"}";
				}
		return result;
			
}
	public RequestCommonSurveyRating prepareJSonObjectForCommonSurveyRating(String jsonData) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestCommonSurveyRating requestSurveyrating = new RequestCommonSurveyRating();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			requestSurveyrating.setEmpId(eId);
			int sId = Integer.parseInt(jsonObject.get("commonSurveyId").toString());
			requestSurveyrating.setCommonSurveyId(sId);

			
			Map<Integer, Integer> map= new HashMap<Integer, Integer>();
			JSONArray jsonArray = jsonObject.getJSONArray("commonSurveyQuestions");
			for (int i = 0; i < jsonArray.length(); i++) {
				
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				
				//Map<Integer, String >map2 = new HashMap<Integer, String>();
				int q_name = jsonObject2.getInt("rating");
				map.put(jsonObject2.getInt("id"), q_name);
			}
			requestSurveyrating.setCommonSurveyQuestion(map);
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
	@RequestMapping(value = "/getCommonSurveyQuestionPercentageBySurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String getCommonSurveyQuestionPercentageBySurveyId(@RequestParam("sid") String sid,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyByLineMngrIdAndDate method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			CommonSurveyPercentageVO csurveyQuestions = commonSurveyQuestionService.getCommonSurveryQuestionsPercentBySurveyId(Integer.parseInt(sid));				
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(csurveyQuestions) + "}";
			
			}catch (Exception e) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
				+ "\"}";
				}
		return result;
			
}
	//closing survey based on survey id
	@RequestMapping(value = "/closingCommonSurveyBySurveyId.htm", method = RequestMethod.POST)
	public @ResponseBody String closingCommonSurveyBySurveyId(@RequestParam("sid") String sid,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("closingSurveyBySurveyId method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = commonSurveyService.closingCommonSurveyBySurveyId(Integer.parseInt(sid));				
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"CommonSurvey closed successfully\"}";

			}catch (Exception e) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
				+ "\"}";
				}
		return result;
			
}
	
}
