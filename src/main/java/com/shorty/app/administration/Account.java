package com.shorty.app.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Account{
	
	@Id
	@Column
	private String username;
	
	//Password will be encoded in the database
	@Column
	private String password;
	
	//Spring Security requires the enabled and authority to work although we don't need them in this project
	@Column
	private boolean enabled;
	
	@Column
	private String authority;
	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
