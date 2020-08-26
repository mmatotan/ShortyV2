package com.shorty.app.utilis.registration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a response to a failed registration")
public class FailedRegistrationResponse {

	@ApiModelProperty(notes = "Description of a failed registration", example = "Account ID already exists!", position = 1)
	String description;
	
	@ApiModelProperty(notes = "Boolean dependant on the outcome of the method", example = "false", position = 0)
	boolean success;
	
	public FailedRegistrationResponse(boolean success) {
		this.success = success;
		this.description = "Account ID already exists!";
	}
	
	public FailedRegistrationResponse() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
