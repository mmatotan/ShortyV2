package com.shorty.app.url;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UrlController {
	
	@Autowired
	UrlService urlService;
	
	@ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
	@RequestMapping(value = "/{extension}", method = RequestMethod.GET)
	@ApiOperation(value = "Redirect to the original url", notes = "Looks for the original url to which the short one leads to.")
	@ApiResponses({
		@ApiResponse(code = 301, message = "MOVED PERMANENTLY - Successful redirection using the 301 Http status code."),
		@ApiResponse(code = 302, message = "FOUND - Successful redirection using the 302 Http status code."),
		@ApiResponse(code = 404, message = "NOT FOUND - Provided extension is not a valid short url.")
	})
	public void redirect(@PathVariable("extension") @ApiParam(value = "Unique string representing the short url.", example = "kxbvsqhvat") String extension, HttpServletResponse httpServletResponse) {
		
		//Find the original url, get its original url and redirect type
		UrlLink url = urlService.getOriginalUrl(extension);
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
	