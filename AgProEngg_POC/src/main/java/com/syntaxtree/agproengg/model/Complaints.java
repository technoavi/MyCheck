package com.syntaxtree.agproengg.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Complaints {
	private	int id;
	private String name;
	private String description;
	private String phone;
	private Date complaintDate;
	
	public Complaints() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		complaintDate = complaintDate;
	}

	@Override
	public String toString() {
		return "Complaints [id=" + id + ", name=" + name + ", description=" + description + ", phone=" + phone
				+ ", ComplaintDate=" + complaintDate + "]";
	}

}
