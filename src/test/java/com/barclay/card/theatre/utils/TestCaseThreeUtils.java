package com.barclay.card.theatre.utils;

import java.util.ArrayList;
import java.util.List;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.ResponseModelWrapper;

public class TestCaseThreeUtils {
    
    /**
     * Test All Negative cases
     * {
	"SeatMap":[
	{"Row":"5,6"},
	{"Row":"7,8"}
	],
	"SeatReservation":[
	{"partyName":"Sam","SeatsRequired":5},
	{"partyName":"John","SeatsRequired":6},
	{"partyName":"Smith","SeatsRequired":6},
	{"partyName":"Bob","SeatsRequired":7}
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
    
    private static List<Row> createSeatMap() {
	List<Row> seatMap = new ArrayList<Row>();
	seatMap.add(TestUtils.createRow("5,6"));
	seatMap.add(TestUtils.createRow("7,8"));
	return seatMap;
    }

    private static List<PartyModel> createPartyList() {
	List<PartyModel> partyModel = new ArrayList<PartyModel>();
	partyModel.add(TestUtils.createParty("Sam", 5));
	partyModel.add(TestUtils.createParty("John", 6));
	partyModel.add(TestUtils.createParty("Smith", 6));
	partyModel.add(TestUtils.createParty("Bob", 7));
	return partyModel;
    }
    
    public static ResponseModelWrapper expectedResult() {
	ResponseModelWrapper response = new ResponseModelWrapper();
	response.addResponse(TestUtils.createResponseModel("Sam", 5, 1, 1, null));
	response.addResponse(TestUtils.createResponseModel("John", 6, 1, 2, null));
	response.addResponse(TestUtils.createResponseModel("Smith", 6, 2, 1, null));
	response.addResponse(TestUtils.createResponseModel("Bob", 7, 2, 2, null));
	return response;
    }

}
