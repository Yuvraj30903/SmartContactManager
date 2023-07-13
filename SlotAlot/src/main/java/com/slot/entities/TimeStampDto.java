package com.slot.entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeStampDto {
	private LocalTime start;
	private LocalTime end;
	private String date;
	public LocalTime getStart() {
		return start;
	}
	public void setStart(LocalTime start) {
		this.start = start;
	}
	public LocalTime getEnd() {
		return end;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setStart(String st) {
		LocalTime start=LocalTime.parse(st,DateTimeFormatter.ofPattern("hh:mm a"));
		this.start = start;
	} 
	public void setEnd(String en) {
		LocalTime end=LocalTime.parse(en,DateTimeFormatter.ofPattern("hh:mm a"));
		this.end = end;
	}
	
	
	

}
