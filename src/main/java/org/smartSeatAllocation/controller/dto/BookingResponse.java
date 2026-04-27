package org.smartSeatAllocation.controller.dto;

import org.smartSeatAllocation.domain.Booking;

import java.util.Date;

public record BookingResponse(
        Long bookingId,
        Long participantId,
        String participantEmail,
        Long departmentId,
        String departmentName,
        Long sessionId,
        String sessionName,
        Date bookingDate
) {
    public static BookingResponse from(Booking booking) {
        if (booking == null) {
            return null;
        }

        return new BookingResponse(
                booking.getBookingId(),
                booking.getParticipant() != null ? booking.getParticipant().getParticipantId() : null,
                booking.getParticipant() != null ? booking.getParticipant().getEmail() : null,
                booking.getParticipant() != null && booking.getParticipant().getDepartment() != null
                        ? booking.getParticipant().getDepartment().getDepartmentId()
                        : null,
                booking.getParticipant() != null && booking.getParticipant().getDepartment() != null
                        ? booking.getParticipant().getDepartment().getDepartmentName()
                        : null,
                booking.getSession() != null ? booking.getSession().getSessionId() : null,
                booking.getSession() != null ? booking.getSession().getSessionName() : null,
                booking.getBookingDate()
        );
    }
}

