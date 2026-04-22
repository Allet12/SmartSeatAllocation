package org.smartSeatAllocation.controller.dto;

import org.smartSeatAllocation.domain.Participant;

public record ParticipantResponse(Long participantId, String email, String division) {

    public static ParticipantResponse from(Participant participant) {
        if (participant == null) {
            return null;
        }
        return new ParticipantResponse(participant.getParticipantId(), participant.getEmail(), participant.getDivision());
    }
}

