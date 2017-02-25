package com.techsource.mycheck.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public interface MyController {

   // @RequestMapping(value = "{id}/", method = RequestMethod.DELETE)
	//(String to, String usernames, String passwords,String subject)
   
	@RequestMapping(value = "/person/{empId}", method = RequestMethod.POST)
	  public  String getPersonDetail(@PathVariable("empId") String empId,
		 HttpServletRequest request, HttpServletResponse response);
	
	@RequestMapping(value = "/sendMailTo.htm", method = RequestMethod.POST)
	  public  String sendMailservices(@RequestParam("to") String to,
				@RequestParam("usernames") String usernames, @RequestParam("passwords") String passwords, 
				@RequestParam("subject") String subject, HttpServletRequest request,
				HttpServletResponse response) ;
}
