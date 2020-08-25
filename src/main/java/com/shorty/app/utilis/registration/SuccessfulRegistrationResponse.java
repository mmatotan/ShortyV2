package com.shorty.app.utilis.registration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a response to a succesful registration")
public class SuccessfulRegistrationResponse {

	@ApiModelProperty(notes = "Returned random password", example = "huxchrtj7578", position = 1)
	String password;
	
	@ApiModelProperty(notes = "Boolean dependant on the outcome of the method", example = "true", position = 0)
	boolean success;
	
	public SuccessfulRegistrationResponse(boolean success, String password) {
		this.success = success;
		this.password = password;
	}
	
	public SuccessfulRegistrationResponse() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
