package com.syntaxtree.agproengg.model;

import java.io.Serializable;
import java.util.Date;

public class FileUpload implements Serializable{
	private long id;
	private String imagePath;
	private String audioPath;
	private String imgName;
	private String audioName;
	private String description;
	private String phone;
	private Date createdDate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getAudioName() {
		return audioName;
	}
	public void setAudioName(String audioName) {
		this.audioName = audioName;
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
	public FileUpload() {
		// TODO Auto-generated constructor stub
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", imagePath=" + imagePath + ", audioPath=" + audioPath + ", imgName=" + imgName
				+ ", audioName=" + audioName + ", description=" + description + ", phone=" + phone + ", createdDate="
				+ createdDate + "]";
	}
	
	

}
