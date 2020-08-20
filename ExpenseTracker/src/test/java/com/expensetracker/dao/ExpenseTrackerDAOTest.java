package com.expensetracker.dao;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTrackerDAOTest {

	@InjectMocks
	ExpenseTrackerDAO expenseTrackerDAO;

	@Mock
	ExpenseTrackerRepository expenseTrackerRepository;

	@Test
	public void signUp() throws Exception {

		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("123");
		actualUser.setUserName("hello");
		when(expenseTrackerRepository.save(actualUser)).thenReturn(actualUser);
		User expectedUser = expenseTrackerDAO.signUp(actualUser);
		Assert.assertEquals(expectedUser.getUserName(), actualUser.getUserName());

	}

	@Test
	public void signIn() throws Exception {

		User actualUser = new User();
		actualUser.setEmail("acb@gmail.com");
		actualUser.setPassword("123");
		actualUser.setUserName("hello");
		when(expenseTrackerRepository.signIn(actualUser.getEmail())).thenReturn(actualUser);
		User expectedUser = expenseTrackerDAO.signIn(actualUser.getEmail());
		Assert.assertEquals(expectedUser.getUserName(), actualUser.getUserName());

	}

}
