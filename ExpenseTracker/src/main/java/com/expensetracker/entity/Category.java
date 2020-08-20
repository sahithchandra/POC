package com.expensetracker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "Category")
@Data
@IdClass(CategoryId.class)
public class Category {
	
	/*@Column(name="CATEGORY_ID")	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer category_Id;*/
	@Id
	@Column(name="NAME")
	private String name;
	@Column(name="BUDGET")
	private Double budget;
	@Id
	@Column(name="EMAIL")
	private String email;

}
