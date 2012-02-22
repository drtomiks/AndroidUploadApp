package com.example.androidbase64json;

public class AppProperties {
	
	public static int uploadMode = 0; // 0: simple 1: json
	public static String uploadUrl = "http://10.0.2.2:8080/devocrserver/uploadfile/phone"; // set to your server

	/**
	 * @return the uploadUrl
	 */
	public static String getUploadUrl() {
		return uploadUrl;
	}

	/**
	 * @param uploadUrl the uploadUrl to set
	 */
	public static void setUploadUrl(String uploadUrl) {
		AppProperties.uploadUrl = uploadUrl;
	}

	/**
	 * @return the uploadMode
	 */
	public static int getUploadMode() {
		return uploadMode;
	}

	/**
	 * @param uploadMode the uploadMode to set
	 */
	public static void setUploadMode(int uploadMode) {
		AppProperties.uploadMode = uploadMode;
	}
	
}
