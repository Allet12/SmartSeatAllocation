package org.smartSeatAllocation.factory;

import org.smartSeatAllocation.domain.Booking;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.domain.Session;

import java.util.Date;

public class BookingFactory {

    private BookingFactory() {
    }

    public static Booking createBooking(Participant participant, Session session, Date bookingDate) {
        if (participant == null || session == null || bookingDate == null) {
            return null;
        }

        if (session.getCurrentCapacity() >= session.getMaxCapacity()) {
            return null;
        }

        Date safeBookingDate = new Date(bookingDate.getTime());

        return new Booking.Builder()
                .setParticipant(participant)
                .setSession(session)
                .setBookingDate(safeBookingDate)
                .build();
    }

    public static Booking createBookingNow(Participant participant, Session session) {
        return createBooking(participant, session, new Date());
    }
}