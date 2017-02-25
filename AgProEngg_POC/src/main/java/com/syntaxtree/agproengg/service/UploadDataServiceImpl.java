package com.syntaxtree.agproengg.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.syntaxtree.agproengg.bo.ComplaintBO;
import com.syntaxtree.agproengg.dao.ComplaintDAO;
import com.syntaxtree.agproengg.dao.UploadDataDao;
import com.syntaxtree.agproengg.model.Complaint;
import com.syntaxtree.agproengg.model.UploadDataVO;
import com.syntaxtree.agproengg.model.UploadedData;


@Repository("uploadDataService")
public class UploadDataServiceImpl implements UploadDataService {
	private final static Logger log = LoggerFactory.getLogger(UploadDataServiceImpl.class);
public static final String COLLECTION="UploadedFiles";
	@Autowired
	UploadDataDao uploadDataDao;
	
	@Autowired
	ComplaintDAO complaintDAO;
	
	@Autowired
	 MongoTemplate mongoTemplate;
	
	
	

	public boolean saveDatas(UploadedData data) {
		
		uploadDataDao.saveDatas(data);
		return true;
			
	}
	@Override
	public UploadedData getImageAndAudioByPhone(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
		  return this.mongoTemplate.findOne(query, UploadedData.class, COLLECTION);
	}
/*	@Override
	public boolean saveData(UploadData uploadDataVO) {
		
		Random random= new Random();
		File images=null;
		File audio=null;
		
		
		images= decodeImage(uploadDataVO.getImage());
		audio=decodeAudio(uploadDataVO.getAudio());
		UploadedData uploadedData = new UploadedData();
		uploadedData.setId(String.valueOf(random.nextInt(4)));
		//uploadedData.setImage(images);
		uploadedData.setAudio(audio);
		uploadedData.setDescription(uploadDataVO.getDescription());
		boolean flag=saveData(uploadedData);
		if(flag)
		return true;
		
		else {
			return false;
		}
	}
	
	 private File decodeAudio(String base64AudioData) {
		
		 
		 
		 File fileName=null;
		 try {

		     FileOutputStream fos = new FileOutputStream(fileName);
		     fos.write(Base64.decodeBase64(base64AudioData.getBytes()));
		     fos.close();

		     return fileName;

		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		return fileName;


		 } 
	public  File decodeImage(String imageDataString) {
		File f=null;
		 try {
			
			 BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(imageDataString);
        log.debug("Decoded upload data : " + decodedBytes.length);
 
           
           
         String uploadFile = "/tmp/test.png";
         log.debug("File save path : " + uploadFile);
 
         BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
         if (image == null) {
              log.error("Buffered Image is null");
          }
         
       f  = new File(uploadFile);
 
         // write the image
         
			ImageIO.write(image, "png", f);
			return f;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
         
      }
	
	*/
public boolean saveData(UploadDataVO uploadDataService) {
	
	uploadDataDao.saveData(uploadDataService);
	return true;
}
@Override
public boolean saveData(UploadedData uploadDataVOs) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<ComplaintBO> getyAudioAndImageByPhone(String phone) {
	// TODO Auto-generated method stub
	List<ComplaintBO> listBos = new LinkedList<ComplaintBO>();
	List<UploadedData>complaints= new LinkedList<UploadedData>();
	
	List<Complaint>complaints2= new LinkedList<Complaint>();
complaints=complaintDAO.getyAudioAndImageByPhone(phone);
complaints2=complaintDAO.getComplaintByPhone(phone);
ComplaintBO bo=null;
for (UploadedData upld : complaints) {
	 bo= new ComplaintBO();
	bo.setAudio(upld.getAudio());
	bo.setImages(upld.getImages());
	for (Complaint complaint : complaints2) {
		bo.setCropname(complaint.getcropname());
		bo.setCreatedDate(complaint.getCreatedDate());
	}
	listBos.add(bo);
}




	return listBos;
}

    }

