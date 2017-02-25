package com.syntaxtree.agproengg.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.syntaxtree.agproengg.dao.ComplaintDAO;
import com.syntaxtree.agproengg.dao.SequenceDao;
import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.Complaints;
import com.syntaxtree.agproengg.model.FileInfo;
import com.syntaxtree.agproengg.model.FileUpload;
import com.syntaxtree.agproengg.model.UploadDataVO;
import com.syntaxtree.agproengg.model.UploadedData;
import com.syntaxtree.agproengg.service.UploadDataService;

@RestController
@RequestMapping("/uploadData")
public class UploadFilesRestController {
	private final Logger logger = Logger.getLogger(UploadFilesRestController.class);

	@Autowired
	UploadDataService uploadDataService;
	
	@Autowired
	ComplaintDAO complaintDAO;
	
	@Autowired
	 MongoTemplate mongoTemplate;
	@Autowired
	private SequenceDao sequenceDao;
	@Autowired
	GridFsTemplate gridFsTemplate;
	
	private static final String HOSTING_SEQ_KEY = "hosting";
	private static final String FILEUPLOAD = "FileUpload"; 
	@Autowired
	 ServletContext context;

	 @RequestMapping(value = "/fileupload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	 public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile) {
	  FileInfo fileInfo = new FileInfo();
	  HttpHeaders headers = new HttpHeaders();
	  if (!inputFile.isEmpty()) {
	   try {
	    String originalFilename = inputFile.getOriginalFilename();
	    File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded")+  File.separator + originalFilename);
	    inputFile.transferTo(destinationFile);
	    fileInfo.setFileName(destinationFile.getPath());
	    fileInfo.setFileSize(inputFile.getSize());
	    headers.add("File Uploaded Successfully - ", originalFilename);
	    return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
	   } catch (Exception e) {    
	    return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
	   }
	  }else{
	   return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
	  }
	 }
	
	@RequestMapping(value = "/uploads", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
	public String uploadFiles(
			  @FormParam("imagefile") InputStream imageFile,
			  @FormParam("audioFile") InputStream audioFile,
			  @FormParam("imageName") String imageName,
			  @FormParam("audioName") String audioName,
			  @FormParam("description") String description,
			  @FormParam("phone") String phone,
			  
			
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		FileUpload files=null;
try{
	InetAddress ipAddr = InetAddress.getLocalHost();
	String imgPath=null;
	String audioPath=null;
	imgPath=imageName+ipAddr.getHostAddress();
	audioPath=audioName+ipAddr.getHostAddress();

	
String ids=	gridFsTemplate.store(imageFile, imgPath).getId().toString();
	gridFsTemplate.store(audioFile, audioPath).getId().toString();
	System.out.println("999999999"+ids);
 files= new FileUpload();
	files.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
	files.setImgName(imageName);
	files.setImagePath(imgPath);
	files.setAudioName(audioName);
	files.setAudioPath(audioPath);
	files.setPhone(phone);
	files.setDescription(description);
	files.setCreatedDate(new Date());
	
	
	this.mongoTemplate.insert(files,FILEUPLOAD);
	
	}catch(Exception e){
	e.printStackTrace();
}
return new Gson().toJson(files);

}
	
	
	/*@GET
	@Path("/download/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)*/
	@RequestMapping(value = "/download",headers="Accept=application/json", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public Response downloadFilebyID(@PathParam("id")  String id) 
	          throws IOException {
	  
	  Response response = null;
	  MongoClient mongoClient = new MongoClient("localhost", 27017);
	  DB mongoDB = mongoClient.getDB("agproengg_poc");

	  logger.info("Inside downloadFilebyID...");
	  logger.info("ID: " + id);

	  BasicDBObject query = new BasicDBObject();
	  query.put("_id", id);
	  GridFS fileStore = new GridFS(mongoDB, "fs");
	  GridFSDBFile gridFile = fileStore.findOne(query);

	  if (gridFile != null && id.equalsIgnoreCase((String)gridFile.getId())) {
	    logger.info("ID...........: " + gridFile.getId());
	    logger.info("FileName.....: " + gridFile.getFilename());
	    logger.info("Length.......: " + gridFile.getLength());
	    logger.info("Upload Date..: " + gridFile.getUploadDate());
	    
	    InputStream in = gridFile.getInputStream();
	        
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int data = in.read();
	    while (data >= 0) {
	      out.write((char) data);
	      data = in.read();
	    }
	    out.flush();

	    ResponseBuilder builder = Response.ok(out.toByteArray());
	    
	    builder.header("Content-Disposition", "attachment; filename=" 
	             + gridFile.getFilename());
	    response = builder.build();
	    } else {
	      response = Response.status(404).
	        entity(" Unable to get file with ID: " + id).
	        type("text/plain").
	        build();
	    }
	      
	  return response;
	}	
	
	
	
	
	
	
	
	
	
/*	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)*/
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(
			 @FormParam("id") String id, @FormParam("imagefile") InputStream imageFile,
			 @FormParam("imagefile") InputStream audiofile,
			@QueryParam("description") String description,
			@QueryParam("phone") String phone,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
try{
	
/*		Image image = null;
		
		InputStream is = new BufferedInputStream(
		       imageFile);
		      image = ImageIO.read(is);*/
		UploadedData uploadedDataVO = new UploadedData();
	

		//uploadedDataVO.setId(" " + System.currentTimeMillis()); getImageAndAudioByPhone
		uploadedDataVO.setAudio(readFully(audiofile));
		uploadedDataVO.setImages(readFully(imageFile));
		uploadedDataVO.setDescription(description);
		uploadedDataVO.setPhone(phone);
		boolean flag=uploadDataService.saveDatas(uploadedDataVO);
if(flag){
	List<Complaint> complaints=	complaintDAO.getComplaintByPhone(phone);


	String data = "{\"count\":" + complaints.size() + ",\"data\":" + new Gson().toJson(complaints) + "}";
		result = "{\"StatusMessage\":\"" + "Data has been uploaded successfully" + "\",\"ResponseData\":\""
				+ data + "\"}";
} else {
	result = "{\"StatusMessage\":\"" + "User was already created by using " 
			+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
	logger.info("addDAta  failed");

}
} catch (Exception e) {
result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
	+ "\"}";
}

return result;
	
	}
	// to convert file into byte[]
	public static byte[] readFully(InputStream input) throws IOException{
		byte[] buffer = new byte[8192];
	    int bytesRead;
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    while ((bytesRead = input.read(buffer)) != -1)
	    {
	        output.write(buffer, 0, bytesRead);
	    }
	    return output.toByteArray();
		
	}
	@RequestMapping(value = "/addData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public @ResponseBody String createUser(@RequestParam("jsonData") String jsonData,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		UploadDataVO uploadDataVOs = null;
		try {
			uploadDataVOs = this.prepareRegisterJsonData(jsonData);

			if (uploadDataVOs != null) {
				boolean flag =false;
					//	uploadDataService.saveData(uploadDataVOs);
				System.out.println("flag " + flag);
				if (flag) {
				

					result = "{\"StatusMessage\":\"" + "Data has been uploaded successfully"
							+ "\"\"ResponseStatus\":\"" + "1 " + "\"}";
					logger.info("addData  uploaded");
				} else {
					result = "{\"StatusMessage\":\"" + "User was already created by using " 
							+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
					logger.info("addDAta  failed");
				}
			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}

	private UploadDataVO prepareRegisterJsonData(String jsonData) {

		JSONObject jsonObject = new JSONObject(jsonData);
		UploadDataVO uploadDataVO = new UploadDataVO();
		
		uploadDataVO.setId(jsonObject.getString("id"));
		uploadDataVO.setImage(jsonObject.getString("image"));
		uploadDataVO.setAudio(jsonObject.getString("audio"));
		uploadDataVO.setDescription(jsonObject.getString("description"));

		return uploadDataVO;
	}
	private Complaints prepareComplaintJsonData(String jsonData) {

		JSONObject jsonObject = new JSONObject(jsonData);
		Complaints uploadDataVO = new Complaints();
		
		uploadDataVO.setName(jsonObject.getString("name"));
		uploadDataVO.setDescription(jsonObject.getString("description"));
		uploadDataVO.setPhone(jsonObject.getString("phone"));
		uploadDataVO.setComplaintDate(new Date(jsonObject.getString("phone")));

		return uploadDataVO;
	}
	//

	@RequestMapping(value = "/uploadImageJson", method = RequestMethod.POST)
	public @ResponseBody Object jsongStrImage(@RequestParam(value = "image") MultipartFile image,
			@RequestParam String jsonStr) {
		return jsonStr;
		// -- use com.fasterxml.jackson.databind.ObjectMapper convert Json
		// String to Object
	}
	@RequestMapping(value = "/registerComplaint", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public @ResponseBody String registerComplaint(@RequestParam("jsonData") String jsonData,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("insertEmailGroup method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		UploadedData uploadDataVOs = null;
		try {
		//	uploadDataVOs = this.prepareComplaintJsonData(jsonData);

			if (uploadDataVOs != null) {
				boolean flag =uploadDataService.saveData(uploadDataVOs);
				System.out.println("flag " + flag);
				if (flag) {
				

					result = "{\"StatusMessage\":\"" + "Data has been uploaded successfully"
							+ "\"\"ResponseStatus\":\"" + "1 " + "\"}";
					logger.info("addData  uploaded");
				} else {
					result = "{\"StatusMessage\":\"" + "User was already created by using " 
							+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
					logger.info("addDAta  failed");
				}
			}
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
	}
	@RequestMapping(value = "/getImgAndAudioByPhone", method = RequestMethod.POST)
	@ResponseBody String getImgAndAudioByPhone(@RequestParam("phone") String phone,
			HttpServletRequest request, HttpServletResponse response) {
		
		String result=null;
		try{
	UploadedData  complaints=uploadDataService.getImageAndAudioByPhone(phone);
		System.out.println(complaints);
		if (complaints!=null) {
	
	
				result = "{\"StatusMessage\":\"" + "New user has been created successfully" + "\",\"ResponseStatus\":\""
						+ new Gson().toJson(complaints) + "\"}";
				logger.info("addUser  created");
			} else {
				result = "{\"StatusMessage\":\"" + "User was already created by using " + complaints
						+ "\",\"ResponseStatus\":\"" + " 0 " + "\"}";
				logger.info("addUser  failed");
			}
	
		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;
}
}
