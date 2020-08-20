package com.expensetracker.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.expensetracker.entity.AddFriendsDTO;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.CategoryVO;
import com.expensetracker.entity.SplitExpensesDTO;
import com.expensetracker.entity.User;
import com.expensetracker.entity.UserDTO;
import com.expensetracker.service.ExpenseTrackerService;
import com.expensetracker.util.EmailUtil;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ExpenseTrackerControllerTest {

	@InjectMocks
	ExpenseTrackerController expenseTrackerController;

	@Mock
	ExpenseTrackerService expenseTrackerService;
	
	@Mock
	EmailUtil emailUtil;

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
		
		doNothing().when(emailUtil).sendEmail("email", "subject", "body");
		when(expenseTrackerService.signUp(userInput)).thenReturn(actualUser);

		ResponseEntity<?> responseEntity = expenseTrackerController.signUp(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void signUpUserAlreadyExists() throws Exception {

		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		when(expenseTrackerService.signUp(userInput)).thenThrow(new Exception());

		ResponseEntity<?> responseEntity = expenseTrackerController.signUp(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void signUpNegative() throws Exception {

		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		when(expenseTrackerService.signUp(userInput)).thenReturn(null);

		ResponseEntity<?> responseEntity = expenseTrackerController.signUp(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void signIn() throws Exception {
		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("123");
		actualUser.setUserName("hello");

		when(expenseTrackerService.signIn(userInput)).thenReturn(actualUser);

		ResponseEntity<?> responseEntity = expenseTrackerController.signin(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void signInwrongPassword() throws Exception {
		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("1234");
		actualUser.setUserName("hello");

		when(expenseTrackerService.signIn(userInput)).thenReturn(actualUser);

		ResponseEntity<?> responseEntity = expenseTrackerController.signin(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void signInNegative() throws Exception {
		UserDTO userInput = new UserDTO();
		userInput.setEmail("acb@gmail.com");
		userInput.setPassword("123");
		userInput.setUsername("hello");

		when(expenseTrackerService.signIn(userInput)).thenThrow(new Exception());

		ResponseEntity<?> responseEntity = expenseTrackerController.signin(userInput);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	public void getCategories() throws Exception {
		
		List<String> categoriesList= new ArrayList<String>();
		categoriesList.add("Entertainment");
		categoriesList.add("Food");
		categoriesList.add("Health");
		categoriesList.add("Housing");
		

		when(expenseTrackerService.getCategories()).thenReturn(categoriesList);

		ResponseEntity<?> responseEntity = expenseTrackerController.getCategories();

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	public void getCategoriesException() throws Exception {

		List<String> categoriesList= new ArrayList<String>();
		categoriesList.add("Entertainment");
		categoriesList.add("Food");
		categoriesList.add("Health");
		categoriesList.add("Housing");

		when(expenseTrackerService.getCategories()).thenThrow(new Exception());

		ResponseEntity<?> responseEntity = expenseTrackerController.getCategories();

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void setBudget() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		List<CategoryVO> categories = new ArrayList<>();
		CategoryVO categoryVO= new CategoryVO();
		categories.add(categoryVO);
		categoryDTO.setCategories(categories);
		when(expenseTrackerService.setBudget(categoryDTO)).thenReturn(1);
		
		ResponseEntity<?> responseEntity = expenseTrackerController.setBudget(categoryDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	public void setBudgetException() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO();
		List<CategoryVO> categories = new ArrayList<>();
		CategoryVO categoryVO= new CategoryVO();
		categories.add(categoryVO);
		categoryDTO.setCategories(categories);
		when(expenseTrackerService.setBudget(categoryDTO)).thenThrow(new Exception());
		
		ResponseEntity<?> responseEntity = expenseTrackerController.setBudget(categoryDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	public void addFriends() throws Exception {
		AddFriendsDTO addFriendsDTO = new AddFriendsDTO();
		List<String> registeredEmails= new ArrayList<>();
		registeredEmails.add("test@gmail.com");
		addFriendsDTO.setEmails(registeredEmails);
		when(expenseTrackerService.addFriends(addFriendsDTO)).thenReturn(registeredEmails);
		
		ResponseEntity<?> responseEntity = expenseTrackerController.addFriends(addFriendsDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	public void addFriendsException() throws Exception {
		AddFriendsDTO addFriendsDTO = new AddFriendsDTO();
		List<String> registeredEmails= new ArrayList<>();
		registeredEmails.add("test@gmail.com");
		addFriendsDTO.setEmails(registeredEmails);
		when(expenseTrackerService.addFriends(addFriendsDTO)).thenThrow(new Exception());
		
		ResponseEntity<?> responseEntity = expenseTrackerController.addFriends(addFriendsDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}
	
	
	@Test
	public void sendInvites() throws Exception {
		AddFriendsDTO addFriendsDTO = new AddFriendsDTO();
		List<String> registeredEmails= new ArrayList<>();
		registeredEmails.add("test@gmail.com");
		addFriendsDTO.setEmails(registeredEmails);
		doNothing().when(expenseTrackerService).sendInvites(addFriendsDTO);	
		
		ResponseEntity<?> responseEntity = expenseTrackerController.sendInvites(addFriendsDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}	
	
	@Test
	public void splitExpensesTrue() throws Exception {
		SplitExpensesDTO splitExpensesDTO= new SplitExpensesDTO();
		when(expenseTrackerService.splitExpenses(splitExpensesDTO)).thenReturn(true);
		ResponseEntity<?> responseEntity = expenseTrackerController.splitExpenses(splitExpensesDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void splitExpensesFalse() throws Exception {
		SplitExpensesDTO splitExpensesDTO= new SplitExpensesDTO();
		when(expenseTrackerService.splitExpenses(splitExpensesDTO)).thenReturn(false);
		ResponseEntity<?> responseEntity = expenseTrackerController.splitExpenses(splitExpensesDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void splitExpensesException() throws Exception {
		SplitExpensesDTO splitExpensesDTO= new SplitExpensesDTO();
		when(expenseTrackerService.splitExpenses(splitExpensesDTO)).thenThrow(new Exception());
		ResponseEntity<?> responseEntity = expenseTrackerController.splitExpenses(splitExpensesDTO);

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}


}
