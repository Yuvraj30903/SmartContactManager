package com.slot.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slot.entities.Slot;
import com.slot.entities.TimeStamp;
import com.slot.repositories.TimeStampRepo;
import com.slot.response.ApiResponse;

@Service
public class AllotmentService {
	
	@Autowired
	private TimeStampRepo timeStampRepo;
	
	
	
	public ApiResponse addDateAndSlot(TimeStamp timeStamp)
	{
		for(Slot s:timeStamp.getSlots())
		{
			s.setTimeStamp(timeStamp);
		}
		try {
			Optional<TimeStamp> findById = timeStampRepo.findById(timeStamp.getDate());
			if(findById.isPresent())throw new Exception("Date already present !!");
			TimeStamp save = timeStampRepo.save(timeStamp);
			return new ApiResponse("success",List.of(save));
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("failed",List.of("Time stamp can't be added!!!"));
			// TODO: handle exception
		}
	}
	
	
	public ApiResponse updateSlot(TimeStamp timeStamp)
	{
		for(Slot s:timeStamp.getSlots())
		{
			s.setTimeStamp(timeStamp);
		}
		
		
		try {
			Optional<TimeStamp> findById = timeStampRepo.findById(timeStamp.getDate());
			if(findById.isEmpty())throw new Exception("Date Not present !!");
			timeStampRepo.save(timeStamp);
			TimeStamp save = timeStampRepo.findById(timeStamp.getDate()).get();
			return new ApiResponse("success",List.of(save)); 
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("failed",List.of("Time stamp can't be updated!!!"));
		}
		
	}

 
	public ApiResponse getTimeStampByDate(String date) {
		try {
			Optional<TimeStamp> findById = timeStampRepo.findById(date);
			if(findById.isEmpty())throw new Exception("Time stamp not found");
			 
			return new ApiResponse("success",List.of(findById.get())); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("failed",List.of("Time stamp not found !!!"));
			// TODO: handle exception
		} 
	}


	public ApiResponse allotSlotInDate(String date, Integer slot) {
	
		try {
			//hh:mm
			Optional<TimeStamp> findById = timeStampRepo.findById(date);
			if(findById.isEmpty())throw new Exception("No Slot available for given date");
			TimeStamp timeStamp = findById.get();
			for(Slot s:timeStamp.getSlots())
			{ 
				if(!s.isBooked())
				{
					int between = (int) ChronoUnit.MINUTES.between(s.getStart(), s.getEnd());
					if(between>slot) {
						timeStampRepo.deleteById(date);
						LocalTime newStart=s.getStart();
						LocalTime newEnd=s.getStart().plusMinutes(slot);
						Slot slot2 = new Slot(newStart,newEnd,true,timeStamp);
						s.setStart(newEnd);
						timeStamp.getSlots().add(slot2); 
						timeStampRepo.save(timeStamp);
						return new ApiResponse("success",List.of(slot2));
					}
					else if(between==slot){
						s.setBooked(true);
						timeStampRepo.save(timeStamp);
						return new ApiResponse("success",List.of(s));
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ApiResponse("failed",List.of("Failed to Allot a slot in given date"));
	}

}
