package org.smartSeatAllocation.service.imp;

import org.smartSeatAllocation.controller.dto.BookingResponse;
import org.smartSeatAllocation.domain.Booking;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.domain.Session;
import org.smartSeatAllocation.factory.BookingFactory;
import org.smartSeatAllocation.repository.BookingRepository;
import org.smartSeatAllocation.repository.ParticipantRepository;
import org.smartSeatAllocation.repository.SessionRepository;
import org.smartSeatAllocation.service.IBookingService;
import org.smartSeatAllocation.service.SeatAllocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final ParticipantRepository participantRepository;
    private final SessionRepository sessionRepository;

    public BookingService(BookingRepository bookingRepository,
                          ParticipantRepository participantRepository,
                          SessionRepository sessionRepository) {
        this.bookingRepository = bookingRepository;
        this.participantRepository = participantRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Booking create(Booking entity) {
        if (entity == null) {
            return null;
        }
        return bookingRepository.save(entity);
    }

    @Override
    public Booking read(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Booking update(Booking entity) {
        if (entity == null) {
            return null;
        }
        return bookingRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional
    public SeatAllocationResponse bookParticipantToSession(long participantId, long sessionId) {
        Optional<Participant> participantOptional = participantRepository.findById(participantId);
        if (participantOptional.isEmpty()) {
            return SeatAllocationResponse.failure("Participant not found", getAvailableSeats(sessionId));
        }

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return SeatAllocationResponse.failure("Session not found", 0);
        }

        Participant participant = participantOptional.get();
        Session session = sessionOptional.get();

        if (bookingRepository.existsByParticipantParticipantIdAndSessionSessionId(participantId, sessionId)) {
            return SeatAllocationResponse.failure("Participant is already booked to this session", getAvailableSeats(sessionId));
        }

        if (bookingRepository.existsByParticipantParticipantId(participantId)) {
            return SeatAllocationResponse.failure("Participant is already booked to a session", getAvailableSeats(sessionId));
        }

        long currentSessionBookings = bookingRepository.countBySessionSessionId(sessionId);
        if (currentSessionBookings >= session.getMaxCapacity()) {
            return SeatAllocationResponse.failure("Session is full", 0);
        }

        if (participant.getDepartment() == null) {
            return SeatAllocationResponse.failure("Participant department not found", getAvailableSeats(sessionId));
        }

        long departmentBookings = bookingRepository
                .countBySessionSessionIdAndParticipantDepartmentDepartmentId(sessionId, participant.getDepartment().getDepartmentId());
        if (departmentBookings >= participant.getDepartment().getMaxPerSession()) {
            return SeatAllocationResponse.failure("Department allocation limit reached for this session", getAvailableSeats(sessionId));
        }

        Booking booking = BookingFactory.createBookingNow(participant, session);
        if (booking == null) {
            return SeatAllocationResponse.failure("Unable to create booking", getAvailableSeats(sessionId));
        }

        Booking savedBooking = bookingRepository.save(booking);
        return SeatAllocationResponse.success(BookingResponse.from(savedBooking), getAvailableSeats(sessionId));
    }

    @Override
    public int getAvailableSeats(long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return 0;
        }

        Session session = sessionOptional.get();
        long bookedSeats = bookingRepository.countBySessionSessionId(sessionId);
        return Math.max(session.getMaxCapacity() - (int) bookedSeats, 0);
    }

    @Override
    public boolean canParticipantBook(long participantId, long sessionId) {
        Optional<Participant> participantOptional = participantRepository.findById(participantId);
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);

        if (participantOptional.isEmpty() || sessionOptional.isEmpty()) {
            return false;
        }

        Participant participant = participantOptional.get();
        Session session = sessionOptional.get();

        if (bookingRepository.existsByParticipantParticipantId(participantId)) {
            return false;
        }

        if (bookingRepository.countBySessionSessionId(sessionId) >= session.getMaxCapacity()) {
            return false;
        }

        if (participant.getDepartment() == null) {
            return false;
        }

        return bookingRepository.countBySessionSessionIdAndParticipantDepartmentDepartmentId(sessionId, participant.getDepartment().getDepartmentId()) < participant.getDepartment().getMaxPerSession();
    }

    @Override
    @Transactional
    public boolean unassignParticipant(long participantId) {
        List<Booking> participantBookings = bookingRepository.findByParticipantParticipantId(participantId);
        if (participantBookings.isEmpty()) {
            return false;
        }

        bookingRepository.delete(participantBookings.get(0));
        return true;
    }
}
