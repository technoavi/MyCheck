package com.syntaxtree.agproengg.dao;

import java.util.List;

import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.UploadedData;

public interface ComplaintDAO {

	List<Complaint> getComplaintByPhone(String phone);
	List<UploadedData> getyAudioAndImageByPhone(String phone);
}
