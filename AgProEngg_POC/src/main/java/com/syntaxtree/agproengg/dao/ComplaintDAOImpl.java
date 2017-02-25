package com.syntaxtree.agproengg.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.syntaxtree.agproengg.bo.ComplaintBO;
import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.UploadedData;


@Repository("complaintDAO")
public class ComplaintDAOImpl  implements ComplaintDAO{
	private static final String COLLECTION = "Complaints"; 
	private static final String COLLECTION2 = "UploadedFiles"; 

	@Autowired
	 MongoTemplate mongoTemplate;
	@Override
	public List<Complaint> getComplaintByPhone(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
		 List<Complaint> complaints=new LinkedList<Complaint>();
		 complaints= (List)mongoTemplate.find(query, Complaint.class, COLLECTION);
		 System.out.println(complaints);
		 return complaints;
	}
@Override
public List<UploadedData> getyAudioAndImageByPhone(String phone) {
	Query query = new Query(Criteria.where("phone").is(phone));
	 List<UploadedData> complaints=new LinkedList<UploadedData>();
	 complaints= (List)mongoTemplate.find(query, UploadedData.class, COLLECTION2);
	 
	 System.out.println(complaints);
	 return complaints;
}
}
