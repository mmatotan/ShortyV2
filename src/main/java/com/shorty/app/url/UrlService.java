package com.shorty.app.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.shorty.app.administration.AccountRepository;
import com.shorty.app.utilis.NameOfServer;

@Service
public class UrlService {
	
	@Autowired
	UrlRepository urlRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	Environment environment;
	
	public UrlLink getOriginalUrl(String shortPart) {
		//Extends the short suffix to the full address
		String fullShortPart = 	"http://" + NameOfServer.nameOfServer + ":" + environment.getProperty("server.port") + "/" + shortPart;
		
		//Find the saved UrlLink
		UrlLink urlLink = urlRepository.findByShortUrl(fullShortPart);
		
		if(urlLink == null) return null;
		
		//Mark as visited
		urlLink.setVisitCounter(urlLink.getVisitCounter() + 1);
		urlRepository.save(urlLink);
		
		return urlLink;
	}
	
}
