package org.smartSeatAllocation.controller.dto;

public record ParticipantCreateRequest(String email, String password, long departmentId) {
}


