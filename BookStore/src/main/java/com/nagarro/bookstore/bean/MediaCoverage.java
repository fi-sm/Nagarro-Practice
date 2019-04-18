package com.nagarro.bookstore.bean;

import java.util.List;

public class MediaCoverage {
	
	private String errorCode;
	private String errorDeescription;
	private List<String> titles;
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorDeescription
	 */
	public String getErrorDeescription() {
		return errorDeescription;
	}
	/**
	 * @param errorDeescription the errorDeescription to set
	 */
	public void setErrorDeescription(String errorDeescription) {
		this.errorDeescription = errorDeescription;
	}
	/**
	 * @return the titles
	 */
	public List<String> getTitles() {
		return titles;
	}
	/**
	 * @param titles the titles to set
	 */
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	

}
