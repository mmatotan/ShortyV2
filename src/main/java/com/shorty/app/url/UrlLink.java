package com.shorty.app.url;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table
@ApiModel(description = "Class representing an original url and its Http redirect status code")
public class UrlLink {
	
	@Column
	@ApiModelProperty(hidden = true)
	private String username;
	
	//Original url to which the shortened url leads to
	@Column
	@ApiModelProperty(notes = "Original url to which the shortened url leads to", example = "https://see.asseco.com/")
	private String url;
	
	public UrlLink(String url, int redirectType) {
		this.url = url;
		this.redirectType = redirectType;
	}
	
	public UrlLink() {
		
	}

	@Id
	@Column
	@ApiModelProperty(hidden = true)
	private String shortUrl;
	
	@Column
	@ApiModelProperty(notes = "Http code for the wanted redirect type", example = "302")
	private int redirectType = 0;
	
	@Column
	@ApiModelProperty(hidden = true)
	private int visitCounter;
	
	public int getVisitCounter() {
		return visitCounter;
	}

	public void setVisitCounter(int visitCounter) {
		this.visitCounter = visitCounter;
	}
	
	public int getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(int redirectType) {
		this.redirectType = redirectType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String originalUrl) {
		this.url = originalUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
