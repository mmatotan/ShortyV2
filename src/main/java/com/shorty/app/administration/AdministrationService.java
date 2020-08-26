package com.shorty.app.administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shorty.app.url.UrlLink;
import com.shorty.app.url.UrlRepository;
import com.shorty.app.utilis.NameOfServer;
import com.shorty.app.utilis.RandomString;
import com.shorty.app.utilis.registration.FailedRegistrationResponse;
import com.shorty.app.utilis.registration.SuccessfulRegistrationResponse;
import com.shorty.app.utilis.shorting.ShortUrl;

@Service
public class AdministrationService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UrlRepository urlRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
	
	@SuppressWarnings("unchecked")
	public <T> T registerAccount(Account account) {
		
		//If the username already exists
		if(accountRepository.findByAccountID(account.getAccountID()) != null) {
			FailedRegistrationResponse response = new FailedRegistrationResponse(false);
			return (T) response;
		}
		
		//Generates the password, only later encodes it so we can respond to the user with their password
		account.setPassword(RandomString.randomPassword());
		String returnedPassword = account.getPassword();

		String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
		account.setPassword(encodedPassword);
		
		account.setEnabled(true);
		account.setAuthority("ROLE_USER");
		
		accountRepository.save(account);
		
		return (T) new SuccessfulRegistrationResponse(true, returnedPassword);
	}
	
	@Autowired
	Environment environment;
	
	public ShortUrl linkUrls(UrlLink urlLink) {
		//Gets the currently authenticated users information so we can extract its username
		UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		urlLink.setUsername(authenticatedUser.getUsername());
		urlLink.setVisitCounter(0);
		if(urlLink.getRedirectType() == 0) {
			urlLink.setRedirectType(302);
		}
		
		do {
			urlLink.setShortUrl("http://" + NameOfServer.nameOfServer + ":" + environment.getProperty("server.port") + "/" + RandomString.randomUrl());
		} while(urlRepository.findByShortUrl(urlLink.getShortUrl()) != null);
		
		urlRepository.save(urlLink);
		
		ShortUrl shortUrl = new ShortUrl(urlLink.getShortUrl());
		return shortUrl;
	}
	
	public Map<String, Integer> getStats(){
		UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Create a list out of needed information for authenticated user
		List<UrlLink> ownedObjectUrls = urlRepository.findByUsername(authenticatedUser.getUsername());
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(UrlLink temp : ownedObjectUrls) {
			map.put(temp.getUrl(), temp.getVisitCounter());
		}
		
		return map;
	}
}
