package com.techsource.mycheck.controller;

import java.util.ArrayList;
import java.util.Iterator;

//@Component
public class MyControllerImpl {

	/*@Override
	public String getPersonDetail(String empId, HttpServletRequest request, HttpServletResponse response)
	{
		
		String result=null;
		try {
			result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + empId + "}";
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":" + empId + "}";
			e.printStackTrace();
		}

		return result;
	}
@Override
public String sendMailservices(String to, String usernames, String passwords, String subject, HttpServletRequest request,
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

	return result;*/
	public static void main(String[] args)
	   {
	      Integer obj1 = new Integer(2009);
	      System.out.println("hashCode for an integer " + obj1.hashCode());

	      String obj2 = new String("2009");
	      System.out.println("\nhashCode for a string " + obj2.hashCode());

	      StringBuffer obj3 = new StringBuffer("2009");
	      System.out.println("\nhashCode for a string buffer " + obj3.hashCode());

	      ArrayList<Integer> obj4 = new ArrayList<Integer>();
	      obj4.add(new Integer(2009));
	      System.out.println("\nhashCode for an arraylist " + obj4.hashCode());

	      Iterator obj5 = obj4.iterator();
	      System.out.println("\nhashCode for an iterator " + obj5.hashCode());

	      MyControllerImpl obj6 = new MyControllerImpl();
	      System.out.println("\nhashCode for HashCodeDemo " + obj6.hashCode());

	      String obj7 = new String("19999999999999999");
	      System.out.println("\nhashCode can be negative " + obj7.hashCode());
	   }

}
