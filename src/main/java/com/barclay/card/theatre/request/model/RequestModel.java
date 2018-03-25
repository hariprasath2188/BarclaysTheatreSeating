package com.barclay.card.theatre.request.model;

import java.io.Serializable;
import java.util.List;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hari
 * This is the complete JSON Request Model
 * Has 2 sections...
 * Section1: Has Seat Map of the theatre
 * Section2 : Has Parties request information
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestModel implements Serializable {

	private static final long serialVersionUID = -5239021380607704368L;

	@JsonProperty("seatReservation")
	private List<PartyModel> parties;

	@JsonProperty("seatMap")
	private List<Row> seatMap;

	public List<PartyModel> getParties() {
		return parties;
	}

	public void setParties(List<PartyModel> parties) {
		this.parties = parties;
	}

	public List<Row> getSeatMap() {
		return seatMap;
	}

	public void setSeatMap(List<Row> seatMap) {
		this.seatMap = seatMap;
	}

	@Override
	public String toString() {
		return "Request [parties=" + parties + ", seatMap=" + seatMap
				+ "]";
	}

}
