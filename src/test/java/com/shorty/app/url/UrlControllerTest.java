package com.shorty.app.url;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorty.app.ShortyV2Application;
import com.shorty.app.utilis.shorting.ShortUrl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShortyV2Application.class)
@AutoConfigureMockMvc
@Transactional
class UrlControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Test
	void redirectTest() throws Exception {
		String auth = "Basic " + new String(Base64.encodeBase64(("admin:admin123").getBytes()));
		
		UrlLink url = new UrlLink("https://www.github.com/mmatotan", 301);
		
		RequestBuilder shortRequest = MockMvcRequestBuilders
				.post("/administration/short")
				.contentType(MediaType.APPLICATION_JSON)				
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth)
				.content(new ObjectMapper().writeValueAsBytes(url));
		
		
		MvcResult shortResult = mvc.perform(shortRequest).andReturn();
		
		String stringShortResponse = shortResult.getResponse().getContentAsString();
		ShortUrl shortUrl = new ObjectMapper().readValue(stringShortResponse, ShortUrl.class);
		
		String extension = new String();
		for(int i = 10; i > 0; i--) {
			int n = shortUrl.getShortUrl().length() - i;
			extension += shortUrl.getShortUrl().charAt(n);
		}
		
		RequestBuilder redirectRequest = MockMvcRequestBuilders
				.get("/" + extension);
		
		mvc.perform(redirectRequest);
		
		RequestBuilder statRequest = MockMvcRequestBuilders
				.get("/administration/statistics")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth);
		
		MvcResult statResult = mvc.perform(statRequest).andReturn();
		
		String stringStatResponse = statResult.getResponse().getContentAsString();
		HashMap map = new ObjectMapper().readValue(stringStatResponse, HashMap.class);
		
		assertEquals(map.toString(), "{https://www.github.com/mmatotan=1}");
	}

}
