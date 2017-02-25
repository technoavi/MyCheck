package com.syntaxtree.agproengg.service;

import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.model.User;

public interface LoginService {
	
	public boolean loginAuthentication(String email, String password);

	UserBO getUserByEmail(String email);

}
