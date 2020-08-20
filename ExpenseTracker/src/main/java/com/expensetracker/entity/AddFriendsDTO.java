package com.expensetracker.entity;

import java.util.List;

import lombok.Data;

@Data
public class AddFriendsDTO {
	
	private String email;
	private List<String> emails;

}
