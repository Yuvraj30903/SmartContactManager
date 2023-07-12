package com.slot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.slot.entities.TimeStamp;

@SpringBootApplication
public class SlotAlotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotAlotApplication.class, args);
//		System.out.println(LocalDate.of(2001, 1, 1));//yyyy-MM-dd
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a",Locale.US);
//		String t=LocalTime.now().format(formatter); 
//		LocalTime t=LocalTime.parse("08:00 PM", formatter);	
//		LocalTime t1=LocalTime.parse("08:00 PM", formatter)	;	
//		System.out.println(t); 
//		System.out.println(t1);
//				System.out.println(ChronoUnit.MINUTES.between(t, t1));
		
	}

}
