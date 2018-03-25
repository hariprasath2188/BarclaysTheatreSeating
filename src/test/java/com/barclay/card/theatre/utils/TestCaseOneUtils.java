package com.barclay.card.theatre.utils;

import java.util.ArrayList;
import java.util.List;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.ResponseModelWrapper;
import com.barclay.card.theatre.util.BarclaysTheatreConstants;

public class TestCaseOneUtils {

    /**
     * Creates request as in Problem... 
     *  6 6
	3 5 5 3
	4 6 6 4
	2 8 8 2
	6 6

	Smith 2
	Jones 5
	Davis 6
	Wilson 100
	Johnson 3
	Williams 4
	Brown 8
	Miller 12
     */
    public static RequestModel createDefaultRequest() {
	RequestModel requestModel = new RequestModel();
	requestModel.setParties(createPartyList());
	requestModel.setSeatMap(createSeatMap());
	return requestModel;
    }

    /**
     * Creats reponse Object as 
        Smith Row 1 Section 1
	Jones Row 2 Section 2
	Davis Row 1 Section 2
	Wilson Sorry, we can't handle your party.
	Johnson Row 2 Section 1
	Williams Row 1 Section 1
	Brown Row 4  Section 2
	Miller Call to split party.
     * @return
     */
    public static ResponseModelWrapper expectedResult() {
	ResponseModelWrapper response = new ResponseModelWrapper();
	response.addResponse(TestUtils.createResponseModel("Smith", 2, 1, 1, null));
	response.addResponse(TestUtils.createResponseModel("Jones", 5, 2, 2, null));
	response.addResponse(TestUtils.createResponseModel("Davis", 6, 1, 2, null));
	response.addResponse(TestUtils.createResponseModel("Wilson", 100, 2, 2,
		BarclaysTheatreConstants.CANT_HANDLE));
	response.addResponse(TestUtils.createResponseModel("Johnson", 3, 2, 1, null));
	response.addResponse(TestUtils.createResponseModel("Williams", 4, 1, 1, null));
	response.addResponse(TestUtils.createResponseModel("Brown", 8, 4, 2, null));
	response.addResponse(TestUtils.createResponseModel("Miller", 12, 1, 1,
		BarclaysTheatreConstants.SPLIT_PARTY));
	return response;
    }

    private static List<Row> createSeatMap() {
	List<Row> seatMap = new ArrayList<Row>();
	seatMap.add(TestUtils.createRow("6,6"));
	seatMap.add(TestUtils.createRow("3,5,5,3"));
	seatMap.add(TestUtils.createRow("4,6,6,4"));
	seatMap.add(TestUtils.createRow("2,8,8,2"));
	seatMap.add(TestUtils.createRow("6,6"));
	return seatMap;
    }

    private static List<PartyModel> createPartyList() {
	List<PartyModel> partyModel = new ArrayList<PartyModel>();
	partyModel.add(TestUtils.createParty("Smith", 2));
	partyModel.add(TestUtils.createParty("Jones", 5));
	partyModel.add(TestUtils.createParty("Davis", 6));
	partyModel.add(TestUtils.createParty("Wilson", 100));
	partyModel.add(TestUtils.createParty("Johnson", 3));
	partyModel.add(TestUtils.createParty("Williams", 4));
	partyModel.add(TestUtils.createParty("Brown", 8));
	partyModel.add(TestUtils.createParty("Miller", 12));
	return partyModel;
    }

}
