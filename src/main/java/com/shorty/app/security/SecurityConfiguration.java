package com.shorty.app.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfiguration(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		//If the user tries to short or get statistics he will need to be authenticated first
		//Otherwise allow access for all inquiries
		http
			.authorizeRequests()
			.antMatchers("/administration/short", "/administration/statistics").authenticated()
			.anyRequest().permitAll()
			.and().httpBasic();
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//Tells Spring Security where to look for authentication needed for users
		//Also specifies the password encoder to use
		auth.jdbcAuthentication().dataSource(dataSource).
			usersByUsernameQuery("select accountid, password, enabled " + "from account " + "where accountid = ?").
			authoritiesByUsernameQuery("select accountid, authority " + "from account " + "where accountid = ?").
			passwordEncoder(passwordEncoder);
	}
	
}
