package com.slot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slot.entities.TimeStamp;

public interface TimeStampRepo extends JpaRepository<TimeStamp, String>{

}
