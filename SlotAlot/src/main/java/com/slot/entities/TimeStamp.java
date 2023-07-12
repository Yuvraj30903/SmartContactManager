package com.slot.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class TimeStamp {
	@Id 
	private String date;
	//using 24 hours 
	//0-12 am 
	//12-0 pm 
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "timeStamp")
	private List<Slot> slots;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Slot> getSlots() {
		return slots;
	}
	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}
	
	public TimeStamp(String date, List<Slot> slots) {
		super();
		this.date = date;
		this.slots = slots;
	}
	public TimeStamp() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
