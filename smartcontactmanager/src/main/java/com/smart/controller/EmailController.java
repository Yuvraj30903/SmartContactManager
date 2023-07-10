package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.config.EmailService;
import com.smart.entities.EmailRequest;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	@PostMapping("/sendmail")
	public ResponseEntity<?> sendmail(@RequestBody EmailRequest er)
	{
		boolean sendEmail = emailService.sendEmail(er.getSubject(), er.getBody(), er.getTo());
		if(sendEmail)
		return ResponseEntity.ok( "success");
		else 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
		
	}

}
