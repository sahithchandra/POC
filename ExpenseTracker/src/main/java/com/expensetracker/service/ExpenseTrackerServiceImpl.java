package com.expensetracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.expensetracker.dao.ExpenseTrackerDAO;
import com.expensetracker.entity.AddFriendsDTO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.CategoryVO;
import com.expensetracker.entity.SplitExpensesDTO;
import com.expensetracker.entity.User;
import com.expensetracker.entity.UserDTO;
import com.expensetracker.util.EmailUtil;

@Service
public class ExpenseTrackerServiceImpl implements ExpenseTrackerService {

	@Autowired
	ExpenseTrackerDAO expenseTrackerDAO;

	@Autowired
	private Environment environment;

	@Autowired
	EmailUtil emailUtil;

	@Override
	public User signUp(UserDTO userDTO) throws Exception {

		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setUserName(userDTO.getUsername());
		return expenseTrackerDAO.signUp(user);
	}

	@Override
	public User signIn(UserDTO userDTO) throws Exception {
		return expenseTrackerDAO.signIn(userDTO.getEmail());
	}

	@Override
	public List<String> getCategories() throws Exception {
		List<String> categoriesList = new ArrayList<>();
		String categories = environment.getProperty("categoriesList");
		for (String split : categories.split(",")) {
			categoriesList.add(split);
		}
		return categoriesList;
	}

	/*
	 * @Override public void updateBudget(CategoryDTO categoryDTO) throws Exception
	 * { Category category = new Category(); category.setCategoryId(6);
	 * category.setBudget(categoryDTO.getBudget());
	 * category.setName(categoryDTO.getName());
	 * category.setEmail(categoryDTO.getEmail());
	 * System.out.println("details:"+categoryDTO.getBudget()+" "+categoryDTO.getName
	 * ()+" "+categoryDTO.getEmail());
	 * expenseTrackerDAO.updateBudget(categoryDTO.getBudget(),categoryDTO.getName(),
	 * categoryDTO.getEmail()); }
	 */

	/*
	 * @Override public List<Category> getCategoryById() throws Exception { return
	 * expenseTrackerDAO.getCategoryById(5); }
	 */
	@Override
	public int setBudget(CategoryDTO categoryDTO) throws Exception {
		int count = 0;
		Category category = new Category();
		category.setEmail(categoryDTO.getEmail());
		for (CategoryVO categoryVO : categoryDTO.getCategories()) {
			category.setName(categoryVO.getName());
			category.setBudget(categoryVO.getBudget());
			Category savedCategory = expenseTrackerDAO.saveCategory(category);
			if (null != savedCategory) {
				count++;
			}
		}
		return count;
	}

	@Override
	public List<String> addFriends(AddFriendsDTO addFriendsDTO) throws Exception {
		List<String> registeredEmails = new ArrayList<>();
		for (String email : addFriendsDTO.getEmails()) {
			if (null != expenseTrackerDAO.checkUserEmail(email)) {
				registeredEmails.add(email);
			}
		}
		return registeredEmails;
	}

	@Override
	public void sendInvites(AddFriendsDTO addFriendsDTO) throws Exception {

		String friendsEmail = addFriendsDTO.getEmail();
		for (String email : addFriendsDTO.getEmails()) {
			emailUtil.sendEmail(email, "Invitation", friendsEmail + " has invited to join in their group");
		}
	}

	@Override
	public Boolean splitExpenses(SplitExpensesDTO splitExpensesDTO) throws Exception {

		Category category = null;
		Category updatedCategory = new Category();
		Category updatedOwnerCategory = new Category();
		boolean updatedFlag = false;
		int friendsCount = splitExpensesDTO.getEmails().size() + 1;
		Double splitExpense = splitExpensesDTO.getBudget() / friendsCount;
		for (String email : splitExpensesDTO.getEmails()) {
			category = expenseTrackerDAO.checkCategoryEmail(email, splitExpensesDTO.getName());
			if (null != category) {
				updatedCategory.setBudget(category.getBudget() - splitExpense);
				updatedCategory.setEmail(category.getEmail());
				updatedCategory.setName(category.getName());
				Category saveCategory = expenseTrackerDAO.saveCategory(updatedCategory);
				if (null != saveCategory) {
					updatedFlag = true;
				}
			} else {
				updatedFlag = false;
			}
		}
		Category ownerCategory = expenseTrackerDAO.checkCategoryEmail(splitExpensesDTO.getEmail(),
				splitExpensesDTO.getName());
		if (null != ownerCategory) {
			updatedOwnerCategory.setBudget(ownerCategory.getBudget() - splitExpense);
			updatedOwnerCategory.setEmail(ownerCategory.getEmail());
			updatedOwnerCategory.setName(ownerCategory.getName());
			Category saveOwnerCategory = expenseTrackerDAO.saveCategory(updatedOwnerCategory);
			if (null != saveOwnerCategory) {
				updatedFlag = true;
			}
		} else {
			updatedFlag = false;
		}
		return updatedFlag;
	}

	@Override
	public int deleteBudget(CategoryDTO categoryDTO) throws Exception {

		return expenseTrackerDAO.deleteBudget(categoryDTO.getEmail(), categoryDTO.getCategories().get(0).getName(),categoryDTO.getCategories().get(0).getBudget());

	}

	@Override
	public List<Category> retriveCategories(String email) {
		
		return expenseTrackerDAO.retriveCategories(email);
	}

}
