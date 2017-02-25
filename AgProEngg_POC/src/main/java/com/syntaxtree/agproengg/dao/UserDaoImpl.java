package com.syntaxtree.agproengg.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.syntaxtree.agproengg.controller.RegistrationRestController;
import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.Login;
import com.syntaxtree.agproengg.model.UploadedData;
import com.syntaxtree.agproengg.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	
	private final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);

	@Autowired
	 MongoTemplate mongoTemplate;
	@Autowired
	MongoOperations mongoOps;


	
	private static final String COLLECTION1 = "Login"; 
	private static final String COLLECTION = "User"; 
	private static final String COLLECTION2 = "Login"; 
	
	
	public boolean saveUser(User user, Login login) {
		try {
			 this.mongoTemplate.insert(user, COLLECTION);
			   this.mongoTemplate.insert(login, COLLECTION1);
			   return true;
				/*Criteria criteria = new Criteria();
		        criteria.orOperator(Criteria.where("email").is(userVO.getEmail()),Criteria.where("phone").is(userVO.getPhone()));
		        Query query = new Query(criteria);

		       User user=(User) mongoTemplate.find(query, User.class, "User");
		       System.out.println(user);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
public User getUserById(long id) {
	Criteria criteria = new Criteria();
    criteria.orOperator(Criteria.where("id").is(id));
    Query query = new Query(criteria);
	 User user=(User) mongoTemplate.find(query, User.class, "User");
	return user;
}

	public List<User> getAllUser() {
		 return (List) mongoTemplate.findAll(User.class,
				    COLLECTION);
	}
	
	@Override
	public List<String> getComplaintsByPhone(String phone) {
		int numOfRecord =0 ;
	/*	Query query = new Query(Criteria.where("phone").is(phone));
		  return (List<String>) this.mongoTemplate.findOne(query, User.class, COLLECTION);*/
		
		Query query = new Query(Criteria.where("phone").is(phone));

		if (numOfRecord > 0)
			query.limit(numOfRecord);

	
		query.fields().include("decription");
		query.fields().include("phone");
	List<UploadedData>	datas=mongoTemplate.find(query, UploadedData.class);
//	mongoOps.findAll(arg0, arg1);
	List<String > complaints= new  ArrayList<String>();
	for (UploadedData uploadedData : datas) {
		complaints.add(uploadedData.getDescription());
		complaints.add(uploadedData.getPhone());
		System.out.println(complaints);
	}
		return complaints;
	}
	
	@Override
	public boolean istUserExistByEmail(String email) {
		Query query = new Query(Criteria.where("email").is(email));
//		 List<User> users=new LinkedList<User>();
			User users= (User) mongoTemplate.findOne(query, User.class, COLLECTION);
		 System.out.println(users);
		 if(users==null){
			 return true;
		 }else{
		return false;}
	}
	@Override
	public boolean isUserExistByPhone(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
		// List<User> users=new LinkedList<User>();
		User users= (User) mongoTemplate.findOne(query, User.class, COLLECTION);
		 System.out.println(users);
		 if(users==null){
			 return true;
		 }
		 else{
				return false;}
	

}



}




















