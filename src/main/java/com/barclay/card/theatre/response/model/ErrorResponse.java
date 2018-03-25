package com.barclay.card.theatre.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hari
 * Error response that will be returned in case of error..
 * This will be Base response type..
 */
public class ErrorResponse implements BaseResponse {

	@JsonProperty("Error")
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
