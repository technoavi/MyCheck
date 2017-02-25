package com.syntaxtree.agproengg.bo;

public class UserBO {
	private long id;
	private String fname;
	private String lname;
	private String phone;
	private String email;
	private String status;
	
	public UserBO() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	@Override
	public String toString() {
		return "UserBO [id=" + id + ", fname=" + fname + ", lname=" + lname + ", phone=" + phone + ", email=" + email
				+ ", status=" + status + "]";
	}


}
