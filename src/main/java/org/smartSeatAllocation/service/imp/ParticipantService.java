package org.smartSeatAllocation.service.imp;

import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.factory.ParticipantFactory;
import org.smartSeatAllocation.repository.ParticipantRepository;
import org.smartSeatAllocation.service.IParticipantService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantService implements IParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public Participant create(Participant entity) {
        if (entity == null) {
            return null;
        }

        return createParticipant(entity.getEmail(), entity.getPassword(), entity.getDivision());
    }

    @Override
    public Participant read(Long id) {
        return participantRepository.findById(id).orElse(null);
    }

    @Override
    public Participant update(Participant entity) {
        if (entity == null) {
            return null;
        }

        return participantRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        participantRepository.deleteById(id);
    }

    @Override
    public Participant createParticipant(String email, String password, String division) {
        Participant participant = ParticipantFactory.createParticipant(email, password, division);
        if (participant == null) {
            return null;
        }

        if (participantRepository.existsByEmailIgnoreCase(participant.getEmail())) {
            return null;
        }

        return participantRepository.save(participant);
    }

    @Override
    public Optional<Participant> findByEmail(String email) {
        return participantRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return participantRepository.existsByEmailIgnoreCase(email);
    }
}
