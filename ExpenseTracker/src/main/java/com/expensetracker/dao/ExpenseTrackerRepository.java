package com.expensetracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.expensetracker.entity.User;

@Repository
public interface ExpenseTrackerRepository extends JpaRepository<User, Integer> {

	@Query("Select u from User u where u.email=:email")
	public User signIn(@Param("email") String email) throws Exception;

	
}
