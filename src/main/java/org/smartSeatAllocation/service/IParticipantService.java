package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Participant;

import java.util.Optional;

public interface IParticipantService extends IService<Participant, Long> {

    Participant createParticipant(String email, String password, String division);

    Optional<Participant> findByEmail(String email);

    boolean existsByEmail(String email);
}
