package com.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.dto.EmailInputDTO;
import com.email.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendEmails")
	public ResponseEntity<?> sendEmails(@RequestBody EmailInputDTO emailInputDTO){
		
		emailService.sendEmails(emailInputDTO);
		
		return new ResponseEntity<String>("Mails delivered Successfully", HttpStatus.OK);
		
	}
}
