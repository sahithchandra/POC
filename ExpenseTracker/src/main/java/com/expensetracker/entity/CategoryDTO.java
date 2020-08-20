package com.expensetracker.entity;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO {
	
	private String email;
	private List<CategoryVO> categories;
	

}
