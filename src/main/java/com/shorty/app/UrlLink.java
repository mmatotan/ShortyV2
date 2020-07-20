package com.shorty.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UrlLink {
	
	@Column
	private String username;
	
	//Original url to which the shortened url leads to
	@Column
	private String url;
	
	@Id
	@Column
	private String shortUrl;
	
	@Column
	private int redirectType = 0;
	
	@Column
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
