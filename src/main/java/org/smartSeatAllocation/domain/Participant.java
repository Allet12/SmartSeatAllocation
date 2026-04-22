package org.smartSeatAllocation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long participantId;
    private String email;
    private String division;

    public Participant() {
    }

    public Participant(String email, String division) {
        this.email = email;
        this.division = division;
    }

    public Participant(ParticipantBuilder participantBuilder) {
        this.email = participantBuilder.email;
        this.division = participantBuilder.division;
    }

    public long getParticipantId() {
        return participantId;
    }

    public String getEmail() {
        return email;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return "Participant{" +
                ", email='" + email + '\'' +
                ", division='" + division + '\'' +
                '}';
    }

    public static class ParticipantBuilder {
        private String email;
        private String division;

        public ParticipantBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public ParticipantBuilder setDivision(String division) {
            this.division = division;
            return this;
        }

        public ParticipantBuilder copy (Participant participant){
            this.email= participant.email;
            this.division = participant.division;
            return this;
        }

        public Participant build(){
            return new Participant(this);
        }
    }
}
