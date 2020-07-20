package com.shorty.app;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministrationController {
	
	@Autowired
	AdministrationService administrationService;
	
	@PostMapping("/administration/register")
	private String registerAccount(@RequestBody Account account) {
		String reply = administrationService.registerAccount(account);
		return reply;		
	}

	@PostMapping("/administration/short")
	private String shortUrl(@RequestBody UrlLink urlLink) {
		return administrationService.linkUrls(urlLink);
	}
	
	@GetMapping("/administration/statistics")
	private String stats() {
		//We use the StringBuilder to create a string list of user statistics after using the method from accountService
		StringBuilder returnValue = new StringBuilder();
		ArrayList<String> ownedUrls = (ArrayList<String>) administrationService.getStats();
		
		returnValue.append("{\n");
		for(String temp : ownedUrls) {
			returnValue.append("\t" + temp + ",\n");
		}
		returnValue.append("}");
		
		return returnValue.toString();
	}
	
}
