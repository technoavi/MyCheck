package com.techsource.mycheck.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.techsource.mycheck.service.LongtermGoalService;
import com.techsource.mycheck.vo.LongTermGoalVO;

/*****
 * 
 * @author Avinash Srivastava
 * 
 * @company syntaxtreesoft
 *
 */
@Controller
@RequestMapping("/longTermGoal")
public class LongTermGoalRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(LongTermGoalRestController.class);
	
	
	@Autowired
	LongtermGoalService longtermGoalService;

	@RequestMapping(value = "/createLongTermGoal.htm", method = RequestMethod.POST)
	public @ResponseBody String createLongTermGoal(@RequestParam("empId") String empId, @RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate
		,HttpServletRequest request, 	HttpServletResponse response) {
		logger.info("into createsurvey()");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd");
		Date sDate=	dateFormat.parse(startDate);
		Date edate=dateFormat.parse(endDate);
		
			LongTermGoalVO longTermGoalVO = new LongTermGoalVO();
			longTermGoalVO.setEmpId(Integer.parseInt(empId));
			longTermGoalVO.setName(name);
			longTermGoalVO.setDescription(description);
			longTermGoalVO.setStartDate(sDate);
			longTermGoalVO.setEndDate(edate);
	
				boolean flag = longtermGoalService.insertLongtermGoal(longTermGoalVO);
				if (flag) {
					
					logger.debug("flag " + flag);
					
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"LongTermGoal Created successfully.\"}";
					
				}
				
			

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateLongTermGoal.htm", method = RequestMethod.POST)
	public @ResponseBody String updateLongTermGoal(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			HttpServletRequest request,	HttpServletResponse response) {
		logger.info("insertTargetBoard method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd");
			Date sDate=	dateFormat.parse(startDate);
			Date edate=dateFormat.parse(endDate);
			
				LongTermGoalVO longTermGoalVO = new LongTermGoalVO();
				longTermGoalVO.setId(id);
				longTermGoalVO.setName(name);
				longTermGoalVO.setDescription(description);
				longTermGoalVO.setStartDate(sDate);
				longTermGoalVO.setEndDate(edate);
				boolean flag = longtermGoalService.updateLongTermGoal(longTermGoalVO);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"LongTermGoal updated successfully.\"}";
				}
			}

		 catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}
		return result;

	}
	@RequestMapping(value = "/deleteLongTermGoalById.htm", method = RequestMethod.POST)
	public @ResponseBody String deleteLongTermGoalById(@RequestParam("gId") String gId, HttpServletRequest request,
			HttpServletResponse response) {
		// String result =
		// "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something
		// went wrong. Please check.\"}";
		String result = null;
		try {
			boolean flag = longtermGoalService.deleteLongTermGoalById(Integer.parseInt(gId));

			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Goal deleted successfully.\"}";

			} else {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"No record Found for the current Goal Id :"
						+ gId + "\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"No record Found for the current Question Id :"
					+ gId + "\"}";
		}

		return result;
	}
	


	@RequestMapping(value = "/getLongTermGoalByEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getSurvey(@RequestParam("eId") int eId ,  HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<LongTermGoalVO> surveyVOs = longtermGoalService.getlongtermGoalByEmpId(eId);
			String data = "{\"count\":" + surveyVOs.size() + ",\"data\":" + new Gson().toJson(surveyVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	
	@RequestMapping(value = "/getLongTermGoalBygoalId.htm", method = RequestMethod.POST)
	public @ResponseBody String getLongTermGoalBygoalId(@RequestParam("gId") int gId ,  HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			LongTermGoalVO longtermVos = longtermGoalService.getLongTermGoalBygoalId(gId);
		//	String data = "{\"count\":" + surveyVOs.size() + ",\"data\":" + new Gson().toJson(surveyVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(longtermVos) + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	
	
}
