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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.service.GroupService;
import com.techsource.mycheck.vo.GroupVO;

/*****
 * 
 * @author rajender
 * 
 * @company syntaxtreesoft
 *
 */
@Controller
@RequestMapping("/group")
public class GroupRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(GroupRestController.class);

	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/getListByDepartment.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getListByDepartment(@RequestParam("departmentId") String departmentId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("getListByDepartment method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<GroupVO> groupsVOs = groupService.getListByDepartment(departmentId);
			String data = "{\"count\":" + groupsVOs.size() + ",\"data\":" + new Gson().toJson(groupsVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
}
