package com.shorty.app.utilis.shorting;

import io.swagger.annotations.ApiModelProperty;

public class ShortUrlExtension {

	@ApiModelProperty(notes = "Generated short url extension", example = "kxbvsqhvat")
	String extension;

	public ShortUrlExtension(String extension) {
		this.extension = extension;
	}

	public String getShortUrlExtension() {
		return extension;
	}

	public void setShortUrl(String extension) {
		this.extension = extension;
	}
	
}
