package com.slot.response;

import java.util.List;

public class ApiResponse {
	
	private String status;
	private List<?> result;
	public ApiResponse(String status, List<?> result) {
		super();
		this.status = status;
		this.result = result;
	}
	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	
	
}
