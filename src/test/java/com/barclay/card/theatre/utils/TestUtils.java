package com.barclay.card.theatre.utils;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.response.model.ResponseModel;

public class TestUtils {
    
    private static final String ROW = "Row ";

    private static final String SECTION = "Section ";

    public static PartyModel createParty(String name, int seats) {
	PartyModel party = new PartyModel();
	party.setName(name);
	party.setSeatsRequired(seats);
	return party;
    }

    public static Row createRow(String seatMap) {
	Row row = new Row();
	row.setRow(seatMap);
	return row;
    }

    public static ResponseModel createResponseModel(String partyName,
	    int seatsRequired, int rowNumber, int sectionNumber, String reason) {
	ResponseModel model = new ResponseModel();
	model.setName(partyName);
	model.setSeatsRequired(seatsRequired);
	if (reason != null && !reason.isEmpty()) {
	    model.setReason(reason);
	    return model;
	}
	model.setRowName(ROW + rowNumber);
	model.setSectionName(SECTION + sectionNumber);
	return model;
    }

}
