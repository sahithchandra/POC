package com.email.util;

import java.io.File;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.email.dto.EmailInputDTO;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender sender;

	public void sendEmail(String email, String filePath, String body, String subject) {

		MimeMessage message = sender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(email); messageHelper.setSubject(subject);
			messageHelper.setText(body);
			messageHelper.addAttachment("Resume", new File(filePath));
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void sendEmail(EmailInputDTO emailInputDTO) {
	 * 
	 * MimeMessage message = sender.createMimeMessage();
	 * 
	 * try { MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
	 * messageHelper.setTo((InternetAddress)
	 * Arrays.asList(emailInputDTO.getEmails())); // messageHelper.setTo(email);
	 * messageHelper.setSubject(emailInputDTO.getSubject());
	 * messageHelper.setText(emailInputDTO.getBody());
	 * messageHelper.addAttachment("Resume", new File(emailInputDTO.getFilePath()));
	 * sender.send(message); } catch (MessagingException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}
