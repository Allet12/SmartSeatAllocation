package org.smartSeatAllocation.controller.dto;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.domain.Participant;

public record ParticipantResponse(Long participantId, String email, Long departmentId, String departmentName) {

    public static ParticipantResponse from(Participant participant) {
        if (participant == null) {
            return null;
        }

        Department department = participant.getDepartment();
        return new ParticipantResponse(
                participant.getParticipantId(),
                participant.getEmail(),
                department != null ? department.getDepartmentId() : null,
                department != null ? department.getDepartmentName() : null
        );
    }
}


