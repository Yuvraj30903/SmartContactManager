package com.smart.entities;

public class LoginData {

	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginData(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public LoginData() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LoginData [username=" + username + ", password=" + password + "]";
	}
	
}
