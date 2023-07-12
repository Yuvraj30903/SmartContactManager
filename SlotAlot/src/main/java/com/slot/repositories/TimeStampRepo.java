package com.slot.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slot.entities.TimeStamp;

public interface TimeStampRepo extends JpaRepository<TimeStamp, String>{

}
