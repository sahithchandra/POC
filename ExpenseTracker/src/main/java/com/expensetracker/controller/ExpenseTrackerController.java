package com.expensetracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.entity.AddFriendsDTO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.CategoryVO;
import com.expensetracker.entity.Response;
import com.expensetracker.entity.SplitExpensesDTO;
import com.expensetracker.entity.User;
import com.expensetracker.entity.UserDTO;
import com.expensetracker.service.ExpenseTrackerService;
import com.expensetracker.util.EmailUtil;

@RestController
@EnableJpaRepositories(basePackages = "com.expensetracker.dao")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseTrackerController {

	@Autowired
	ExpenseTrackerService expenseTrackerService;

	@Autowired
	EmailUtil emailUtil;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {

		System.out.println("Received UserName for Registering:" + userDTO.getUsername());
		System.out.println("Received Email for Registering:" + userDTO.getEmail());

		Response response = new Response();

		User user = null;
		try {
			user = expenseTrackerService.signUp(userDTO);
		} catch (Exception e) {
			// 1-> Email_Id Already exists.Please choose a different one !!
			System.err.println(" Email_Id Already exists.Please choose a different one !!");
			response.setIndicator(false);
			response.setMessage("1");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		if (user != null) {
			// 0- success
			System.out.println("User Succesfully Registered!!");
			emailUtil.sendEmail(userDTO.getEmail(), "Welcome to Expense Tracker",
					"Welcome to Expense Tracker!! You have Succesfully registered and can track your expenses effectively using Welcome to Expense Tracker application.");
			response.setIndicator(true);
			response.setMessage("0");

		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {

		System.out.println("Received Email for Logging In:" + userDTO.getEmail());

		User user = null;
		Response response = new Response();

		try {
			user = expenseTrackerService.signIn(userDTO);
			Predicate<String> p = s -> s.equalsIgnoreCase(userDTO.getPassword());

			if (p.test(user.getPassword())) {
				System.out.println("User Succesfully Logs In!!");
				response.setIndicator(true);
				response.setMessage("0");
			} else {
				// 2- Password doesn't match!!
				System.err.println("Password doesn't match!!");
				response.setIndicator(false);
				response.setMessage("2");
			}

		} catch (Exception e) {
			// 3- email not found.Please Signup before signing in
			System.err.println("Email not found.Please Signup before logging in!!");
			response.setIndicator(false);
			response.setMessage("3");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/getCategories")
	public ResponseEntity<?> getCategories() {

		List<String> categoriesList = null;
		try {
			categoriesList = expenseTrackerService.getCategories();
		} catch (Exception e) {
			System.err.println("No Categories Found!!");
		}
		return new ResponseEntity<>(categoriesList, HttpStatus.OK);

	}

	@PostMapping("/setBudget")
	public ResponseEntity<?> setBudget(@RequestBody CategoryDTO categoryDTO) {
		Response response = new Response();
		int count = 0;
		try {
			count = expenseTrackerService.setBudget(categoryDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.setIndicator(false);
			// failed to update the budgets
			response.setMessage("4");
		}
		if (categoryDTO.getCategories().size() == count) {
			System.out.println("User's Budgets Succesfully Added!!");
			response.setIndicator(true);
			response.setMessage("0");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/addFriends")
	public ResponseEntity<?> addFriends(@RequestBody AddFriendsDTO addFriendsDTO) {
		Response response = new Response();
		List<String> registeredEmails = null;
		try {
			registeredEmails = expenseTrackerService.addFriends(addFriendsDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.setIndicator(false);
			// failed to send emails to friends
			response.setMessage("5");
		}
		return new ResponseEntity<>(registeredEmails, HttpStatus.OK);
	}

	@PostMapping("/sendInvites")
	public ResponseEntity<?> sendInvites(@RequestBody AddFriendsDTO addFriendsDTO) {
		Response response = new Response();
		try {
			expenseTrackerService.sendInvites(addFriendsDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.setIndicator(false);
			// failed to send emails to friends
			response.setMessage("6");
		}
		response.setIndicator(true);
		response.setMessage("0");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/splitExpenses")
	public ResponseEntity<?> splitExpenses(@RequestBody SplitExpensesDTO splitExpensesDTO) {
		Response response = new Response();
		try {
			Boolean splitExpenses = expenseTrackerService.splitExpenses(splitExpensesDTO);
			if (splitExpenses) {
				response.setIndicator(true);
				response.setMessage("0");
			} else {
				response.setIndicator(false);
				// failed to update the split expense either Category or email is not found
				response.setMessage("7");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.setIndicator(false);
			// failed to update the split expense either Category or email is not found
			response.setMessage("7");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/deleteBudget")
	public ResponseEntity<?> deleteBudget(@RequestBody CategoryDTO categoryDTO) {
		List<Category> categoriesList = null;
		CategoryDTO updatedcategoryDTO = new CategoryDTO();
		List<CategoryVO> categoryVOList = new ArrayList<>();
		int count = 0;
		try {
			count = expenseTrackerService.deleteBudget(categoryDTO);
			if (count > 0) {
				// retrive all categories
				categoriesList = expenseTrackerService.retriveCategories(categoryDTO.getEmail());
				for (Category category : categoriesList) {
					CategoryVO categoryVO = new CategoryVO();
					categoryVO.setBudget(category.getBudget());
					categoryVO.setName(category.getName());
					categoryVOList.add(categoryVO);
				}
				updatedcategoryDTO.setCategories(categoryVOList);				
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return new ResponseEntity<>(updatedcategoryDTO, HttpStatus.OK);
	}

	/*
	 * @PutMapping("/updateBudget") public ResponseEntity<?>
	 * updateBudget(@RequestBody CategoryDTO categoryDTO) { Category category= null;
	 * int count=0; try { expenseTrackerService.updateBudget(categoryDTO); } catch
	 * (Exception e) { System.err.println(e); } return new ResponseEntity<>(1,
	 * HttpStatus.OK); }
	 */

	/*
	 * @PostMapping("/getCategoryById") public ResponseEntity<?> getCategoryById() {
	 * 
	 * List<Category> category = null; try { category =
	 * expenseTrackerService.getCategoryById(); } catch (Exception e) {
	 * System.err.println("No Categories Found!!"); } return new
	 * ResponseEntity<>(category, HttpStatus.OK);
	 * 
	 * }
	 */

}
