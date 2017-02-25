package com.techsource.mycheck.utility;

public class StatusCode {

	private int responseCode;
	private String responseMessage;
	private String responseData;

	public StatusCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatusCode(int responseCode, String responseMessage, String responseData) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseData = responseData;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
	
	public static StatusCode getDefault(){
		
		return new StatusCode(400,"failed","");
	}

}
