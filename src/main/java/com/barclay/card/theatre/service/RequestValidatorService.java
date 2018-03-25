package com.barclay.card.theatre.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.request.model.RequestModel;

@Service
public class RequestValidatorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean checkAndReportError(RequestModel requestModel,
	    String transactionId) {
	/**
	 * Check Parties and Seat Map for the theater is available.
	 * If any one of the object from request is Empty return false
	 */
	if (requestModel == null || isEmpty(requestModel.getParties())
		|| isEmpty(requestModel.getSeatMap())) {
	    logger.error("Transaction=" + transactionId
		    + " Not all information is present to process the request");
	    return false;
	}
	/**
	 * Checks whether any of the party requested for seats. 
	 * Concentrates mainly on "seatRequired" field. If "0" for all parties then return false as there is no point in proceeding.
	 * To make sure Parties requested "0" seats.
	 */
	List<PartyModel> partyModels = requestModel.getParties();
	int requestValid = 0;
	for (PartyModel model : partyModels) {
	    if (model.getSeatsRequired() > 0) {
		requestValid++;
		break;
	    } else {
		continue;
	    }
	}
	if (requestValid == 0) {
	    logger.error("Transaction=" + transactionId
		    + "  None of the parties requested for Seats");
	    return false;
	}
	/**
	 * Checks seats are available in the theater.
	 * To make sure request doesn't contain "0" in the Seat Map 
	 */
	requestValid = 0;
	List<Row> rows = requestModel.getSeatMap();
	for (Row row : rows) {
	    if (row.seatsAvailable()) {
		requestValid++;
		break;
	    }
	}
	if (requestValid == 0) {
	    logger.error("Transaction=" + transactionId
		    + "  Theatre doesn't have any seats");
	    return false;
	}
	/**
	 * All validations done, all looks good to proceed for allocating seats...
	 * Return true
	 */
	logger.info("Transaction="
		+ transactionId
		+ "  Input valided successfully.  Proceeding to allocate seats for parties..");
	return true;
    }

    private boolean isEmpty(List<?> list) {
	return list == null || list.isEmpty();
    }

}