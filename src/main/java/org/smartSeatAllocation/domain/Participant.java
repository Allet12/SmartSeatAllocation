package org.smartSeatAllocation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long participantId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    public Participant() {
    }

    public Participant(String email, String password, Department department) {
        this.email = email;
        this.password = password;
        this.department = department;
    }

    public Participant(ParticipantBuilder participantBuilder) {
        this.email = participantBuilder.email;
        this.password = participantBuilder.password;
        this.department = participantBuilder.department;
    }

    public long getParticipantId() {
        return participantId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantId=" + participantId +
                ", email='" + email + '\'' +
                ", department=" + (department != null ? department.getDepartmentName() : null) +
                '}';
    }

    public static class ParticipantBuilder {
        private String email;
        private String password;
        private Department department;

        public ParticipantBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public ParticipantBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public ParticipantBuilder setDepartment(Department department) {
            this.department = department;
            return this;
        }

        public ParticipantBuilder copy(Participant participant) {
            this.email = participant.email;
            this.password = participant.password;
            this.department = participant.department;
            return this;
        }

        public Participant build() {
            return new Participant(this);
        }
    }
}
