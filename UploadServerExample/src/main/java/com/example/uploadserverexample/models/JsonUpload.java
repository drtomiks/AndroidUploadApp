package com.example.uploadserverexample.models;

public class JsonUpload {
	private String fileData;
	String filename;
	

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the fileData
	 */
	public String getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(String fileData) {
		this.fileData = fileData;
	}


}
