package com.barclay.card.theatre.response.model;

import com.barclay.card.theatre.model.PartyModel;
import com.barclay.card.theatre.model.Row;
import com.barclay.card.theatre.model.Section;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Hari
 * Response Model which extends PartyModel(which has Party Information such as Name, Seats Required) 
 * has information about seat allocation.
 * If allocated then Rows and Section Information will be included.
 * If not, reason column will have why not allocated.
 */
@JsonInclude(Include.NON_NULL)
public class ResponseModel extends PartyModel {

    private static final long serialVersionUID = 2579694058560264198L;

    @JsonProperty("row")
    private String rowName;

    @JsonProperty("section")
    private String sectionName;

    @JsonProperty("why can't allocate?")
    private String reason;

    @JsonIgnore
    private Boolean isTentative;
    
    @JsonIgnore
    private Section section;
    
    @JsonIgnore
    private Row row;

    public String getRowName() {
	return rowName;
    }

    public void setRowName(String rowName) {
	this.rowName = rowName;
    }

    public String getSectionName() {
	return sectionName;
    }

    public void setSectionName(String sectionName) {
	this.sectionName = sectionName;
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public Boolean isTentative() {
	return isTentative;
    }

    public void setTentative(Boolean isTentative) {
	this.isTentative = isTentative;
    }
    
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    
    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((reason == null) ? 0 : reason.hashCode());
	result = prime * result + ((rowName == null) ? 0 : rowName.hashCode());
	result = prime * result
		+ ((sectionName == null) ? 0 : sectionName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ResponseModel other = (ResponseModel) obj;
	if (reason == null) {
	    if (other.reason != null)
		return false;
	} else if (!reason.equals(other.reason))
	    return false;
	if (rowName == null) {
	    if (other.rowName != null)
		return false;
	} else if (!rowName.equals(other.rowName))
	    return false;
	if (sectionName == null) {
	    if (other.sectionName != null)
		return false;
	} else if (!sectionName.equals(other.sectionName))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Response [rowName=" + rowName + ", sectionName=" + sectionName
		+ " " + reason + "]";
    }

}
