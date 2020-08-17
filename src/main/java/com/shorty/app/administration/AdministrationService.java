package com.shorty.app.administration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shorty.app.url.UrlLink;
import com.shorty.app.url.UrlRepository;
import com.shorty.app.utilis.NameOfServer;
import com.shorty.app.utilis.RandomString;

@Service
public class AdministrationService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UrlRepository urlRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
	
	public String registerAccount(Account account) {
		//If the username already exists
		if(accountRepository.findByUsername(account.getUsername()) != null) {
			return "{ success : false, description : 'Account ID already exists!' }"; 
		}
		
		//Generates the password, only later encodes it so we can respond to the user with their password
		account.setPassword(RandomString.randomPassword());
		String returnedPassword = account.getPassword();

		String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
		account.setPassword(encodedPassword);
		
		account.setEnabled(true);
		account.setAuthority("ROLE_USER");
		
		accountRepository.save(account);
		
		return "{ success : true, password : '" + returnedPassword + "' }";
	}
	
	
	public String linkUrls(UrlLink urlLink) {
		//Gets the currently authenticated users information so we can extract its username
		//In case user already shortened the url
		if(urlRepository.findByUrl(urlLink.getUrl()) != null) {
			return "{\n\tdescription : 'Url already shortened by the user!',"
					+ "\n\tshortUrl : '" + urlRepository.findByUrl(urlLink.getUrl()).getShortUrl() + "'\n}";
		}
		
		UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		urlLink.setUsername(authenticatedUser.getUsername());
		urlLink.setVisitCounter(0);
		if(urlLink.getRedirectType() == 0) {
			urlLink.setRedirectType(301);
		}
		
		urlLink.setShortUrl("http://" + NameOfServer.nameOfServer + ":8080/" + RandomString.randomUrl());
		while(urlRepository.findByShortUrl(urlLink.getShortUrl()) != null) {
			urlLink.setShortUrl("http://" + NameOfServer.nameOfServer + ":8080/" + RandomString.randomUrl());
		}
		
		urlRepository.save(urlLink);
		
		return "{ shortUrl : '" + urlLink.getShortUrl() + "' }"; 
	}
	
	public List<String> getStats(){
		UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Create a list out of needed information for authenticated user
		List<String> ownedUrls = new ArrayList<String>();
		List<UrlLink> ownedObjectUrls = urlRepository.findByUsername(authenticatedUser.getUsername());
		
		for(UrlLink temp : ownedObjectUrls) {
			ownedUrls.add("'" + temp.getUrl() + "'" + " : " + temp.getVisitCounter());
		}
		
		return ownedUrls;
	}
}
