package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.domain.Participant;

import java.util.Optional;

public interface IParticipantService extends IService<Participant, Long> {

    Participant createParticipant(String email, String password, long departmentId);

    Participant createParticipant(String email, String password, Department department);

    Optional<Participant> findByEmail(String email);

    boolean existsByEmail(String email);
}

