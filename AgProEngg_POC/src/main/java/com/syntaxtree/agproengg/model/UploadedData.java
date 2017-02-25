package com.syntaxtree.agproengg.model;

import java.io.File;

public class UploadedData {
	
	private  byte[] images;
	private  byte[] audio;
	private String id;
	private String description;
	private String phone;
	public UploadedData() {
		// TODO Auto-generated constructor stub
	}




	public byte[] getImages() {
		return images;
	}




	public void setImages(byte[] bs) {
		this.images = bs;
	}





	public byte[] getAudio() {
		return audio;
	}




	public void setAudio(byte[] audio) {
		this.audio = audio;
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	

	
	

}
