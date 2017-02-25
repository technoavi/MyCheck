package com.syntaxtree.agproengg.dao;

import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.model.User;

public interface LoginDao {

	User getUserByEmail(String email);

}
