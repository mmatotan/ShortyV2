package com.shorty.app.administration;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shorty.app.url.UrlLink;
import com.shorty.app.utilis.registration.FailedRegistrationResponse;
import com.shorty.app.utilis.registration.SuccessfulRegistrationResponse;
import com.shorty.app.utilis.shorting.ShortUrl;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ExampleProperty;

@RestController
@RequestMapping("/administration")
public class AdministrationController {
	
	@Autowired
	AdministrationService administrationService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Register new account", notes = "Upon successful register returns a password, and upon unsuccessful one returns the description of a reson for an unsuccessful registration.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = SuccessfulRegistrationResponse.class),
		@ApiResponse(code = 409, message = "CONFLICT", response = FailedRegistrationResponse.class)
	})
	private <T> T registerAccount(@ApiParam(required = true, name = "account", value = "Creates a new account from provided the accountID.") @RequestBody Account account, HttpServletResponse httpServletResponse) {
		T response = administrationService.registerAccount(account);
		if(response instanceof FailedRegistrationResponse) {
			httpServletResponse.setStatus(409);
			return response;
		}
		httpServletResponse.setStatus(200);
		return response;
	}

	@RequestMapping(value = "/short", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Short a url", notes = "Returns a new short url which redirects users to the original url.", authorizations = {@Authorization(value = "basicAuth")})
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = ShortUrl.class)
	})
	private ShortUrl shortUrl(@ApiParam(required = true, name = "url", value = "Links an original url with a newly generated short one.") @RequestBody UrlLink urlLink) {
		return administrationService.linkUrls(urlLink);
	}
	
	@RequestMapping(value = "statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve user statistics", notes = "Responds with a 'original url : number of visits' map.", authorizations = {@Authorization(value = "basicAuth")})
	// Had to be hard coded, springfox doesn't work well with already made java objects such as Maps and HashMaps
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK",
		        examples = @io.swagger.annotations.Example(
		            value = {
		                @ExampleProperty(
		                    mediaType = "application/json",
		                    value = "{\n\t\"https://github.com\": 5,\n\t\"https://see.asseco.com\": 3\n}"),
		                }),
		        response = HashMap.class
		    )})
	private Map<String, Integer> stats() {
		return administrationService.getStats();		
	}
}
