package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Booking;

public class SeatAllocationResponse {

    private final boolean valid;
    private final String message;
    private final Booking booking;
    private final int availableSeats;

    public SeatAllocationResponse(boolean valid, String message, Booking booking, int availableSeats) {
        this.valid = valid;
        this.message = message;
        this.booking = booking;
        this.availableSeats = availableSeats;
    }

    public static SeatAllocationResponse success(Booking booking, int availableSeats) {
        return new SeatAllocationResponse(true, "Booking created successfully", booking, availableSeats);
    }

    public static SeatAllocationResponse failure(String message, int availableSeats) {
        return new SeatAllocationResponse(false, message, null, availableSeats);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public Booking getBooking() {
        return booking;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}

