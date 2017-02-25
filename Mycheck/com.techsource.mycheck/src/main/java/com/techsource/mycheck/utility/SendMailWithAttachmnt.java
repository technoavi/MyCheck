package com.techsource.mycheck.utility;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

public class SendMailWithAttachmnt {
   // public static void main(String[] args) {
	
   public static boolean sendMailWithAttachmnt(String too, String cname, File file, HttpServletRequest request) 
		   throws FileNotFoundException{
      // Recipient's email ID needs to be mentioned.
      String to = too;
     
    String from = "javatest.mail11@gmail.com";//change accordingly
      final String username = "javatest.mail11@gmail.com";//change accordingly
      final String password = "testmail";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "relay.jangosmtp.net";

 Properties props = System.getProperties();  
	props.put("mail.smtp.user", username);
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "465");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.debug", "true");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.socketFactory.fallback", "false"); 

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {
    	  
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject("MyCheck Report");

         // Create the message part
         BodyPart messageBodyPart = new MimeBodyPart();

         // Now set the actual message
         messageBodyPart.setText("Hi "+cname+"!!! \n\n Your Report for this Quater,  PFA. \n\nThanks, "
         		+ "\nMyCheck Team.");

         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         
         // to get the real path 
        // String filename1 ="MyCheckReport.pdf";
       /*  InputStream is = null ;
         String realPath  = request.getSession().getServletContext().getRealPath("/WEB-INF/jasper/"+filename1);
         is = new FileInputStream(realPath);
         System.out.println("path isssss "+realPath);*/
            
         
         
         
         
         DataSource source = new FileDataSource(file);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(cname+" MyCheckReport.pdf");
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart);

         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");
  
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
return true;}
}
