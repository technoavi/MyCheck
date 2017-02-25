package com.syntaxtree.agproengg.bo;

import java.util.Arrays;
import java.util.Date;

public class ComplaintBO {
	private String	cropname;
	private String	diseaseType; 
	private String	impact; 
	private String	reason; 
	private String	medicine;
	private String	recomndedproduct;
	private String	phone; 
	private Date	createdDate;
	private  byte[] images;
	private  byte[] audio;
	
	public ComplaintBO() {
		// TODO Auto-generated constructor stub
	}

	public String getCropname() {
		return cropname;
	}

	public void setCropname(String cropname) {
		this.cropname = cropname;
	}

	public String getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public String getRecomndedproduct() {
		return recomndedproduct;
	}

	public void setRecomndedproduct(String recomndedproduct) {
		this.recomndedproduct = recomndedproduct;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}

	public byte[] getAudio() {
		return audio;
	}

	public void setAudio(byte[] audio) {
		this.audio = audio;
	}

	@Override
	public String toString() {
		return "ComplaintBO [cropname=" + cropname + ", diseaseType=" + diseaseType + ", impact=" + impact + ", reason="
				+ reason + ", medicine=" + medicine + ", recomndedproduct=" + recomndedproduct + ", phone=" + phone
				+ ", createdDate=" + createdDate + ", images=" + Arrays.toString(images) + ", audio="
				+ Arrays.toString(audio) + "]";
	}
	
	

}
