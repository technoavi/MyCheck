package com.syntaxtree.agproengg.service;


import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.dao.LoginDao;
import com.syntaxtree.agproengg.model.Login;
import com.syntaxtree.agproengg.model.User;

@Repository("loginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	 LoginDao loginDao;
	
	@Autowired
	 MongoTemplate mongoTemplate;
	
	public boolean loginAuthentication(String email, String password) {
		
		Query query=null;
		try {
			System.out.println("loginAuthentication ");
			 Login login = mongoTemplate.findOne(new org.springframework.data.mongodb.core.query.Query(Criteria
					  .where("username").is(email).and("password").is(password)), Login.class,"Login");
	        if(login!=null){
	        	return true;
	        }
	        else
		return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public UserBO getUserByEmail(String email) {
		// TODO Auto-generated method stub
		UserBO bo=null;
	//	User user=null;
		User user=	loginDao.getUserByEmail(email);
		bo= new UserBO();
		bo.setId(user.getId());
		bo.setFname(user.getFname());
		bo.setLname(user.getLname());
		bo.setEmail(user.getEmail());
		bo.setPhone(user.getPhone());
		bo.setStatus(user.getStatus());
		
		return bo;
	}
}
