package com.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.email.dto.EmailInputDTO;
import com.email.util.EmailUtil;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailUtil emailUtil;

	@Override
	public void sendEmails(EmailInputDTO emailInputDTO) {

		//emailUtil.sendEmail(emailInputDTO);
		
		  for (String email : emailInputDTO.getEmails()) { emailUtil.sendEmail(email,
		  emailInputDTO.getFilePath(), emailInputDTO.getBody(),
		  emailInputDTO.getSubject()); }
		 
	}

}
