package com.shorty.app.administration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.shorty.app.url.UrlLink;
import com.shorty.app.utilis.registration.FailedRegistrationResponse;
import com.shorty.app.utilis.registration.SuccessfulRegistrationResponse;
import com.shorty.app.utilis.shorting.ShortUrl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShortyV2Application.class)
@AutoConfigureMockMvc
@Transactional
class AdministrationControllerTest {

	@Autowired
	private MockMvc mvc;
	
	//Test registration
	@Test
	void registrationTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/administration/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"accountID\": \"Andrija\" }");
		
		MvcResult result = mvc.perform(request).andReturn();
		String stringResponse = result.getResponse().getContentAsString();
		SuccessfulRegistrationResponse response = new ObjectMapper().readValue(stringResponse, SuccessfulRegistrationResponse.class);
		
		assertThat(response.isSuccess(), is(true));
	}
	
	//Test double registration fail safe
	@Test
	void doubleRegistrationTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/administration/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"accountID\": \"Andrija\" }");		
		
		MvcResult result = mvc.perform(request).andReturn();
		result = mvc.perform(request).andReturn();
		String stringResponse = result.getResponse().getContentAsString();
		FailedRegistrationResponse response = new ObjectMapper().readValue(stringResponse, FailedRegistrationResponse.class);
		
		assertThat(response.isSuccess(), is(false));
	}
	
	
	//Test shorting urls
	@Test
	void shortTest() throws Exception {
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
		
		assertNotNull(shortUrl.getShortUrl());
	}
	
	//Test empty map
	@Test
	void statTest() throws Exception {
		String auth = "Basic " + new String(Base64.encodeBase64(("admin:admin123").getBytes()));
		
		RequestBuilder statRequest = MockMvcRequestBuilders
				.get("/administration/statistics")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth);
		
		MvcResult statResult = mvc.perform(statRequest).andReturn();
		
		String stringStatResponse = statResult.getResponse().getContentAsString();
		HashMap map = new ObjectMapper().readValue(stringStatResponse, HashMap.class);
		
		assertNotNull(map);
	}
	
	//Test filled map
	@Test
	void stat2Test() throws Exception {
		String auth = "Basic " + new String(Base64.encodeBase64(("admin:admin123").getBytes()));
		
		UrlLink url = new UrlLink("https://www.github.com/mmatotan", 301);
		
		RequestBuilder shortRequest = MockMvcRequestBuilders
				.post("/administration/short")
				.contentType(MediaType.APPLICATION_JSON)				
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth)
				.content(new ObjectMapper().writeValueAsBytes(url));
		
		
		mvc.perform(shortRequest);
		
		RequestBuilder statRequest = MockMvcRequestBuilders
				.get("/administration/statistics")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth);
		
		MvcResult statResult = mvc.perform(statRequest).andReturn();
		
		String stringStatResponse = statResult.getResponse().getContentAsString();
		HashMap map = new ObjectMapper().readValue(stringStatResponse, HashMap.class);
		
		assertEquals(map.toString(), "{https://www.github.com/mmatotan=0}");
	}


}
