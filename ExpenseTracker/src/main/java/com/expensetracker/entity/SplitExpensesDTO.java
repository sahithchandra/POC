package com.expensetracker.entity;

import java.util.List;

import lombok.Data;

@Data
public class SplitExpensesDTO {
	
	private String email;
	private List<String> emails;
	private String name;
	private Double budget;

}
