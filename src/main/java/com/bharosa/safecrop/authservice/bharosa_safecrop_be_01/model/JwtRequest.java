package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public JwtRequest(String number) {
		super();
		this.number = number;
	}

	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}