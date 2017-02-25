package com.syntaxtree.agproengg.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.syntaxtree.agproengg.model.User;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {
	
	private static final String COLLECTION = "User"; 
	@Autowired
	 MongoTemplate mongoTemplate;
	public LoginDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
@Override
public User getUserByEmail(String email) {
	Query query = new Query(Criteria.where("email").is(email));
	  return this.mongoTemplate.findOne(query, User.class, COLLECTION);
}
}
