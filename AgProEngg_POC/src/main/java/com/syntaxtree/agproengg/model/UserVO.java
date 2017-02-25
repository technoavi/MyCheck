package com.syntaxtree.agproengg.model;

public class UserVO {
	private int id;
	private String fname;
	private String lname;
	private String phone;
	private String email;
	private String status;
/*	private Date created;
	private Date modified;
	private Date deleted;*/
	private String password;
	public UserVO() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserVO [id=" + id + ", fname=" + fname + ", lname=" + lname + ", mobile_no=" + phone + ", email="
				+ email + ", status=" + status + ", password=" + password + "]";
	}
	
}
