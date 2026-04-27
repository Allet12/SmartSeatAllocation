package org.smartSeatAllocation.service;

import org.smartSeatAllocation.controller.dto.BookingResponse;

public class SeatAllocationResponse {

    private final boolean valid;
    private final String message;
    private final BookingResponse booking;
    private final int availableSeats;

    public SeatAllocationResponse(boolean valid, String message, BookingResponse booking, int availableSeats) {
        this.valid = valid;
        this.message = message;
        this.booking = booking;
        this.availableSeats = availableSeats;
    }

    public static SeatAllocationResponse success(BookingResponse booking, int availableSeats) {
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

    public BookingResponse getBooking() {
        return booking;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
