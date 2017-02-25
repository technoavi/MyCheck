package com.syntaxtree.agproengg.service;

import java.util.List;

import com.syntaxtree.agproengg.bo.ComplaintBO;
import com.syntaxtree.agproengg.model.UploadedData;

public interface UploadDataService {
	public boolean saveData(UploadedData uploadDataVOs);
	
	public boolean saveDatas(UploadedData uploadedDataVO);
	UploadedData getImageAndAudioByPhone(String phone);
	 List<ComplaintBO> getyAudioAndImageByPhone(String phone);

}
