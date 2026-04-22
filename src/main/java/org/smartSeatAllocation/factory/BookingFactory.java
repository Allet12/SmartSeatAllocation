package org.smartSeatAllocation.factory;

import za.ac.smartseat.domain.Booking;
import za.ac.smartseat.domain.Participant;
import za.ac.smartseat.domain.Session;
import za.ac.smartseat.util.Helper;

import java.util.Date;

public class BookingFactory {

    public static Booking createBooking(Participant participant, Session session, Date bookingDate) {


        if (participant == null || session == null || bookingDate == null) {
            return null;
        }


        if (Helper.isNullOrEmpty(participant.getParticipantId().toString()) ||
                Helper.isNullOrEmpty(session.getSessionId().toString())) {
            return null;
        }


        return new Booking.Builder()
                .setBookingId(Long.parseLong(Helper.generateId()))
                .setParticipant(participant)
                .setSession(session)
                .setBookingDate(bookingDate)
                .build();
    }
}