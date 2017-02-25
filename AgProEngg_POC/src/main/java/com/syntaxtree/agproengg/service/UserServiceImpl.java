package com.syntaxtree.agproengg.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.dao.SequenceDao;
import com.syntaxtree.agproengg.dao.UserDao;
import com.syntaxtree.agproengg.exception.EmailAreadyExistException;
import com.syntaxtree.agproengg.exception.PhoneException;
import com.syntaxtree.agproengg.model.Login;
import com.syntaxtree.agproengg.model.User;
import com.syntaxtree.agproengg.model.UserVO;

@Service("userService")
// @Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	MongoTemplate mongoTemplate;
	private static final String HOSTING_SEQ_KEY = "hosting";
	private static final String COLLECTION2 = "User"; 
	@Autowired
	UserDao userDao;
	
	@Autowired
	private SequenceDao sequenceDao;

	public boolean saveUser(UserVO userVO) throws EmailAreadyExistException, PhoneException {
		if(userVO!=null){
			/*Criteria criteria = new Criteria();
	        criteria.orOperator(Criteria.where("email").is(userVO.getEmail()),Criteria.where("phone").is(userVO.getPhone()));
	        Query query = new Query(criteria);

	       User user2=(User) mongoTemplate.find(query, User.class, "User");
	     
	       System.out.println(user2);*/
			boolean userPhoneExist=userDao.isUserExistByPhone(userVO.getPhone());
			if(userPhoneExist){
				boolean userEmailExist=userDao.istUserExistByEmail(userVO.getEmail());
				if(userEmailExist){
			User user = new User();
			user.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
			user.setFname(userVO.getFname());
			user.setLname(userVO.getLname());
			user.setEmail(userVO.getEmail());
			user.setPassword(userVO.getPassword());
			user.setPhone(userVO.getPhone());

				 if (user!= null) {
						Login login = new Login();
						login.setUsername(user.getEmail());
						login.setPassword(user.getPassword());
						login.setId(user.getId());

						boolean flag = userDao.saveUser(user, login);
						return true;
					} 
				 //return true;
			}
				
				else{
				throw new EmailAreadyExistException("Email Already Exist");
			}
		return true;
			}else {
			throw new PhoneException("PhoneNo  Already exist");
		}
	
	}
		return false;
		}
	
public User getUserById(long id) {
	// TODO Auto-generated method stub
	return userDao.getUserById(id);
}

public List<UserBO> getAllUserList() {
	List<UserBO> bos=new LinkedList<UserBO>();
	//	User user=null;
	UserBO bo=null;
		List<User> user=	userDao.getAllUser();
for (User user2 : user) {
	 bo= new UserBO();
	 bo.setId(user2.getId());
	bo.setFname(user2.getFname());
	bo.setLname(user2.getLname());
	bo.setEmail(user2.getEmail());
	bo.setPhone(user2.getPhone());
	bo.setStatus(user2.getStatus());
	bos.add(bo);
}
		//bos.add(bo);

		return bos;
	}
	/*@Override
	public boolean saves(User user) {
		try {
			 this.mongoTemplate.insert(user, COLLECTION2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}*/
	 @Override
	public List<String> getComplaintsByPhone(String phone) {
		// TODO Auto-generated method stub
		 return userDao.getComplaintsByPhone(phone);
	}
}
