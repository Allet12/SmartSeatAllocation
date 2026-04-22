package org.smartSeatAllocation.controller;

import org.smartSeatAllocation.controller.dto.ParticipantCreateRequest;
import org.smartSeatAllocation.controller.dto.ParticipantResponse;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.service.IParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
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

        Participant participant = participantService.createParticipant(request.email(), request.password(), request.division());
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

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getParticipantByEmail(@PathVariable String email) {
        Optional<Participant> participantOptional = participantService.findByEmail(email);
        if (participantOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }
        return ResponseEntity.ok(ParticipantResponse.from(participantOptional.get()));
    }
}
