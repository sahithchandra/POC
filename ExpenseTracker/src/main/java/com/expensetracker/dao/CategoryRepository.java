package com.expensetracker.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{
	
	@Query("Select c from Category c group by c.name")
	public List<Category> getCategories() throws Exception;
		
	
	@Query("Select c from Category c where c.email=:email and c.name=:name")
	public Category checkEmail(@Param("email") String email,@Param("name") String name) throws Exception;

	@Transactional
	@Modifying
	@Query("DELETE FROM Category c WHERE c.email = :email and c.name = :name and c.budget=:budget")
	public int deleteBudget(@Param("email")String email,@Param("name") String name,@Param("budget") Double budget);


	@Query("Select c from Category c where c.email=:email")
	public List<Category> retriveCategories(@Param("email") String email);
	
	
	
	/*@Query(value="Select * from Category  where category_Id=:category_Id and name=:name",nativeQuery=true)
	public Category getCategoryById(@Param("category_Id") Integer category_Id,@Param("name") String name) throws Exception;*/
	
	
	/*@Transactional
	@Modifying
	@Query("UPDATE Category c SET c.budget = :budget WHERE c.email = :email and c.name = :name")
	public void updateBudget(@Param("budget") Double budget,@Param("email") String email,@Param("name") String name);*/
}
