package com.expensetracker.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import com.expensetracker.dao.ExpenseTrackerDAO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.CategoryVO;
import com.expensetracker.entity.User;
import com.expensetracker.entity.UserDTO;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTrackerServiceImplTest {

	@InjectMocks
	ExpenseTrackerServiceImpl expenseTrackerServiceImpl;

	@Mock
	ExpenseTrackerDAO expenseTrackerDAO;

	@Mock
	Environment environment;

	@Test
	public void signUp() throws Exception {

		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("123");
		actualUser.setUserName("hello");

		when(expenseTrackerDAO.signUp(actualUser)).thenReturn(actualUser);

		User expectedUser = expenseTrackerServiceImpl.signUp(userInput);

		Assert.assertEquals(expectedUser.getUserName(), actualUser.getUserName());

	}

	@Test
	public void signIn() throws Exception {

		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("acb@gmail.com");
		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("123");
		actualUser.setUserName("hello");

		when(expenseTrackerDAO.signIn(userDTO.getEmail())).thenReturn(actualUser);
		User expectedUser = expenseTrackerServiceImpl.signIn(userDTO);
		Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());

	}

	@Test
	public void getCategories() throws Exception {

		String categories = "Food,Health,Environment";
		when(environment.getProperty("categoriesList")).thenReturn(categories);

		List<String> expectedList = expenseTrackerServiceImpl.getCategories();
		List<String> categoriesList = new ArrayList<String>();
		categoriesList.add("Entertainment");
		categoriesList.add("Food");
		categoriesList.add("Health");

		Assert.assertEquals(categoriesList.size(), expectedList.size());
	}

	@Test
	public void setBudget() throws Exception {
		CategoryDTO categoryDTO= new CategoryDTO();
		categoryDTO.setEmail("test@gmail.com");
		List<CategoryVO> categoryVOList= new ArrayList<>();
		CategoryVO categoryVO= new CategoryVO();
		categoryVO.setName("food");
		categoryVO.setBudget(20.5);
		categoryVOList.add(categoryVO);
		categoryDTO.setCategories(categoryVOList);
		
		Category category= new Category();		
		category.setBudget(20.5);		
		category.setEmail("test@gmail.com");
		category.setName("food");
		
		Category expcategory= new Category();		
		expcategory.setBudget(20.5);		
		expcategory.setEmail("test@gmail.com");
		expcategory.setName("food");
		
		when(expenseTrackerDAO.saveCategory(category)).thenReturn(expcategory);		
		
		expenseTrackerServiceImpl.setBudget(categoryDTO);
		Assert.assertEquals(expcategory.getBudget(), new Double(20.5));
		
		
	}
	
	@Test
	public void setBudgetNull() throws Exception {
		CategoryDTO categoryDTO= new CategoryDTO();
		categoryDTO.setEmail("test@gmail.com");
		List<CategoryVO> categoryVOList= new ArrayList<>();
		CategoryVO categoryVO= new CategoryVO();
		categoryVO.setName("food");
		categoryVO.setBudget(20.5);
		categoryVOList.add(categoryVO);
		categoryDTO.setCategories(categoryVOList);
		
		Category category= new Category();		
		category.setBudget(20.5);		
		category.setEmail("test@gmail.com");
		category.setName("food");
		
		Category expcategory= new Category();		
		expcategory.setBudget(20.5);		
		expcategory.setEmail("test@gmail.com");
		expcategory.setName("food");
		
		when(expenseTrackerDAO.saveCategory(category)).thenReturn(null);		
		
		expenseTrackerServiceImpl.setBudget(categoryDTO);		
		
		
	}

}