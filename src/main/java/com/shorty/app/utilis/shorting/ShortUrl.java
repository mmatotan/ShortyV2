package com.shorty.app.utilis.shorting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a shortened url")
public class ShortUrl {

	@ApiModelProperty(notes = "Generated short url", example = "http://localhost:8080/kxbvsqhvat")
	String shortUrl;

	public ShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	public ShortUrl() {
		
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
}
