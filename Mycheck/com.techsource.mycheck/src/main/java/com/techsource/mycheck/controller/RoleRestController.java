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
import com.techsource.mycheck.service.RoleService;
import com.techsource.mycheck.vo.RoleVO;

/*****
 * 
 * @author rajender
 * 
 * @company syntaxtreesoft
 *
 */
@Controller
@RequestMapping("/role")
public class RoleRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(RoleRestController.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/getRoleList.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getRoleList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getEmployeeList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			List<RoleVO> roleVOs = roleService.getList();
			String data = "{\"count\":" + roleVOs.size() + ",\"data\":" + new Gson().toJson(roleVOs) + "}";
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
}
