package com.slot.entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(CompositeKey.class) 
public class Slot {


	@Id
	private LocalTime start;
	@Id
	private LocalTime end;

	public void setStart(LocalTime start) {
		this.start = start;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	private boolean booked;
	
	@JsonIgnore
	@ManyToOne
	@Id
	private TimeStamp timeStamp;
	
	@JsonGetter(value = "start")
	public String getStartString()
	{
		return  DateTimeFormatter.ofPattern("hh:mm a").format(this.start);

	}
	@JsonGetter(value = "end")
	public String getEndString()
	{
		return  DateTimeFormatter.ofPattern("hh:mm a").format(this.end); 
	}
	public boolean isBooked() {
		return booked;
	}
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	@JsonIgnore
	public LocalTime getStart() {
		return start;
	}
	
	@JsonSetter
	public void setStart(String st) {
		LocalTime start=LocalTime.parse(st,DateTimeFormatter.ofPattern("hh:mm a"));
		this.start = start;
	}
	@JsonIgnore
	public LocalTime getEnd() {
		return end;
	}
	
	
	@JsonSetter
	public void setEnd(String en) {
		LocalTime end=LocalTime.parse(en,DateTimeFormatter.ofPattern("hh:mm a"));
		this.end = end;
	}
	public TimeStamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Slot(LocalTime start, LocalTime end, boolean booked, TimeStamp timeStamp) {
		super();
		this.start = start;
		this.end = end;
		this.booked = booked;
		this.timeStamp = timeStamp;
	}
	public Slot() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Slot [start=" + start + ", end=" + end + ", booked=" + booked +  "]";
	}
	
	
	
}
