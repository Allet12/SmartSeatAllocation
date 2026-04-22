package org.smartSeatAllocation.controller;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Department> create(@RequestBody Department department) {
        System.out.println("DepartmentController: POST /department/create - " + department.getDepartmentName());
        Department created = service.create(department);
        if (created == null) {
            System.out.println("DepartmentController: Create failed - bad request");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Department> read(@PathVariable Long id) {
        System.out.println("DepartmentController: GET /department/read/" + id);
        try {
            return ResponseEntity.ok(service.read(id));
        } catch (RuntimeException e) {
            System.out.println("DepartmentController: Department not found with id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        System.out.println("DepartmentController: GET /department");
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("DepartmentController: DELETE /department/delete/" + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Department> update(@RequestBody Department department) {
        System.out.println("DepartmentController: PUT /department/update - id: " + department.getDepartmentId());
        return ResponseEntity.ok(service.update(department));
    }
}
