package com.shorty.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shorty.project.help.NameOfServer;

@Service
public class UrlService {
	
	@Autowired
	UrlRepository urlRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	public UrlLink getOriginalUrl(String shortPart) {
		//Extends the short suffix to the full address
		String fullShortPart = 	"http://" + NameOfServer.nameOfServer + ":8080/" + shortPart;
		
		//Find the saved UrlLink
		UrlLink urlLink = urlRepository.findByShortUrl(fullShortPart);
		
		if(urlLink == null) return null;
		
		//Mark as visited
		urlLink.setVisitCounter(urlLink.getVisitCounter() + 1);
		urlRepository.save(urlLink);
		
		return urlLink;
	}
	
}
