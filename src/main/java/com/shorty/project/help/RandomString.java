package com.shorty.project.help;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RandomString {
	public static String randomPassword() {
		//Used to generate a random password and a random short url for now; Might be changed for convenience
		//Nisam siguran koji je najbolji pristup za generiranje urla/passworda, ali se lagano promjeni ako postoji bolje riješenje
		int leftLimit = 97; 
		int rightLimit = 122;
		int leftNumLimit = 48;
		int rightNumLimit = 57;
		int randomNumber;
		
		Random random = new Random();
		String generatedString = new String();
		
		for (int i = 0; i < 8; i++) {
			randomNumber = random.nextInt(rightLimit - leftLimit) + leftLimit;
			generatedString += (char)randomNumber;
		}
		for (int i = 0; i < 4; i++) {
			randomNumber = random.nextInt(rightNumLimit - leftNumLimit) + leftNumLimit;
			generatedString += (char)randomNumber;
		}
		
		return generatedString;
	}
	
	public static String randomUrl() {
		int leftLimit = 97; 
		int rightLimit = 122;
		int randomNumber;
		
		Random random = new Random();
		String generatedString = new String();
		
		for (int i = 0; i < 10; i++) {
			randomNumber = random.nextInt(rightLimit - leftLimit) + leftLimit;
			generatedString += (char)randomNumber;
		}
		
		return generatedString;
	}
	
	private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(5);

	public static String bCryptPasswordFromUsername(String accountUsername) {
		String password = new String(), tempPassword;
		tempPassword = bCryptPasswordEncoder.encode(accountUsername);
		for(int i = 0; i < 6; i++) {
			password += tempPassword.charAt(i);
		}
		return password;
	}
}
