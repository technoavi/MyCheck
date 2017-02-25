package com.techsource.mycheck.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class MailGroup implements Runnable {
	private List<String> to;
	private String subject;
	private String m_text;

	public MailGroup(List<String> to,String subject, String m_text) {
		this.to = to;
		this.m_text = m_text;
		//this.usernames = usernames;
		//this.passwords = passwords;
		this.subject = subject;

	}

	@Override
	public void run() {
		

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("javatest.mail11@gmail.com", "testmail");
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
		//	message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
		//	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		
			//message.setFrom(new InternetAddress(fromEmail, fromName));
              for (String email : to) {
            	  message.addRecipient(Message.RecipientType.TO,
                                      new InternetAddress(email));
              } 
			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(m_text);

			//TODO enable in production
			// Send message
		Transport.send(message);

			System.out.println("Sent message successfully....");
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}

