package com.upgrad.FoodOrderingApp.service.businness;

import org.springframework.beans.factory.annotation.Autowired;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;

public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private CustomerAuthDao customerAuthDao;
	
	@Autowired
	private PasswordCryptographyProvider passwordCryptographyProvider;
}
