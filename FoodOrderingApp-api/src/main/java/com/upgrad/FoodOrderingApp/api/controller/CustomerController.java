package com.upgrad.FoodOrderingApp.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.LogoutResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.UpdateCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.UpdateCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.UpdatePasswordRequest;
import com.upgrad.FoodOrderingApp.api.model.UpdatePasswordResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.PasswordCryptographyProvider;
import com.upgrad.FoodOrderingApp.service.common.AuthUtility;
import com.upgrad.FoodOrderingApp.service.entity.Customer;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;

@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = ("access-token"))
@RestController
@RequestMapping("/")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(method = RequestMethod.POST, path = "customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<SignupCustomerResponse> customerSignUp(
			@RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest)
			throws SignUpRestrictedException {

		Customer customerEntity = new Customer();
		customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
		customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
		customerEntity.setFirstName(signupCustomerRequest.getFirstName());
		customerEntity.setLastName(signupCustomerRequest.getLastName());
		customerEntity.setPassword(signupCustomerRequest.getPassword());
		customerEntity.setUuid(UUID.randomUUID().toString());

		Customer createdCustomer = customerService.saveCustomer(customerEntity);
		SignupCustomerResponse response = new SignupCustomerResponse().id(createdCustomer.getUuid())
				.status("CUSTOMER SUCCESSFULLY REGISTERED");
		return new ResponseEntity<SignupCustomerResponse>(response, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/customer/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization)
			throws AuthenticationFailedException, AuthorizationFailedException {

		String[] base64EncodedString = authorization.split("Basic ");
		if (base64EncodedString.length != 2) {
			throw new AuthenticationFailedException("ATH-003",
					"Incorrect format of decoded customer name and password");
		}
		byte[] decodedArray = PasswordCryptographyProvider.getBase64DecodedStringAsBytes(base64EncodedString[1]);
		String decodedString = new String(decodedArray);

		String[] decodedUserNamePassword = decodedString.split(":");

		if (decodedUserNamePassword.length != 2) {
			throw new AuthenticationFailedException("ATH-003",
					"Incorrect format of decoded customer name and password");
		}

		CustomerAuth customerAuthEntity = customerService.authenticate(decodedUserNamePassword[0],
				decodedUserNamePassword[1]);

		LoginResponse loginResponse = new LoginResponse().id(customerAuthEntity.getCustomer().getUuid())
				.firstName(customerAuthEntity.getCustomer().getFirstName())
				.lastName(customerAuthEntity.getCustomer().getLastName())
				.contactNumber(customerAuthEntity.getCustomer().getContactNumber())
				.emailAddress(customerAuthEntity.getCustomer().getEmail()).message("SIGNED IN SUCCESSFULLY");
		HttpHeaders headers = new HttpHeaders();
		headers.add("access-token", customerAuthEntity.getAccessToken());

		List<String> header = new ArrayList<>();
		header.add("access-token");
		headers.setAccessControlExposeHeaders(header);

		return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/customer/logout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization") final String authorization)
			throws AuthorizationFailedException {

		CustomerAuth customerAuthEntity = customerService.logout(AuthUtility.getAuthToken(authorization));

		LogoutResponse logoutResponse = new LogoutResponse().id(customerAuthEntity.getCustomer().getUuid())
				.message("SIGNED OUT SUCCESSFULLY");
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<LogoutResponse>(logoutResponse, headers, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/customer", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UpdateCustomerResponse> updateCustomer(
			@RequestHeader("authorization") final String authorization,
			@RequestBody final UpdateCustomerRequest updateCustomerRequest)
			throws UpdateCustomerException, AuthorizationFailedException {

		if (updateCustomerRequest.getFirstName() == null || updateCustomerRequest.getFirstName().isEmpty()) {
			throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
		}

		Customer customerEntity = customerService.getCustomer(AuthUtility.getAuthToken(authorization));

		customerEntity.setFirstName(updateCustomerRequest.getFirstName());

		if (updateCustomerRequest.getLastName() != null && !updateCustomerRequest.getLastName().isEmpty()) {
			customerEntity.setLastName(updateCustomerRequest.getLastName());
		}

		Customer updatedcustomerEntity = customerService.updateCustomer(customerEntity);

		UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse().id(updatedcustomerEntity.getUuid())
				.firstName(updatedcustomerEntity.getFirstName()).lastName(updatedcustomerEntity.getLastName())
				.status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");

		return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/customer/password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UpdatePasswordResponse> updatePassword(
			@RequestHeader("authorization") final String authorization,
			@RequestBody final UpdatePasswordRequest updatePasswordRequest)
			throws UpdateCustomerException, AuthorizationFailedException {

		if (updatePasswordRequest.getOldPassword() == null || updatePasswordRequest.getOldPassword().isEmpty()
				|| updatePasswordRequest.getNewPassword() == null || updatePasswordRequest.getNewPassword().isEmpty()) {
			throw new UpdateCustomerException("UCR-003", "No field should be empty");
		}

		Customer customerEntity = customerService.getCustomer(AuthUtility.getAuthToken(authorization));

		customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(),
				updatePasswordRequest.getNewPassword(), customerEntity);

		UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse().id(customerEntity.getUuid())
				.status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");

		return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse, HttpStatus.OK);
	}

}