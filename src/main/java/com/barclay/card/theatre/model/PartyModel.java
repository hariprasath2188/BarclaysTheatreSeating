package com.barclay.card.theatre.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hari
 * Model which just holds PartyName and SeatsReuired..
 * This model will be used for both the request and response.. 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PartyModel implements Serializable {
	
	private static final long serialVersionUID = 8663001635212350005L;

	@JsonProperty("partyName")
	private String name;
	
	@JsonProperty("seatsRequired")
	private int seatsRequired;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatsRequired() {
		return seatsRequired;
	}

	public void setSeatsRequired(int seatsRequired) {
		this.seatsRequired = seatsRequired;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result + seatsRequired;
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
	    PartyModel other = (PartyModel) obj;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (seatsRequired != other.seatsRequired)
		return false;
	    return true;
	}

	@Override
	public String toString() {
		return "Party [name=" + name + ", seatsRequired=" + seatsRequired
				+ "]";
	}
}
