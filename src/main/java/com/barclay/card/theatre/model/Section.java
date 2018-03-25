package com.barclay.card.theatre.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Hari
 * Section object which has section information.
 * Used in parsing request..
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Section implements Serializable {
	
	private static final long serialVersionUID = -1003225041333619240L;
	
	private String name;
	
	private int totalSeatsAvailable;
	
	private int seatsAvailableNow;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalSeatsAvailable() {
		return totalSeatsAvailable;
	}

	public void setTotalSeatsAvailable(int totalSeatsAvailable) {
		this.totalSeatsAvailable = totalSeatsAvailable;
	}

	public int getSeatsAvailableNow() {
		return seatsAvailableNow;
	}

	public void setSeatsAvailableNow(int seatsAvailableNow) {
		this.seatsAvailableNow = seatsAvailableNow;
	}

	@Override
	public String toString() {
	    return "Section [name=" + name + ", totalSeatsAvailable="
		    + totalSeatsAvailable + ", seatsAvailableNow="
		    + seatsAvailableNow + "]";
	}

}
