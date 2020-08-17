package com.shorty.app.url;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {
	
	@Autowired
	UrlService urlService;
		
	@GetMapping(path = "{shortUrl}")
	public void redirect(@PathVariable("shortUrl") String shortUrl, HttpServletResponse httpServletResponse) {
		
		//Find the original url, get its original url and redirect type
		UrlLink url = urlService.getOriginalUrl(shortUrl);
		if(url == null) {
			httpServletResponse.setStatus(404);
			return;
		}
		
		String originalUrl = url.getUrl();
		int redirectType = url.getRedirectType();
		
		//Execute the redirection
		httpServletResponse.setHeader("Location", originalUrl);
		httpServletResponse.setStatus(redirectType);
	}
}
