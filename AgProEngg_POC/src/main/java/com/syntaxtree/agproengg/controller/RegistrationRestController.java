package com.syntaxtree.agproengg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.dao.ComplaintDAO;
import com.syntaxtree.agproengg.exception.EmailAreadyExistException;
import com.syntaxtree.agproengg.exception.PhoneException;
import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.User;
import com.syntaxtree.agproengg.model.UserVO;
import com.syntaxtree.agproengg.service.UserService;

@RestController
@RequestMapping("/regis")
public class RegistrationRestController {
	private final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);

	@Autowired
	UserService userService; // Service which will do all data
								// retrieval/manipulation work
	
	@Autowired
	ComplaintDAO complaintDAO; 

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public @ResponseBody String addEmployee(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("mobileno") String mobileno,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		logger.info("addUser  started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			UserVO userVO = new UserVO();
			userVO.setId(1);
			userVO.setFname(fname);
			userVO.setLname(lname);
			userVO.setEmail(email);
			userVO.setPhone(mobileno);
			userVO.setPassword(password);
			userVO.setStatus("0");
			boolean flag = userService.saveUser(userVO);
			// return new ResponseEntity<User>(car, HttpStatus.OK);
			if (flag) {
				result = "{\"StatusMessage\":\"" + "New user has been created successfully" + "\",\"ResponseStatus\":\""
						+ "1 " + "\"}";
				logger.info("addUser  created");
			} else {
				result = "{\"StatusMessage\":\"" + "User was already created by using " + userVO.getPhone()
						+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
				logger.info("addUser  failed");
			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)*/
		@POST
	  @Path("{jsonData}")
	  @Consumes(MediaType.APPLICATION_JSON)
	public String mymethod(@PathParam("jsonData") String registerjsonData) {
		String result=null;
		return result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" +registerjsonData+"\"}";

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestParam("registerjsonData") String registerjsonData,
			HttpServletRequest request, HttpServletResponse response) throws EmailAreadyExistException, PhoneException {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		UserVO userVO = null;
		try {
			userVO = this.prepareRegisterJsonData(registerjsonData);
			
			if (userVO != null) {
				boolean flag = userService.saveUser(userVO);
				System.out.println("flag " + flag);
				if (flag) {
					//to generate the response using response
				/*	return Response.status(Response.Status.CREATED).entity("A new User has been created").build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("User already exist with email : " + userVO.getEmail()).build();
				//return	new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity("user " + userVO)
					.type(MediaType.APPLICATION_JSON).tag(e.getMessage()).build();

		}
		// return Response.status(200).entity("new User : " + userVO).build();
		return Response.status(400).tag("failure").build();*/
			
						result = "{\"StatusMessage\":\"" + "New user has been created successfully" + "\",\"ResponseStatus\":\""
								+ "1 " + "\"}";
						logger.info("addUser  created");
					} /*else {
						result = "{\"StatusMessage\":\"" + "User was already created by using " +userVO.getEmail()
								+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
						logger.info("addUser  failed");
					}*/
				
			}
		} catch (EmailAreadyExistException e) {
			result = "{\"StatusMessage\":\"expetion\",\"ResponseStatus\":\"" + e.getMessage()
					+ "\"}";
		}catch (PhoneException e) {
			result = "{\"StatusMessage\":\"expetion\",\"ResponseStatus\":\"" + e.getMessage()
			+ "\"}";
}
				catch (Exception e) {
					result = "{\"StatusMessage\":\"expetion\",\"ResponseStatus\":\"" + e.getMessage()
							+ "\"}";
				}

				return result;
	}

	private UserVO prepareRegisterJsonData(String jsonData) {

		JSONObject jsonObject = new JSONObject(jsonData);
		UserVO userVO = new UserVO();
	
		userVO.setFname(jsonObject.getString("fname"));
		userVO.setLname(jsonObject.getString("lname"));
		userVO.setEmail(jsonObject.getString("email"));
		userVO.setPhone(jsonObject.getString("phone"));
		userVO.setPassword(jsonObject.getString("password"));
		userVO.setStatus("1");

		return userVO;
	}
	@RequestMapping(value = "/getUserById", method = RequestMethod.POST)
	@ResponseBody String getUserById(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		String result=null;
		try{
		User user=userService.getUserById(Long.parseLong(id));
		
		if (user!=null) {
	
	
				result = "{\"StatusMessage\":\"" + "New user has been created successfully" + "\",\"ResponseStatus\":\""
						+ new Gson().toJson(user) + "\"}";
				logger.info("addUser  created");
			} else {
				result = "{\"StatusMessage\":\"" + "User was already created by using " + user.getId()
						+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
				logger.info("addUser  failed");
			}
	
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
}
	
	@RequestMapping(value = "/getAllUserList", method = RequestMethod.POST)
	@ResponseBody String getAllUser(
			HttpServletRequest request, HttpServletResponse response) {
		
		String result=null;
		try{
		List<UserBO> user=userService.getAllUserList();
		System.out.println(user);
		
		if (user!=null) {
	
			//String data = "{\"count\":" + user.size() + ",\"data\":" + new Gson().toJson(user) + "}";
				result = new Gson().toJson(user)  ;
				logger.info("addUser  created");
			} else {
				result = "{\"StatusMessage\":\"" + "No record Found" 
						+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
				logger.info("addUser  failed");
			}
	
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
}
	@RequestMapping(value = "/getComplaintsByPhone", method = RequestMethod.POST)
	@ResponseBody String getComplaintsByPhone(@RequestParam("phone") String phone,
			HttpServletRequest request, HttpServletResponse response) {
		
		String result=null;
		try{
		List<Complaint>  complaints=complaintDAO.getComplaintByPhone(phone);
		System.out.println(complaints);
		if (complaints!=null) {
	
	
				result = new Gson().toJson(complaints);
				logger.info("addUser  created");
			} else {
				result = "{\"StatusMessage\":\"" + "User was already created by using " + complaints
						+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
				logger.info("addUser  failed");
			}
	
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
}
}
