package com.email.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmailInputDTO {
	
	private List<String> emails;
	private String subject;
	private String body;
	private String filePath;
	

}
