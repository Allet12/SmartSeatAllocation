package org.smartSeatAllocation.controller;

import org.smartSeatAllocation.domain.Session;
import org.smartSeatAllocation.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:5173")
public class SessionController {

    private final SessionService service;

    @Autowired
    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Session> create(@RequestBody Session session) {
        System.out.println("SessionController: POST /session/create - " + session.getSessionName());
        Session created = service.create(session);
        if (created == null) {
            System.out.println("SessionController: Create failed - bad request");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Session> read(@PathVariable Long id) {
        System.out.println("SessionController: GET /session/read/" + id);
        try {
            return ResponseEntity.ok(service.read(id));
        } catch (RuntimeException e) {
            System.out.println("SessionController: Session not found with id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Session>> getAll() {
        System.out.println("SessionController: GET /session");
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("SessionController: DELETE /session/delete/" + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Session> update(@RequestBody Session session) {
        System.out.println("SessionController: PUT /session/update - id: " + session.getSessionId());
        return ResponseEntity.ok(service.update(session));
    }
}
