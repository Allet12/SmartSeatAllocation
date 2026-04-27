package org.smartSeatAllocation.factory;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.util.Helper;


public class ParticipantFactory {

    private ParticipantFactory() {
    }

    public static Participant createParticipant(String email, String password, Department department) {

        if (!Helper.isValidEmail(email)
                || !Helper.isValidPassword(password)
                || department == null) {
            return null;
        }

        return new Participant.ParticipantBuilder()
                .setEmail(email.trim().toLowerCase())
                .setPassword(password)
                .setDepartment(department)
                .build();
    }




}
