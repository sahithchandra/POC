package com.expensetracker.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtilImpl implements EmailUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String email, String subject, String body) {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMesageHelper= new MimeMessageHelper(mimeMessage);
		try {
			mimeMesageHelper.setTo(email);
			mimeMesageHelper.setSubject(subject);
			mimeMesageHelper.setText(body);
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}	

		javaMailSender.send(mimeMessage);
		
	}

}
