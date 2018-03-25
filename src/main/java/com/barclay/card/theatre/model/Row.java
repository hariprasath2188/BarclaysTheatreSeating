package com.barclay.card.theatre.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclay.card.theatre.util.RowsAndSeatsCounter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Row implements Serializable {

    private static final long serialVersionUID = 6014356803575592537L;

    private static final String SPLIT_STRING = ",";

    private static final String SECTION_NAME = "Section ";

    private static final String ROW_NAME = "Row ";

    @JsonProperty("row")
    private String row;

    @JsonIgnore
    private List<Section> sections;

    @JsonIgnore
    private String rowName;
    
    public String getRow() {
	return row;
    }

    public void setRow(String row) {
	this.row = row;
	setRowName();
	addSection(row);
    }

    public List<Section> getSections() {
	return sections;
    }

    public void addSections(Section section) {
	if (this.sections == null) {
	    this.sections = new ArrayList<Section>();
	}
	this.sections.add(section);
    }

    public String getRowName() {
	return rowName;
    }

    public void setRowName(String rowName) {
	this.rowName = rowName;
    }

    private void setRowName() {
	int rowCount = RowsAndSeatsCounter.getRowCounter();
	setRowName(new StringBuilder(ROW_NAME).append(
		Integer.toString(rowCount)).toString());
	RowsAndSeatsCounter.setRowCounter(++rowCount);
    }

    private void addSection(String row) {
	String[] sectionArray = row.split(SPLIT_STRING);
	if (sectionArray == null || sectionArray.length == 0) {
	    return;
	}
	for (int i = 0; i < sectionArray.length; i++) {
	    String val = sectionArray[i];
	    Section sec = new Section();
	    sec.setName(new StringBuilder(SECTION_NAME).append(
		    Integer.toString(i + 1)).toString());
	    int seats = Integer.parseInt(val);
	    sec.setTotalSeatsAvailable(seats);
	    sec.setSeatsAvailableNow(seats);
	    RowsAndSeatsCounter.addToTotalAvailableSeats(seats);
	    addSections(sec);
	}
    }

    public boolean seatsAvailable() {
	for (Section sec : this.sections) {
	    if (sec.getSeatsAvailableNow() > 0) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String toString() {
	return "Row [rowName=" + rowName + ", sections=" + sections + "]";
    }

}
