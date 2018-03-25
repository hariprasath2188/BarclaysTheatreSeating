package com.barclay.card.theatre.request.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Hari
 * This is the Model which parses Seat Model in the Theatre.. 
 * This has whole theatre seat map..
 * Theatre has many rows..
 * Each row has many sections..
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TheatreSeatModel implements Serializable {
	
	private static final long serialVersionUID = 8873931501158836530L;

	private List<Row> rows;

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
}
