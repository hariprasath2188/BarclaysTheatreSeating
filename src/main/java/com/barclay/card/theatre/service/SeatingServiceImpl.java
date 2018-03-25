package com.barclay.card.theatre.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.model.Section;
import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.BaseResponse;
import com.barclay.card.theatre.response.model.ErrorResponse;
import com.barclay.card.theatre.response.model.ResponseModel;
import com.barclay.card.theatre.response.model.ResponseModelWrapper;
import com.barclay.card.theatre.util.BarclaysTheatreConstants;
import com.barclay.card.theatre.util.RowsAndSeatsCounter;

/**
 * @author Hari 
 * ServiceClass which receives the request , validates the request
 * and assigns seats to the party or return appropriate Reason
 */

@Service
public class SeatingServiceImpl implements SeatingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestValidatorService requestValidator;

    /**
     * Core Method which delegates task such as Validation of the request and
     * sends each party to a method which assigns seat..
     */
    @Override
    public ResponseEntity<BaseResponse> checkAndAllocateSeat(
	    RequestModel requestModel, String transactionId) {
	logger.info("Transaction=" + transactionId
		+ " Trying to allocate seat...");
	/**
	 * Below block check the authenticity of the request
	 */
	if (!requestValidator.checkAndReportError(requestModel, transactionId)) {
	    logger.error("Transaction=" + transactionId
		    + " Request Invalid.. Not Processing further..");
	    return returnErrorResponse(BarclaysTheatreConstants.REQ_INVALID,
		    HttpStatus.BAD_REQUEST);
	}
	/**
	 * Creates Final Response model
	 */
	ResponseModelWrapper responseModel = new ResponseModelWrapper();
	List<PartyModel> parties = requestModel.getParties();
	try {
	    parties.forEach(party -> {
		/**
		 * Iterates and send each party to assign seats
		 */
		responseModel.addResponse(allocateSeatForParty(party,
			requestModel, transactionId));
	    });
	} catch (Exception e) {
	    return returnErrorResponse(BarclaysTheatreConstants.EXCEPTION_MSG,
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}
	RowsAndSeatsCounter.setToDefaults();
	logger.info("Transaction=" + transactionId + " Response from service "
		+ responseModel);
	return new ResponseEntity<BaseResponse>(responseModel, HttpStatus.OK);

    }

    /**
     * @param party
     * @param requestModel
     * @return ResponseModel - for a Party
     * 
     * 1. If number of seats required is greater than available then return
     *    "Can't handle and return" 
     * 2. If not, send the Party to allocate seat or to say "Call to Split"
     */
    private ResponseModel allocateSeatForParty(PartyModel party,
	    RequestModel requestModel, String transactionId) {
	logger.info("Transaction=" + transactionId
		+ " Allocating seat for party ..." + party);
	ResponseModel responseModel = createAndpopulateModel(party);
	if (!canHandleParty(responseModel, transactionId)) {
	    return responseModel;
	}
	logger.info("Transaction=" + transactionId
		+ " Party can be Handled ..." + party);
	if (allocateSeats(party, responseModel, requestModel, transactionId)) {
	    logger.info("Seats allocated for party ..." + party);
	    return responseModel;
	}
	logger.warn("Transaction=" + transactionId + " Party " + party
		+ " has to split..");
	responseModel.setReason(BarclaysTheatreConstants.SPLIT_PARTY);
	RowsAndSeatsCounter.deductTotalAvailableSeats(party.getSeatsRequired());
	return responseModel;
    }

    /**
     * @param responseModel
     * @param party
     * @return Return true if number of seats required is less than available
     *         seats and allocation can be made
     */
    private boolean canHandleParty(ResponseModel responseModel, String transactionId) {
	if (RowsAndSeatsCounter.getTotalAvailableSeats() < responseModel
		.getSeatsRequired()) {
	    logger.warn("Transaction=" + transactionId
		    + " Can't Handle party ..." + responseModel.getName());
	    responseModel.setReason(BarclaysTheatreConstants.CANT_HANDLE);
	    return false;
	}
	return true;
    }

    /**
     * @param party
     * @param responseModel
     * @param requestModel
     * @return
     * 
     *  Method iterates Row Object, pass on party and Row to see whether
     *  that Row(all sections) has seats to allocate for that Party
     */
    private boolean allocateSeats(PartyModel party,
	    ResponseModel responseModel, RequestModel requestModel,
	    String transactionId) {
	List<Row> rows = requestModel.getSeatMap();
	for (Row row : rows) {
	    logger.info("Transaction=" + transactionId
		    + " Trying to accomodate Party in Row  " + row.getRowName());
	    if (!row.seatsAvailable()) {
		logger.info("Transaction=" + transactionId
			+ " Seats not available on row " + row.getRowName());
		continue;
	    }
	    if (checkAndAllocateSection(row, party, responseModel,
		    transactionId)) {
		if (responseModel.isTentative() != null
			&& responseModel.isTentative()) {
		    continue;
		}
		responseModel.setTentative(null);
		return true;
	    }
	    logger.info("Transaction=" + transactionId + " Row  "
		    + row.getRowName() + " not suited. Moving to next ..");
	}
	if (responseModel.isTentative() != null && responseModel.isTentative()
		&& responseModel.getRowName() != null
		&& responseModel.getSectionName() != null) {
	    responseModel.setTentative(null);
	    return true;
	}
	responseModel.setTentative(null);
	return false;
    }

    /**
     * @param row
     * @param party
     * @param responseModel
     * @return
     * 
     *         Iterates each section of the Rows and see that section can
     *         accommodate party
     */
    private boolean checkAndAllocateSection(Row row, PartyModel party,
	    ResponseModel responseModel, String transactionId) {
	for (Section section : row.getSections()) {
	    if (!canAllocate(section, party, responseModel, transactionId)) {
		logger.info("Transaction=" + transactionId
			+ " Seats not suitable in Section  "
			+ section.getName() + ".Moving to next ..");
		continue;
	    }
	    /**
	     * Tentative means section has enough seats but kept tentative as to
	     * check any section has exact number of seats or more than half of required seats..
	     */
	    logger.info("Transaction=" + transactionId + " Tentative "
		    + responseModel.isTentative());
	    if (responseModel.isTentative() != null
		    && responseModel.isTentative()) {
		logger.info("Transaction=" + transactionId
			+ " Section set as Tentative  " + section.getName()
			+ ". If more accurate section, found will be set..");
		section.setSeatsAvailableNow(section.getSeatsAvailableNow()
			- party.getSeatsRequired());
		RowsAndSeatsCounter.deductTotalAvailableSeats(party
			.getSeatsRequired());
		responseModel.setRowName(row.getRowName());
		responseModel.setSectionName(section.getName());
		responseModel.setSection(section);
		responseModel.setRow(row);
		continue;
	    }
	    /**
	     * Found a section with exact number of seats or less than half no.
	     * of seats..
	     */
	    logger.info("Transaction="
		    + transactionId
		    + " More accurate section found.. Hence removing tentaive Section");
	    if (responseModel.getRow() != null
		    && responseModel.getSection() != null) {
		responseModel.getSection().setSeatsAvailableNow(
			responseModel.getSection().getSeatsAvailableNow()
				+ party.getSeatsRequired());
		RowsAndSeatsCounter.addToTotalAvailableSeats(party
			.getSeatsRequired());
	    }
	    logger.info("Transaction="
		    + transactionId
		    + " More accurate section found.. Adding more accurate section..");
	    section.setSeatsAvailableNow(section.getSeatsAvailableNow()
		    - party.getSeatsRequired());
	    RowsAndSeatsCounter.deductTotalAvailableSeats(party
		    .getSeatsRequired());
	    responseModel.setRowName(row.getRowName());
	    responseModel.setSectionName(section.getName());
	    return true;
	}
	if (responseModel.isTentative() != null && responseModel.isTentative()
		&& responseModel.getRowName() != null
		&& responseModel.getSectionName() != null) {
	    logger.info("Transaction="
		    + transactionId
		    + " Setting tentative section as final as no accurate section found..");
	    return true;
	}
	return false;
    }

    /**
     * 
     * @param section
     * @param party
     * @return
     * 
     *         Party will be allocated seat if: 1. Number of seats in a section
     *         is equal to number of Seats required for a party 2. Number of
     *         seats required is half of seats on a section
     */
    private boolean canAllocate(Section section, PartyModel party,
	    ResponseModel responseModel, String transactionId) {
	if (section.getSeatsAvailableNow() == party.getSeatsRequired()) {
	    logger.info("Transaction=" + transactionId
		    + " Seats allocated for party " + party + " in section "
		    + section.getName());
	    responseModel.setTentative(false);
	    return true;
	}
	if (Math.ceil(section.getSeatsAvailableNow() / 2) >= party.getSeatsRequired()) {
	    logger.info("Transaction=" + transactionId
		    + " Seats allocated for party " + party + " in section "
		    + section.getName());
	    responseModel.setTentative(false);
	    return true;
	}
	if (section.getSeatsAvailableNow() > party.getSeatsRequired()) {
	    logger.info("Transaction=" + transactionId
		    + " Seats allocated for party " + party + " in section "
		    + section.getName());
	    if (responseModel.isTentative() == null
		    || !responseModel.isTentative()) {
		responseModel.setTentative(true);
		return true;
	    }
	}
	return false;
    }

    private ResponseModel createAndpopulateModel(PartyModel party) {
	ResponseModel responseModel = new ResponseModel();
	responseModel.setName(party.getName());
	responseModel.setSeatsRequired(party.getSeatsRequired());
	return responseModel;
    }

    private ResponseEntity<BaseResponse> returnErrorResponse(
	    String errorReason, HttpStatus status) {
	ErrorResponse errResp = new ErrorResponse();
	errResp.setError(errorReason);
	return new ResponseEntity<BaseResponse>(errResp, status);
    }
}
