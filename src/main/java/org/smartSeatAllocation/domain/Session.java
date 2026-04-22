package org.smartSeatAllocation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    private String sessionName;
    private String timeSlot;
    private int maxCapacity = 20;
    private int currentCapacity = 0;

    public Session(){

    }

    public Session(String sessionName,  String timeSlot, int maxCapacity, int currentCapacity) {
        this.sessionName = sessionName;
        this.timeSlot = timeSlot;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;

    }

    public long getSessionId() {
        return sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }


}
