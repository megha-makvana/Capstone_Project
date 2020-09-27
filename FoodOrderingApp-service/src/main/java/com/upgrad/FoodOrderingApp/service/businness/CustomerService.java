package com.upgrad.FoodOrderingApp.service.businness;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.Customer;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerAuthDao customerAuthDao;

	@Autowired
	private PasswordCryptographyProvider passwordCryptographyProvider;

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer saveCustomer(Customer customerEntity) throws SignUpRestrictedException {
		Customer existingCustomer = customerDao.getUserByContactNumber(customerEntity.getContactNumber());
		if (null != existingCustomer) {
			throw new SignUpRestrictedException("SGR-001",
					"This contact number is already registered! Try other contact number.");
		}

		if (customerEntity.getContactNumber().isEmpty() || customerEntity.getEmail().isEmpty()
				|| customerEntity.getFirstName().isEmpty() || customerEntity.getPassword().isEmpty()) {
			throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
		}

		if (!customerEntity.getEmail().matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}")) {
			throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
		}

		if (!customerEntity.getContactNumber().matches("[0-9]{10,}")) {
			throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
		}

		if (isWeakPassword(customerEntity.getPassword())) {
			throw new SignUpRestrictedException("SGR-004", "Weak password!");
		}

		String[] encryptedPassword = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
		customerEntity.setSalt(encryptedPassword[0]);
		customerEntity.setPassword(encryptedPassword[1]);

		return customerDao.createCustomer(customerEntity);
	}

//	Password should have atltest 
//	8 Characters
//	one digit
//	one uppercase
//	one of the following characters [#@$%&*!^]

	private boolean isWeakPassword(String password) {
		if (password.length() < 8) {
			return true;
		}
		boolean containsDigit = false;
		boolean containsUpperCase = false;

		for (char c : password.toCharArray()) {
			if (Character.isDigit(c)) {
				containsDigit = true;
			}
			if (Character.isUpperCase(c)) {
				containsUpperCase = true;
			}
		}
		if (!containsDigit || !containsUpperCase) {
			return true;
		}

		Pattern myPattern = Pattern.compile("[#@$%&*!^]", Pattern.CASE_INSENSITIVE);
		Matcher myMatcher = myPattern.matcher(password);
		if (!myMatcher.find()) {
			return true;
		}
		return false;
	}

	public CustomerAuth authenticate(String contactNumber, String password) throws AuthenticationFailedException {

		if (contactNumber == null || contactNumber.isEmpty() || password == null || password.isEmpty()) {
			throw new AuthenticationFailedException("ATH-003",
					"Incorrect format of decoded customer name and password");
		}

		Customer customerEntity = customerDao.getUserByContactNumber(contactNumber);
		if (customerEntity == null) {
			throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
		}

		String encryptedPassword = PasswordCryptographyProvider.encrypt(password, customerEntity.getSalt());
		if (!encryptedPassword.equals(customerEntity.getPassword())) {
			throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
		}

		CustomerAuth customerAuthEntity = new CustomerAuth();
		customerAuthEntity.setCustomer(customerEntity);

		final ZonedDateTime now = ZonedDateTime.now();
		final ZonedDateTime expiresAt = now.plusHours(8);

		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);

		customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
		customerAuthEntity.setLoginAt(now);
		customerAuthEntity.setExpiresAt(expiresAt);
		customerAuthEntity.setUuid(UUID.randomUUID().toString());

		customerAuthDao.createAuthToken(customerAuthEntity);

		return customerAuthEntity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer updateCustomer(Customer customerEntity) {
		return customerDao.updateCustomer(customerEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerAuth logout(String accessToken) throws AuthorizationFailedException {
		CustomerAuth customerAuthEntity = getCustomerAuth(accessToken);
		customerAuthEntity.setLogoutAt(ZonedDateTime.now());
		return customerAuthDao.updateAuthToken(customerAuthEntity);
	}

	private CustomerAuth getCustomerAuth(final String accessToken) throws AuthorizationFailedException {
		CustomerAuth customerAuthEntity = customerAuthDao.getAuthTokenEntityByAccessToken(accessToken);
		if (customerAuthEntity != null) {
			if (customerAuthEntity.getLogoutAt() != null) {
				throw new AuthorizationFailedException("ATHR-002",
						"Customer is logged out. Log in again to access this endpoint.");
			} else if (customerAuthEntity.getExpiresAt().compareTo(ZonedDateTime.now()) <= 0) {
				throw new AuthorizationFailedException("ATHR-003",
						"Your session is expired. Log in again to access this endpoint.");
			}
			return customerAuthEntity;
		} else {
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
	}

	public Customer getCustomer(String authToken) throws AuthorizationFailedException {
		return getCustomerAuth(authToken).getCustomer();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer updateCustomerPassword(String oldPassword, String newPassword, Customer customerEntity)
			throws UpdateCustomerException {
		if (isWeakPassword(newPassword)) {
			throw new UpdateCustomerException("UCR-001", "Weak password!");
		}

		String encryptedOldPassword = PasswordCryptographyProvider.encrypt(oldPassword, customerEntity.getSalt());
		if (!encryptedOldPassword.equals(customerEntity.getPassword())) {
			throw new UpdateCustomerException("UCR-004", "Incorrect old password!");
		}

		customerEntity.setPassword(PasswordCryptographyProvider.encrypt(newPassword, customerEntity.getSalt()));

		return customerDao.updateCustomer(customerEntity);

	}
}
