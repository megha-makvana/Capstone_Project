package com.upgrad.FoodOrderingApp.service.businness;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCl {

	public static void main(String[] args) {
		System.out.println(isWeakPassword("Avin##a!sh@@85"));
	}
	
//	Password should have atltest 
//	8 Characters
//	one digit
//	one uppercase
//	one of the following characters [#@$%&*!^]
	
	private static boolean isWeakPassword(String password) {
		if(password.length() <8) {
			return true;
		}
		boolean containsDigit= false;
		boolean containsUpperCase= false;
		
		for(char c: password.toCharArray()) {
			if(Character.isDigit(c)) {
				containsDigit=true;
			}
			if(Character.isUpperCase(c)) {
				containsUpperCase= true;
			}
		}
		if(!containsDigit || !containsUpperCase) {
			return true;
		}
		
		Pattern myPattern= Pattern.compile("[#@$%&*!^]", Pattern.CASE_INSENSITIVE);
		Matcher myMatcher = myPattern.matcher(password);
		if(!myMatcher.find()) {
			return true;
		}
		return false;
	}
}
