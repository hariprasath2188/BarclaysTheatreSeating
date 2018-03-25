package com.barclay.card.theatre.response.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hari 
 * Final output model which is just a wrapper holds each Party information...
 */
@JsonInclude(Include.NON_NULL)
public class ResponseModelWrapper implements BaseResponse {

    private static final long serialVersionUID = -6978027059119519756L;

    @JsonProperty
    private List<ResponseModel> response;

    public List<ResponseModel> getResponse() {
	return response;
    }

    public void setResponse(List<ResponseModel> response) {
	this.response = response;
    }

    public void addResponse(ResponseModel model) {
	if (response == null) {
	    response = new ArrayList<ResponseModel>();
	}
	response.add(model);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((response == null) ? 0 : response.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ResponseModelWrapper other = (ResponseModelWrapper) obj;
	if (response == null) {
	    if (other.response != null)
		return false;
	} else if (!response.equals(other.response))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ResponseModelWrapper [response=" + response + "]";
    }
    
    

}
