package com.slot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.slot.entities.TimeStamp;
import com.slot.response.ApiResponse;
import com.slot.services.AllotmentService;

@RestController
public class MyController {
	//ISO_LOCAL_DATE ="yyyy-MM-dd"
	@Autowired
	private AllotmentService allotmentService;
	
	
	/* Adding new TimeSlot with Date */
	@PostMapping("/date")
	public ResponseEntity<ApiResponse> addDateAndSlot(@RequestBody TimeStamp timeStamp)
	{
		
		ApiResponse response = allotmentService.addDateAndSlot(timeStamp); 
		if(response.getStatus()=="success")
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	/* Updating existing timeSlot */
	@PutMapping("/date/slot")
	public ResponseEntity<ApiResponse> addSlotToDate(@RequestBody TimeStamp timeStamp)
	{
		
		ApiResponse response = allotmentService.updateSlot(timeStamp); 
		if(response.getStatus()=="success")
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	
	/*Getting list of slots avialable */
	@GetMapping("/{date}")
	public ResponseEntity<ApiResponse> getTimeStamp(@PathVariable String date)
	{ 
		ApiResponse response = allotmentService.getTimeStampByDate(date); 
		if(response.getStatus()=="success")
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@PostMapping("/{date}/{slot}")
	public ResponseEntity<ApiResponse> allotSlotInDate(@PathVariable("date") String date,@PathVariable("slot") Integer slot)
	{
		System.out.println(date+slot);
		ApiResponse response=allotmentService.allotSlotInDate(date,slot);
		if(response.getStatus()=="success")
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		else 
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
	}
	

}
