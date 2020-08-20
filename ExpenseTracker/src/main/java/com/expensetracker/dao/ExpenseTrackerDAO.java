package com.expensetracker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.CategoryDTO;
import com.expensetracker.entity.User;

@Component
public class ExpenseTrackerDAO {

	@Autowired
	ExpenseTrackerRepository expenseTrackerRepository;
	
	
	@Autowired
	CategoryRepository categoryRepository;

	public User signUp(User user) throws Exception {
		return expenseTrackerRepository.save(user);
	}

	public User signIn(String email) throws Exception {
		return expenseTrackerRepository.signIn(email);

	}
	
	public List<Category> getCategories() throws Exception {		
		return categoryRepository.getCategories();		
	}
	
	
	/*public void updateBudget(Double budget,String name,String email) throws Exception {
		//System.out.println(budget+" "+name+" "+email);
		 categoryRepository.updateBudget(budget,email,name);

	}*/

	/*public List<Category> getCategoryById(Integer id) throws Exception {
		//return categoryRepository.getCategoryById(id,"food");
		return categoryRepository.getCategories();
	}*/

	public Category saveCategory(Category category) throws Exception {
		return categoryRepository.save(category);
		
	}

	public User checkUserEmail(String email) throws Exception {
		return expenseTrackerRepository.signIn(email);
		
	}
	
	public Category checkCategoryEmail(String email,String name) throws Exception {
		return categoryRepository.checkEmail(email,name);
		
	}

	public int deleteBudget(String email, String name,Double budget) {
		return categoryRepository.deleteBudget(email,name,budget);
	}

	public List<Category> retriveCategories(String email) {
		
		return categoryRepository.retriveCategories(email);
	}
	

}
