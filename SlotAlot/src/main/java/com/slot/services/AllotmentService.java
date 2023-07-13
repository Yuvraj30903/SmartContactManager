package com.slot.services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slot.entities.Slot;
import com.slot.entities.TimeStamp;
import com.slot.entities.TimeStampDto;
import com.slot.repositories.TimeStampRepo;
import com.slot.response.ApiResponse;

@Service
public class AllotmentService {

	@Autowired
	private TimeStampRepo timeStampRepo;

	
	/* Add a Date and (Slot if required) */

/* Implement that slot does not repeat or clashes */
	public ApiResponse addDateAndSlot(TimeStamp timeStamp) {
			try {
				for (Slot s : timeStamp.getSlots()) {
					s.setTimeStamp(timeStamp);
					if(s.getEnd().compareTo(s.getStart()) != 1)
						throw new Exception("Invalid input");
				}
				boolean valid=checkValidityOfSlots(timeStamp.getSlots());
				if(!valid)throw new Exception("Slots are invalid");
			Optional<TimeStamp> findById = timeStampRepo.findById(timeStamp.getDate());
			if (findById.isPresent())
				throw new Exception("Date already present !!");
			TimeStamp save = timeStampRepo.save(timeStamp);
			return new ApiResponse("success", List.of(save));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
			return new ApiResponse("failed", List.of("Time stamp can't be added!!!"));
	}
	
	
	private boolean checkValidityOfSlots(List<Slot> slots) {
		for(int i=0;i<slots.size();i++)
		{
			Slot slotOfI = slots.get(i);
			LocalTime start1 = slotOfI.getStart();
			LocalTime end1 = slotOfI.getEnd();
			for(int j=i+1;j<slots.size();j++)
			{
				Slot slotOfJ=slots.get(j);
				LocalTime start2 = slotOfJ.getStart();
				LocalTime end2 = slotOfJ.getEnd();
				// start1  start2 end2 end1
				//start2 start1 end1  end2 
				if( !(start2.compareTo(end1)!=-1 || start1.compareTo(end2)!=-1))
					return false;
				
			}
		}
		
		
		
		return true;
	}


	/* Add slots to a date which is already added */
/* Implement that slot does not repeat or clashes */
	public ApiResponse updateSlot(TimeStamp timeStamp) {

		try {
			for (Slot s : timeStamp.getSlots()) {
				s.setTimeStamp(timeStamp);
				if(s.getEnd().compareTo(s.getStart()) != 1)
					throw new Exception("Invalid input");
			}
			boolean valid=checkValidityOfSlots(timeStamp.getSlots());
			if(!valid)throw new Exception("Slots are invalid");
			
			Optional<TimeStamp> findById = timeStampRepo.findById(timeStamp.getDate());
			if (findById.isEmpty())
				throw new Exception("Date Not present !!");
			timeStampRepo.save(timeStamp);
			TimeStamp save = timeStampRepo.findById(timeStamp.getDate()).get();
			return new ApiResponse("success", List.of(save));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ApiResponse("failed", List.of("Time stamp can't be updated!!!"));
		}

	}

	
	/* Get Slot available for Given Date */
	public ApiResponse getTimeStampByDate(String date) {
		try {
			Optional<TimeStamp> findById = timeStampRepo.findById(date);
			if (findById.isEmpty())
				throw new Exception("Time stamp not found");
			TimeStamp save = findById.get();
			List<Slot> list = save.getSlots().stream().filter(e->e.isBooked()==false).collect(Collectors.toList());
			save.setSlots(list);
			return new ApiResponse("success", List.of(save));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		return new ApiResponse("failed", List.of("Time stamp not found !!!"));
	}

	/* Allotment of Slot in given date and slot time  */

	public ApiResponse allotSlotInDate(TimeStampDto timeStampDto) {

		try { 
			if(timeStampDto.getEnd().compareTo(timeStampDto.getStart()) != 1)
				throw new Exception("Invalid input");
			// hh:mm
			Optional<TimeStamp> findById = timeStampRepo.findById(timeStampDto.getDate());
			if (findById.isEmpty())
				throw new Exception("No Slot available for given date");
			TimeStamp timeStamp = findById.get();
			LocalTime startDto = timeStampDto.getStart();
			LocalTime endDto = timeStampDto.getEnd(); 
			for (Slot s : timeStamp.getSlots()) {
				if (!s.isBooked()) {
					LocalTime start = s.getStart();
					LocalTime end = s.getEnd();

					if (startDto.compareTo(start) != -1 && end.compareTo(endDto) != -1) {
						timeStampRepo.delete(timeStamp);
						timeStamp.getSlots().remove(s);
						if (!start.equals(startDto))
							timeStamp.getSlots().add(new Slot(start, startDto, false, timeStamp));
						timeStamp.getSlots().add(new Slot(startDto, endDto, true, timeStamp));

						if (!end.equals(endDto))
							timeStamp.getSlots().add(new Slot(endDto, end, false, timeStamp));
						timeStampRepo.save(timeStamp);
						return new ApiResponse("success", List.of("Given Slot Alloted to you successfully"));
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ApiResponse("failed", List.of("Failed to Allot a slot in given date"));
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
