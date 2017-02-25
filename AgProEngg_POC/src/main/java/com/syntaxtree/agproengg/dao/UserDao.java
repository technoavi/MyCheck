package com.syntaxtree.agproengg.dao;

import java.util.List;

import com.syntaxtree.agproengg.model.Login;
import com.syntaxtree.agproengg.model.User;

public interface UserDao {
	public boolean saveUser(User user, Login login);
	User getUserById(long id);
	public List<String> getComplaintsByPhone(String phone);
	List<User> getAllUser();
	boolean istUserExistByEmail(String email);
	boolean isUserExistByPhone(String phone);
}
