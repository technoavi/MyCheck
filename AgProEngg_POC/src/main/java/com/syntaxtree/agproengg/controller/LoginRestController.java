package com.syntaxtree.agproengg.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.syntaxtree.agproengg.bo.UserBO;
import com.syntaxtree.agproengg.model.User;
import com.syntaxtree.agproengg.service.LoginService;
import com.syntaxtree.agproengg.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	private static Logger logger = Logger.getLogger(LoginRestController.class);
	
	
	 
	 @Autowired
	 LoginService loginService; 

	/*@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loginAuthenticatoin(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		
		fuser -n tcp 8083

pkill -9 -f mysql

		return "";
	}*/
	 
	 
	@RequestMapping(value = "/loginUser", method =  RequestMethod.POST , consumes = MediaType.APPLICATION_JSON)
	public @ResponseBody String loginUser(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
	logger.info("loginUser  started");

		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\"}";
		try {
			boolean falg = loginService.loginAuthentication(email, password);
		
			if (falg) {
				UserBO userBO=loginService.getUserByEmail(email);
				
				/*String data =  "{\"ResponseMessage\":\"" + "failure"+ ",\"data\":" + new Gson().toJson(user) + "}";
				result = "{\"StatusMessage\":\"" + "Login  successfully" + "\",\"ResponseData\":\""
						+ data + "\"}";*/
				
				String data = "{\"data\":" + new Gson().toJson(userBO) + "}";
				result = "{\"StatusCode\":\"1\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
				
				logger.info("login Success");
			} else {
				result = " {\"StatusCode\":\"0\",\"StatusMessage\":\"" + "failure" + "\",\"ResponseData\":\"" + 
						"USER_LOGIN_FAILURE" + "\"}";
				logger.info("login Failed");
				
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
			logger.debug("Login Exception"+e.getMessage());
		}

		return result;

	}
	// not used
	
	  @POST
	  @Path("/upload")
	  @Consumes(MediaType.MULTIPART_FORM_DATA)
	 // @Produces("text/html")
	  public Response uploadFile(
	      @FormDataParam("file") InputStream fileInputStream,
	      @FormDataParam("file") FormDataContentDisposition fileInputDetails,
	      @FormDataParam("id") String  id,
	      @FormDataParam("description") String  description
	) throws UnknownHostException {
	  
	    MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
	    DB mongoDB = mongoClient.getDB("agproengg_poc");
	     
	    //Let's store the standard data in regular collection
	    DBCollection collection = mongoDB.getCollection("UploadedFiles");
	     
	    // Let's query to ensure ID does not already exist in Mongo
	    // if it does, we will alert the user 
	    BasicDBObject query = new BasicDBObject();
	    query.put("_id", id);
	    DBCursor cursor = collection.find(query);
	     
	    if (!cursor.hasNext()) {
	      // Build our document and add all the fields
	      BasicDBObject document = new BasicDBObject();
	      document.append("_id", id);
	      document.append("description", description);
	      document.append("filename", fileInputDetails.getFileName());
	       
	      //insert the document into the collection 
	      collection.insert(document);
	       
	      // Now let's store the binary file data using filestore GridFS  
	      GridFS fileStore = new GridFS(mongoDB, "filestore");
	      GridFSInputFile inputFile = fileStore.createFile(fileInputStream);
	      inputFile.setId(id);
	      inputFile.setFilename(fileInputDetails.getFileName());
	      inputFile.save();
	           
	      String status = "Upload has been successful";
	     
	      logger.info("ID: " + id);
	      logger.info("description: " + description);
	      logger.info("fileInputDetails: " + fileInputDetails);
	       
	      return Response.status(200).entity(status).build();
	    } else {
	      String status = "Unable to insert record with ID: " + id +" as record already exists!!!";
	      logger.info(status);
	      return Response.status(200).entity(status).build();
	    }
	  }
	
}
