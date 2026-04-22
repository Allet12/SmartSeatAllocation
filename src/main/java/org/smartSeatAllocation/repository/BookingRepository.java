package org.smartSeatAllocation.repository;

import org.smartSeatAllocation.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByParticipantParticipantId(long participantId);

    boolean existsByParticipantParticipantIdAndSessionSessionId(long participantId, long sessionId);

    List<Booking> findByParticipantParticipantId(long participantId);

    List<Booking> findBySessionSessionId(long sessionId);

    Optional<Booking> findByParticipantParticipantIdAndSessionSessionId(long participantId, long sessionId);

    long countBySessionSessionId(long sessionId);

    long countBySessionSessionIdAndParticipantDivisionIgnoreCase(long sessionId, String division);
}
