package org.smartSeatAllocation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class participant {

    @Id
    @GeneratedValue()
    private long participantId;
    private String email;
    private String division;
}
