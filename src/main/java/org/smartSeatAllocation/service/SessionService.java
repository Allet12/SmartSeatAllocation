package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Session;
import org.smartSeatAllocation.factory.SessionFactory;
import org.smartSeatAllocation.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService implements IServiceSession {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session create(Session session) {
        System.out.println("SessionService: Creating session: " + session.getSessionName());
        Session created = SessionFactory.createSession(session.getSessionName(), session.getTimeSlot(), session.getMaxCapacity());
        if (created == null) {
            System.out.println("SessionService: Failed to create session - invalid input");
            return null;
        }
        Session saved = sessionRepository.save(created);
        System.out.println("SessionService: Session created successfully with id: " + saved.getSessionId());
        return saved;
    }

    @Override
    public Session read(Long id) {
        System.out.println("SessionService: Reading session with id: " + id);
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) System.out.println("SessionService: Session not found with id: " + id);
        return session;
    }

    @Override
    public Session update(Session session) {
        System.out.println("SessionService: Updating session with id: " + session.getSessionId());
        Session updated = sessionRepository.save(session);
        System.out.println("SessionService: Session updated successfully");
        return updated;
    }

    @Override
    public void delete(Long id) {
        System.out.println("SessionService: Deleting session with id: " + id);
        sessionRepository.deleteById(id);
        System.out.println("SessionService: Session deleted successfully");
    }

    @Override
    public List<Session> getAll() {
        System.out.println("SessionService: Fetching all sessions");
        List<Session> sessions = sessionRepository.findAll();
        System.out.println("SessionService: Found " + sessions.size() + " sessions");
        return sessions;
    }
}
