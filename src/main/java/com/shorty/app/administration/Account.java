package com.shorty.app.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table
@ApiModel(description = "Class representing a registered account")
public class Account{
	
	@Id
	@Column
	@ApiModelProperty(notes = "User's account ID", example = "Vladimir")
	private String accountID;
	
	//Password will be encoded in the database
	@Column
	@ApiModelProperty(hidden = true)
	private String password;
	
	//Spring Security requires the enabled and authority to work although we don't need them in this project
	@Column
	@ApiModelProperty(hidden = true)
	private boolean enabled;
	
	@Column
	@ApiModelProperty(hidden = true)
	private String authority;
	
	public Account() {
		
	}

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

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String username) {
		this.accountID = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
