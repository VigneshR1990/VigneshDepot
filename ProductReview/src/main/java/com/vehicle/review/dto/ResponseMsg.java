package com.vehicle.review.dto;

public class ResponseMsg {
	
	private String errorCode;
	private String message;
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	public ResponseMsg() {
		// TODO Auto-generated constructor stub
	}

	

	public ResponseMsg(String errorCode2, String message2) {
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode2;
		this.message = message2;
	}
	
	
	
}
