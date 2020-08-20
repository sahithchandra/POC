package com.expensetracker.service;

import java.util.List;

import com.expensetracker.entity.AddFriendsDTO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.SplitExpensesDTO;
import com.expensetracker.entity.User;
import com.expensetracker.entity.UserDTO;

public interface ExpenseTrackerService {

	public User signUp(UserDTO userDTO) throws Exception;

	public User signIn(UserDTO userDTO) throws Exception;	
	
	public List<String> getCategories() throws Exception;	
	
	//public void updateBudget(CategoryDTO categoryDTO) throws Exception;
	
	public int setBudget(CategoryDTO categoryDTO)throws Exception;

	//	public List<Category> getCategoryById()throws Exception;

	public List<String> addFriends(AddFriendsDTO addFriendsDTO) throws Exception;

	public void sendInvites(AddFriendsDTO addFriendsDTO)throws Exception;

	public Boolean splitExpenses(SplitExpensesDTO splitExpensesDTO)throws Exception;

	public int deleteBudget(CategoryDTO categoryDTO)throws Exception;

	public List<Category> retriveCategories(String email);
		
}
