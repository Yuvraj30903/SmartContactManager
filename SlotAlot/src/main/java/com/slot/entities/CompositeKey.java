package com.slot.entities;

import java.io.Serializable;
import java.time.LocalTime;

public class CompositeKey implements Serializable {
    private LocalTime start;
    private LocalTime end;
    private TimeStamp timeStamp;
    
}