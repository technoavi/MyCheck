package com.techsource.mycheck.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
import com.techsource.mycheck.service.EmailGroupService;
import com.techsource.mycheck.utility.SendMails;
import com.techsource.mycheck.vo.EmailGroupVO;
import com.techsource.mycheck.vo.EmployeeInfo;
import com.techsource.mycheck.vo.RequestEmailGroup;

@Controller
@RequestMapping("/emailGroup")
public class EmailGroupRestController {
	private final static Logger logger = LoggerFactory.getLogger(EmailGroupRestController.class);

	@Autowired
	EmailGroupService emailGroupService;

	@RequestMapping(value = "/insertEmailGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String insertTargetBoard(@RequestParam("jsonData") String jsonData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestEmailGroup requestEmailGroup = this.prepareEmailGroupJSonObject(jsonData, false);
			if (requestEmailGroup != null) {
				boolean flag = emailGroupService.insertEmailGroup(requestEmailGroup);
				System.out.println("flag " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Email Group added successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	@RequestMapping(value = "/checkEmailGroupName.htm", method = RequestMethod.POST)
	public @ResponseBody String checkEmailGroupName(@RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSurveyList method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean flag = emailGroupService.checkEmailGroupExit(name);
			if (flag) {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"EmailGroup already Exist Please try other!!! .\"}";
			}
			else {
				 result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"OK\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}

	@RequestMapping(value = "/getEmailGroupList.htm", method = RequestMethod.POST)
	public @ResponseBody String getEmailGroupList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<EmailGroupVO> emailGroup = emailGroupService.getEmailGroupList();

			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
					+ new Gson().toJson(emailGroup) + "}";

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getEmailGroupDetailsById.htm", method = RequestMethod.POST)
	public @ResponseBody String getEmailGroupListById(@RequestParam("emailGpId") String emailGpId,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getEmailGroupListById method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			// RequestEmailGroup requestEmailGroup = new RequestEmailGroup();
			EmailGroupVO emailGroupVO = emailGroupService.getEmailGroupDetailsById(Integer.parseInt(emailGpId));
			if (emailGroupVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(emailGroupVO) + "}";
				// requestEmailGroup.setBusinessDivisionId(emailGroupVO.getId());

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	private RequestEmailGroup prepareEmailGroupJSonObject(String jsonData, boolean flag) {

		JSONObject jsonObject = new JSONObject(jsonData);
		RequestEmailGroup requestEmailGroup = new RequestEmailGroup();
		int emailgroupId = jsonObject.getInt("emailGroupId");
		requestEmailGroup.setEmailgroupId(emailgroupId);
		if (!flag) {
			String emailGroupName = jsonObject.getString("emailGroupName");
			requestEmailGroup.setEmailGroupName(emailGroupName);
		}

		int businessDivision = jsonObject.getInt("businessDivision");
		requestEmailGroup.setBusinessDivisionId(businessDivision);
		int department = jsonObject.getInt("department");
		requestEmailGroup.setDepartmentId(department);

		JSONArray jsonArray = jsonObject.getJSONArray("data");
		List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonObject2 = jsonArray.getJSONObject(i);

			EmployeeInfo employeeInfo2 = new EmployeeInfo();
			// JSONObject jsonObject3 = jsonArray2.getJSONObject(j);
			String name2 = jsonObject2.getString("name");
			employeeInfo2.setName(name2);
			int id2 = jsonObject2.getInt("id");
			employeeInfo2.setId(id2);
			// boolean state = jsonObject2.getBoolean("state");
			employeeInfo2.setState("TRUE");
			employeeInfos.add(employeeInfo2);
			// System.out.println("state :"+state +" empId ::"+id2);
			JSONArray jsonArray2 = jsonObject2.getJSONArray("employees");

			for (int j = 0; j < jsonArray2.length(); j++) {
				EmployeeInfo employeeInfo = new EmployeeInfo();
				JSONObject jsonObject3 = jsonArray2.getJSONObject(j);
				String name = jsonObject3.getString("name");
				employeeInfo.setName(name);
				int id = jsonObject3.getInt("id");
				employeeInfo.setId(id);
				boolean state2 = jsonObject3.getBoolean("state");
				System.out.println("state :" + state2 + " empId ::" + id);
				employeeInfo.setState(String.valueOf(state2));

				employeeInfos.add(employeeInfo);
			}

		}
		requestEmailGroup.setEmployeeInfos(employeeInfos);

		// TODO Auto-generated method stub
		return requestEmailGroup;
	}

	// class for the email group

	@RequestMapping(value = "/sendMailToGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String sendMailToGroup(@RequestParam("emailGrpId") String emailGrpId,
			@RequestParam("subject") String subject, @RequestParam("msg") String msg, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			boolean emailGroup = emailGroupService.sendEmailByEmpGrpId(Integer.parseInt(emailGrpId), subject, msg);
			if (emailGroup) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Email sent successfully\"}";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/deleteEmailGroupByEmailGrpId.htm", method = RequestMethod.POST)
	public @ResponseBody String deleteEmailGroupByEmailGrpId(@RequestParam("emailGrpId") String emailGrpId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			boolean emailGroup = emailGroupService.deleteEmailGroupByEmailGrpId(Integer.parseInt(emailGrpId));
			if (emailGroup) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Emailgroup deleted successfully\"}";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateEmailGroup.htm", method = RequestMethod.POST)
	public @ResponseBody String UpdateEmailGroup(@RequestParam("jsonData") String jsonData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestEmailGroup requestEmailGroup = this.prepareEmailGroupJSonObject(jsonData, true);
			if (requestEmailGroup != null) {
				boolean flag = emailGroupService.updateEmailGroup(requestEmailGroup);
				System.out.println("flag updated succesfully " + flag);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Email Group updated successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}
	/*@RequestMapping(value = "/sendMailTo.htm", method = RequestMethod.POST)
	  public  @ResponseBody String sendMailservices(@RequestParam("to") String to,
				@RequestParam("usernames") String usernames, @RequestParam("passwords") String passwords, 
				@RequestParam("subject") String subject, HttpServletRequest request,
				HttpServletResponse response) {
		
		String result=null;
		boolean ss=false;
		try {
			 ss =SendMails.sendMails(to, usernames, passwords, subject);

			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + ss + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":" + ss + "}";
			e.printStackTrace();
		}

		return result;
	}
*/
}
