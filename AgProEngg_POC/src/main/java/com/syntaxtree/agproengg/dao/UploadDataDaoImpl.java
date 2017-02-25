package com.syntaxtree.agproengg.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.syntaxtree.agproengg.model.UploadDataVO;
import com.syntaxtree.agproengg.model.UploadedData;


@Repository("uploadDataDao")
public class UploadDataDaoImpl implements UploadDataDao{

	
	@Autowired
	 MongoTemplate mongoTemplate;
	
	
	private static final String COLLECTION = "UploadedFiles"; 
	

	public boolean saveDatas(UploadedData data) {
		this.mongoTemplate.insert(data, COLLECTION);
		   return true;
	}
	public boolean saveData(UploadDataVO data) {
		// TODO Auto-generated method stub
		return false;
	}

}
