package org.smartSeatAllocation.controller;

import org.smartSeatAllocation.controller.dto.AvailableSeatsResponse;
import org.smartSeatAllocation.controller.dto.BookingCreateRequest;
import org.smartSeatAllocation.domain.Booking;
import org.smartSeatAllocation.service.IBookingService;
import org.smartSeatAllocation.service.SeatAllocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<SeatAllocationResponse> createBooking(@RequestBody BookingCreateRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body(SeatAllocationResponse.failure("Booking request is required", 0));
        }

        SeatAllocationResponse response = bookingService.bookParticipantToSession(request.participantId(), request.sessionId());
        if (response.isValid()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(resolveStatus(response.getMessage())).body(response);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable long bookingId) {
        Booking booking = bookingService.read(bookingId);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/sessions/{sessionId}/available-seats")
    public ResponseEntity<AvailableSeatsResponse> getAvailableSeats(@PathVariable long sessionId) {
        return ResponseEntity.ok(new AvailableSeatsResponse(sessionId, bookingService.getAvailableSeats(sessionId)));
    }

    @GetMapping("/sessions/{sessionId}/can-book/{participantId}")
    public ResponseEntity<?> canParticipantBook(@PathVariable long sessionId, @PathVariable long participantId) {
        return ResponseEntity.ok(bookingService.canParticipantBook(participantId, sessionId));
    }

    private HttpStatus resolveStatus(String message) {
        if (message == null) {
            return HttpStatus.BAD_REQUEST;
        }

        String normalizedMessage = message.toLowerCase();
        if (normalizedMessage.contains("not found")) {
            return HttpStatus.NOT_FOUND;
        }
        if (normalizedMessage.contains("already") || normalizedMessage.contains("full") || normalizedMessage.contains("limit")) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
