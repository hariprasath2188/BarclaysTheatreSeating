package com.barclay.card.theatre.api;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.BaseResponse;
import com.barclay.card.theatre.response.model.ErrorResponse;
import com.barclay.card.theatre.service.SeatingService;
import com.barclay.card.theatre.util.BarclaysTheatreConstants;

/**
 * @author Hari
 * Controller that has Endpoint. This class delegates the request to service class
 * and get the request processed and return the response(either success or Exception).
 */
@RestController
@RequestMapping("api/v1")
public class BarclaycardTheatreController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeatingService seatingService;

    @RequestMapping(value = "/theatre/barclays/allocate/seat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> mapSeat(@RequestBody RequestModel model) {
	String transactionId = UUID.randomUUID().toString();
	logger.info("Transaction=" + transactionId + " Model from Request "
		+ model);
	if (model == null) {
	    logger.error("Transaction=" + transactionId
		    + " Request is not valid.. Returning Error Response.. ");
	    ErrorResponse errResp = new ErrorResponse();
	    errResp.setError(BarclaysTheatreConstants.REQ_PARSE_FAILED);
	    return new ResponseEntity<BaseResponse>(errResp,
		    HttpStatus.BAD_REQUEST);
	}
	try {
	    ResponseEntity<BaseResponse> response = seatingService
		    .checkAndAllocateSeat(model, transactionId);
	    logger.info("Transaction=" + transactionId
		    + " Response from allocation " + response);
	    return response;
	} catch (Exception e) {
	    logger.error("Transaction=" + transactionId + " "
		    + BarclaysTheatreConstants.EXCEPTION_MSG + e);
	    ErrorResponse errResp = new ErrorResponse();
	    errResp.setError(BarclaysTheatreConstants.EXCEPTION_MSG);
	    return new ResponseEntity<BaseResponse>(errResp,
		    HttpStatus.INTERNAL_SERVER_ERROR);
	}

    }
}
