package com.techsource.mycheck.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.service.DivisionService;
import com.techsource.mycheck.vo.DivisionVO;

/*****
 * 
 * @author rajender
 * 
 * @company syntaxtreesoft
 *
 */
@Controller
@RequestMapping("/division")
public class DivisionRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(DivisionRestController.class);

	@Autowired
	private DivisionService divisionService;

	@RequestMapping(value = "/getDivisionList.htm", method ={ RequestMethod.POST})
	public @ResponseBody String getDivisionList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getEmployeeList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<DivisionVO> divisionVOs = divisionService.getActiveList();
			String data = "{\"count\":" + divisionVOs.size() + ",\"data\":" + new Gson().toJson(divisionVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
}
