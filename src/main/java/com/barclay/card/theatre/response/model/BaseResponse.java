package com.barclay.card.theatre.response.model;

import java.io.Serializable;

/**
 * @author Hari
 * Just a Interface which will be implemented by bith Error and Success response
 * so that controller can respond BaseResponse as a type in both Error and success cases
 */
public interface BaseResponse extends Serializable{

}
