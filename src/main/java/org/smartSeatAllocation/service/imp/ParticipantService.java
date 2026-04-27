package org.smartSeatAllocation.service.imp;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.factory.ParticipantFactory;
import org.smartSeatAllocation.repository.DepartmentRepository;
import org.smartSeatAllocation.repository.ParticipantRepository;
import org.smartSeatAllocation.service.IParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService implements IParticipantService {

    private final ParticipantRepository participantRepository;
    private final DepartmentRepository departmentRepository;

    public ParticipantService(ParticipantRepository participantRepository, DepartmentRepository departmentRepository) {
        this.participantRepository = participantRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Participant create(Participant entity) {
        if (entity == null) {
            return null;
        }

        Department department = entity.getDepartment();
        return createParticipant(entity.getEmail(), entity.getPassword(), department);
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
    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    @Override
    public Participant createParticipant(String email, String password, long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        return createParticipant(email, password, department);
    }

    @Override
    public Participant createParticipant(String email, String password, Department department) {
        Participant participant = ParticipantFactory.createParticipant(email, password, department);
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
