package org.smartSeatAllocation.factory;

import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.util.Helper;


public class ParticipantFactory {

    private ParticipantFactory() {
    }

    public static Participant createParticipant(String email, String password, String division) {

        if (!Helper.isValidEmail(email)
                || !Helper.isValidPassword(password)
                || Helper.isBlank(division)) {
            return null;
        }

        return new Participant.ParticipantBuilder()
                .setEmail(email.trim().toLowerCase())
                .setPassword(password)
                .setDivision(division.trim())
                .build();
    }




}
