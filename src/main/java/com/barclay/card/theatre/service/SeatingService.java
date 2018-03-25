package com.barclay.card.theatre.service;

import org.springframework.http.ResponseEntity;

import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.BaseResponse;

public interface SeatingService {

	public ResponseEntity<BaseResponse> checkAndAllocateSeat(RequestModel model, String transactionId);
	
}
