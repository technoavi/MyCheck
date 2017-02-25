package com.syntaxtree.agproengg.model;

import java.util.Arrays;
import java.util.Date;

public class Complaint {
private String	cropname;
private String	diseaseType; 
private String	impact; 
private String	reason; 
private String	medicine;
private String	recomndedproduct;
private String	phone; 
private Date	createdDate;
/*private  byte[] images;
private  byte[] audio;*/

public Complaint() {
	// TODO Auto-generated constructor stub
}

public String getcropname() {
	return cropname;
}

public void setcropname(String cropname) {
	cropname = cropname;
}

public String getDiesesType() {
	return diseaseType;
}

public void setDiesesType(String diesesType) {
	this.diseaseType = diesesType;
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

@Override
public String toString() {
	return "Complaint [cropname=" + cropname + ", diesesType=" + diseaseType + ", impact=" + impact + ", reason="
			+ reason + ", medicine=" + medicine + ", recomndedproduct=" + recomndedproduct + ", phone=" + phone
			+ ", createdDate=" + createdDate + "]";
}

/*public byte[] getImages() {
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
*/


}
