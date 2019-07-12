package com.ez.herb.email;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired private JavaMailSender mailSender;
	
	public void sendEmail(String subject, String content, String recipient, String sender) throws MessagingException {
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		mimeMessage.setSubject(subject);
		mimeMessage.setText(content);
		mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		mimeMessage.setFrom(new InternetAddress(sender));
		
		mailSender.send(mimeMessage);
		
	}
}
