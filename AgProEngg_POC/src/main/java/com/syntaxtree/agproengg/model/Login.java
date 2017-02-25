package com.syntaxtree.agproengg.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Login {
	@Field("id")
	private long id;
	private String username;
	private String password;
	public Login(int id, String username, String password) {
		
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public Login() {
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
	

}
