package com.syntaxtree.agproengg.model;

import java.io.FileInputStream;

public class UploadDataVO {
	private  String image;
	private  String audio;
	private String id;
	private String description;
	
	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getAudio() {
		return audio;
	}


	public void setAudio(String audio) {
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


	@Override
	public String toString() {
		return "UploadDataVO [image=" + image + ", audio=" + audio + ", id=" + id + ", description=" + description
				+ "]";
	}
	


}
