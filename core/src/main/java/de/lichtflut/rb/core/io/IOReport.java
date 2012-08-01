/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.io;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * <p>
 *  Holds report infos for data import and export.
 * </p>
 *
 * <p>
 * 	Created 08.02.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class IOReport implements Serializable{

	public enum ReportStatus {
		UNDEFINED,
		ERROR,
		SUCCESS
	}
	
	private ReportStatus status;
	private long startTime;
	private long duration;
	private HashMap<String, Integer> quantityMap;
	private String additionalInfo;
	
	private final static String INFO_SEPERATOR = " [NEXT] ";

	// -----------------------------------------------------
	
	/**
	 * Constructor to initialize and start the timer.
	 */
	public IOReport() {
		this.status = ReportStatus.UNDEFINED;
		this.startTime = System.currentTimeMillis();
		this.quantityMap = new HashMap<String, Integer>();
		this.additionalInfo = "";
	}
	
	// -----------------------------------------------------
			
	/**
	 * Stops the timer and calculates the duration.
	 */
	private void stopTimer() {
		duration = System.currentTimeMillis() - startTime;
	}
	
	// -- CONTROL-METHODS ----------------------------------
	
	/**
	 * Adds the element to the quantityMap.
	 * @param elementName
	 * @param quantity
	 */
	public void add(String elementName, Integer quantity) {
		getQuantityMap().put(elementName, quantity);
	}
	
	/**
	 * Stops the timer and sets the report status to SUCCESS.
	 */
	public void success() {
		stopTimer();
		status = ReportStatus.SUCCESS;
	}
	
	/**
	 * Stops the timer and sets the report status to ERROR.
	 */
	public void error() {
		stopTimer();
		status = ReportStatus.ERROR;
	}
	
	/**
	 * Sets the additional information.
	 * @param additionalInfo
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	/**
	 * Merges an additional report to this one.
	 * If the reports are in different status the status is set to 'UNDEFINED'. 
	 * @param additionalReport
	 */
	public void merge(IOReport additionalReport) {
        if (additionalReport == null) {
            return;
        }
        if(!additionalReport.getAdditionalInfo().isEmpty()) {
            if(this.additionalInfo.isEmpty()) {
                this.additionalInfo = additionalReport.getAdditionalInfo();
            } else {
                this.additionalInfo += INFO_SEPERATOR +additionalReport.getAdditionalInfo();
            }
        }
        for (Entry<String, Integer> newEntry : additionalReport.getQuantityMap().entrySet()) {
            String key = newEntry.getKey();
            Integer value = newEntry.getValue();
            if(this.getQuantityMap().containsKey(key)) {
                Integer oldValue = this.getQuantityMap().get(key);
                this.getQuantityMap().put(key, oldValue + value);
            } else {
                this.getQuantityMap().put(key, value);
            }
        }
        mergeStatus(additionalReport.getStatus());
        stopTimer();
    }

    // -- GET-THE-INFOS ------------------------------------
	
	public ReportStatus getStatus() {
		return status;
	}
		
	public long getDuration() {
		return duration;
	}

	public HashMap<String, Integer> getQuantityMap() {
		return quantityMap;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	// -----------------------------------------------------
	
	public String toHTML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<h3>IO Report</h3>");
		sb.append("<div style='display: inline-block; border: 1px solid #000; padding: 5px; background-color: #EEE'>");
		
		String color = "#F90000";
		if(getStatus().equals(ReportStatus.SUCCESS)) {
			color = "#00D900";
		}
		sb.append("<span style='background-color:").append(color).append(";'>Status: <b>").append(getStatus()).append("</b></span><br/>");

		sb.append("<span>Duration: " +getDuration() +"ms</span><br/>");
		
		if(!getQuantityMap().isEmpty()) {
			sb.append("<ul>");
			for (Entry<String, Integer> entry : getQuantityMap().entrySet()) {
				sb.append("<li>" +entry.getKey() +": " +entry.getValue() +"</li>");
			}
			sb.append("</ul>");
		}
		
		if(!getAdditionalInfo().isEmpty()) {
			sb.append("<span>AdditionalInfo:<br/>" +getAdditionalInfo().replace(INFO_SEPERATOR, "<br/>") +"</span>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}

    // ----------------------------------------------------

    private void mergeStatus(ReportStatus other) {
        if (ReportStatus.ERROR.equals(other)) {
            this.status = ReportStatus.ERROR;
        } else if (ReportStatus.UNDEFINED.equals(this.status)) {
            this.status = other;
        }
    }
}
