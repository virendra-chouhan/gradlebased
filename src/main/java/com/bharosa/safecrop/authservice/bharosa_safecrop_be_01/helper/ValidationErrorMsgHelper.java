package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper;

import org.springframework.stereotype.Component;

@Component
public class ValidationErrorMsgHelper {
	private String Field;
	private String message;
	public String getField() {
		return Field;
	}
	public void setField(String field) {
		Field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ValidationErrorMsgHelper(String field, String message) {
		super();
		Field = field;
		this.message = message;
	}
	public ValidationErrorMsgHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
