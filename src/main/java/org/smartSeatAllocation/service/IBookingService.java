package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Booking;

public interface IBookingService extends IService<Booking, Long> {

    SeatAllocationResponse bookParticipantToSession(long participantId, long sessionId);

    int getAvailableSeats(long sessionId);

    boolean canParticipantBook(long participantId, long sessionId);
}
