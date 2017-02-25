package com.syntaxtree.agproengg.service;

import java.util.List;

import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.model.User;
import com.syntaxtree.agproengg.model.UserVO;



public interface UserService  {
	
/*	User findById(long id);
	
	User findByName(String name);*/
	
	boolean saveUser(UserVO user) throws Exception;

	User getUserById(long id);
	
	List<UserBO> getAllUserList();
	
	List<String> getComplaintsByPhone(String phone);
//	boolean saves(User  user);
	
	/*void updateUser(User user);
	
	void deleteUserById(long id);

	List<User> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(User user);*/
	
}
