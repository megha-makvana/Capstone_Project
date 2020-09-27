package com.upgrad.FoodOrderingApp.service.common;

import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;

public class AuthUtility {

	public static String getAuthToken(String authorization) throws AuthorizationFailedException {

		String[] base64EncodedString = authorization.split("Bearer ");

		if (base64EncodedString.length != 2) {
			throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in");
		}

		return base64EncodedString[1];
	}
}
