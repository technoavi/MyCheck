
package com.techsource.mycheck.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author technoAvi 
 *
 */


@Component
public class SendMails  {

	
	@Value("${smtp.user}")
	private static  String users;

	
	@Value("${smtp.pass}")
	private static String passs;
	
	@Value("${smtp.from}")
	private static String from;

/*	private String to;
	private String usernames;
	private String passwords;
	private String subject;

	public SendMails(String to, String usernames, String passwords,String subject) {
		this.to = to;
		this.usernames = usernames;
		this.passwords = passwords;
		this.subject = subject;

	}*/

	public static boolean sendMails(String to, String usernames, String passwords,String subject){
		System.out.println("user "+users);
		System.out.println("pas "+passs);
		System.out.println("from "+from);
		
		
		String m_text = " Hi!!!\n\n  You have requested to forgot your password. \n\n Your username is " + usernames
				+ " and \n password is " + passwords
				+ "\n\n Dont share with any one .\n\n MyCheck. \n\n";
		//String from = "javatest.mail11@gmail.com";// change accordingly
	//	final String username = "javatest.mail11@gmail.com";// change
															// accordingly
		//final String password = "testmail";// change accordingly

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
				return new PasswordAuthentication(users, passs);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(m_text);

			//TODO enable in production
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");
			return true;
		} catch (Exception e) {

			e.printStackTrace();

		}
	return false;
	}
	

}

