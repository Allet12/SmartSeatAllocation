package org.smartSeatAllocation.controller;

import org.smartSeatAllocation.controller.dto.ParticipantCreateRequest;
import org.smartSeatAllocation.controller.dto.ParticipantResponse;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.service.IParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    private final IParticipantService participantService;

    public ParticipantController(IParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<?> createParticipant(@RequestBody ParticipantCreateRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Participant request is required");
        }

        Participant participant = participantService.createParticipant(request.email(), request.password(), request.departmentId());
        if (participant == null) {
            if (request.email() != null && participantService.existsByEmail(request.email())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Participant email already exists");
            }
            return ResponseEntity.badRequest().body("Invalid participant details");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ParticipantResponse.from(participant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable long id) {
        Participant participant = participantService.read(id);
        if (participant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }
        return ResponseEntity.ok(ParticipantResponse.from(participant));
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAll().stream().map(ParticipantResponse::from).toList());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getParticipantByEmail(@PathVariable String email) {
        Optional<Participant> participantOptional = participantService.findByEmail(email);
        if (participantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }
        return ResponseEntity.ok(ParticipantResponse.from(participantOptional.get()));
    }
}
