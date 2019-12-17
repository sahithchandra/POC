package com.metro.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metro.controller.MetroController;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/metro/test/applicationContext.xml");
		MetroController controller = context.getBean("metroController", MetroController.class);
		int fare = controller.getFare("Nagole", "Prakash nagar");
		((ConfigurableApplicationContext) context).close();
	}
}
