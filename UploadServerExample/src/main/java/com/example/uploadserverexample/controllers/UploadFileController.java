package com.example.uploadserverexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import javax.servlet.ServletConfig;
import java.io.FileOutputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.http.*;

import com.example.uploadserverexample.models.JsonUpload;
import com.example.uploadserverexample.models.UploadItem;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

@Controller
@RequestMapping(value = "/uploadfile")
public class UploadFileController {

	final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	private String uploadFolderPath;
	private Integer MAXFILESIZE = 100000;
	private String uploadMode = "0";
	// ServletConfig config;
	File tempDir;


	public String getUploadFolderPath() {
		return uploadFolderPath;
	}

	public void setUploadFolderPath(String uploadFolderPath) {
		this.uploadFolderPath = uploadFolderPath;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new UploadItem());
		return "/uploadfile";
	}

	@RequestMapping(value = "/phone", method = RequestMethod.POST)
	@ResponseBody
	public void uploadFileFromPhone(UploadItem uploadItem,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		String retData;
		Integer uploadMode = 0;
		String contentType = request.getContentType();

		System.out.println("Uploading from phone.");
		System.out.println("Content Type: " + contentType);

		if (contentType.equalsIgnoreCase("application/json")) {
			uploadMode = 2;
			retData = processJSONUpload(uploadItem, result, request, response,
					session, uploadMode);
		} else {
			retData = processUpload(uploadItem, result, request, response,
					session, uploadMode);
		}

		// Send OCR results back in JSON format
		response.setContentType("application/json");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(retData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploadFileFromForm(UploadItem uploadItem,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		String retTarget;
		Integer uploadMode = 1;

		System.out.println("Uploading from form.");

		retTarget = processUpload(uploadItem, result, request, response,
				session, uploadMode);

		return retTarget;

	}

	private String processJSONUpload(UploadItem uploadItem,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			Integer uploadMode) {

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "/uploadfile";
		}

		JsonUpload uploadedData = new JsonUpload();

		String retData = "";
		String ocrResultText = "";

		System.out
				.println("processJSONUpload: BEGIN -------------------------------------------");

		try {
			String fileName = null;
			String imageFileOriginalName = null;
			OutputStream outputStream = null;
			String encodedImageData = null;
			String basePath = null;

			uploadedData = mapper.readValue(request.getInputStream(),
					JsonUpload.class);

			// System.out.println("uploadedData.getFileData(): " +
			// uploadedData.getFileData());
			imageFileOriginalName = uploadedData.getFilename();
			encodedImageData = uploadedData.getFileData();

			basePath = request.getRealPath("");
			// basePath = config.getServletContext().getRealPath("");
			System.out.println("basePath: " + basePath);

			fileName = basePath + "/resources/images/" + imageFileOriginalName;
			outputStream = new FileOutputStream(fileName);
			System.out.println("fileName: " + imageFileOriginalName);

			byte[] decodedImageData = Base64.decodeBase64(encodedImageData
					.getBytes());
			outputStream.write(decodedImageData, 0, decodedImageData.length);

			outputStream.close();
			// ..........................................
			// Optionally do something with the uploaded file.

			//mOCRItem = new OCRItem(basePath);
			//ocrResultText = mOCRItem.getOCRResult(fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		retData = "{\"OCR_Results\": \"" + ocrResultText + "\"}";

		System.out
				.println("processJSONUpload: END -------------------------------------------");

		return retData;
	}

	private String processUpload(UploadItem uploadItem, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Integer uploadMode) {

		System.out
				.println("processUpload uploadMode: " + uploadMode.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "/uploadfile";
		}

		String retData = "";
		String ocrResultText = "";

		System.out.println("BEGIN -------------------------------------------");

		try {
			MultipartFile file = uploadItem.getFileData();
			String fileName = null;
			String imageFileOriginalName = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			String basePath = null;

			if (file == null) {
				System.out.println("ERROR: Upload image file is NULL.");
				return "/uploadfile";
			}

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				if (file.getSize() > MAXFILESIZE) {
					System.out.println("File Size Exceeds MAXFILESIZE: "
							+ file.getSize());
					return "/uploadfile";
				}
				System.out.println("File size: " + file.getSize());

				imageFileOriginalName = file.getOriginalFilename();

				basePath = request.getRealPath("");
				// basePath = config.getServletContext().getRealPath("");
				System.out.println("basePath: " + basePath);

				fileName = basePath + "/resources/images/"
						+ imageFileOriginalName;
				outputStream = new FileOutputStream(fileName);
				System.out.println("fileName: " + imageFileOriginalName);

				int readBytes = 0;
				byte[] buffer = new byte[MAXFILESIZE];
				while ((readBytes = inputStream.read(buffer, 0, MAXFILESIZE)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}

			}

			outputStream.close();
			inputStream.close();

			// ..........................................
			// Optionally do something with the file.

			//mOCRItem = new OCRItem(basePath);
			//ocrResultText = mOCRItem.getOCRResult(fileName);

			session.setAttribute("uploadFile", file.getOriginalFilename());
			session.setAttribute("ocrResultText", ocrResultText);

			session.setAttribute("uploadFile", file.getOriginalFilename());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("END -------------------------------------------");

		switch (uploadMode) {
		case 0: // phone upload
			retData = "{\"OCR_Results\": \"" + ocrResultText + "\"}"; // very
																		// simple
																		// JSON
																		// for
																		// now
			break;
		case 1: // form upload
			retData = "redirect:/uploadfileindex";
			break;
		default:
			retData = "redirect:/uploadfileindex";
		}

		return retData;

	}

	public static File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: "
					+ temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "
					+ temp.getAbsolutePath());
		}

		return (temp);
	}

}
