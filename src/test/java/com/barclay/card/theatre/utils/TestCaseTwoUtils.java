package com.barclay.card.theatre.utils;

import java.util.ArrayList;
import java.util.List;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.ResponseModelWrapper;
import com.barclay.card.theatre.util.BarclaysTheatreConstants;

public class TestCaseTwoUtils {
    
    /**
     * Test All Negative cases
     * {
	"SeatMap":[
	{"Row":"8,8"}
	],
	"SeatReservation":[
	{"partyName":"Sam","SeatsRequired":10},
	{"partyName":"John","SeatsRequired":10},
	{"partyName":"Smith","SeatsRequired":10}
	]
	}
     * @return
     */
    public static RequestModel createDefaultRequest() {
   	RequestModel requestModel = new RequestModel();
   	requestModel.setParties(createPartyList());
   	requestModel.setSeatMap(createSeatMap());
   	return requestModel;
       }
    
    public static ResponseModelWrapper expectedResult() {
	ResponseModelWrapper response = new ResponseModelWrapper();
	response.addResponse(TestUtils.createResponseModel("Sam", 2, 1, 1, BarclaysTheatreConstants.SPLIT_PARTY));
	response.addResponse(TestUtils.createResponseModel("John", 5, 2, 2, BarclaysTheatreConstants.CANT_HANDLE));
	response.addResponse(TestUtils.createResponseModel("Smith", 6, 1, 2, BarclaysTheatreConstants.CANT_HANDLE));
	return response;
    }

    private static List<Row> createSeatMap() {
	List<Row> seatMap = new ArrayList<Row>();
	seatMap.add(TestUtils.createRow("8,8"));
	return seatMap;
    }

    private static List<PartyModel> createPartyList() {
	List<PartyModel> partyModel = new ArrayList<PartyModel>();
	partyModel.add(TestUtils.createParty("Sam", 10));
	partyModel.add(TestUtils.createParty("John", 10));
	partyModel.add(TestUtils.createParty("Smith", 10));
	return partyModel;
    }

}
