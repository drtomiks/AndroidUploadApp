	package com.example.uploadserverexample.controllers;
	
	import java.util.Map;
	import org.springframework.stereotype.Controller;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	
	@Controller
	@RequestMapping("/uploadfileindex")
	public class UploadFileIndexController {
	
		// Display the form on the get request
		@RequestMapping(method = RequestMethod.GET)
		public String showRegistration(Map model) {
			return "uploadfileindex";
		}
	
		// Process the form.
		@RequestMapping(method = RequestMethod.POST)
		public String processRegistration(BindingResult result) {
			return "uploadfileindex";
		}
	}
