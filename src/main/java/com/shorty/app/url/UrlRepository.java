package com.shorty.app.url;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlLink, Integer> {
	UrlLink findByShortUrl(String shortUrl);
	List<UrlLink> findByUsername(String username);
	UrlLink findByUrl(String url);
}
