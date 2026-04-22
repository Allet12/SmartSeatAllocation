package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.factory.DepartmentFactory;
import org.smartSeatAllocation.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IServiceDepartment {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department create(Department department) {
        System.out.println("DepartmentService: Creating department: " + department.getDepartmentName());
        Department created = DepartmentFactory.createDepartment(department.getDepartmentName(), department.getTotalParticipants(), department.getMaxPerSession());
        if (created == null) {
            System.out.println("DepartmentService: Failed to create department - invalid input");
            return null;
        }
        Department saved = departmentRepository.save(created);
        System.out.println("DepartmentService: Department created successfully with id: " + saved.getDepartmentId());
        return saved;
    }

    @Override
    public Department read(Long id) {
        System.out.println("DepartmentService: Reading department with id: " + id);
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) System.out.println("DepartmentService: Department not found with id: " + id);
        return department;
    }

    @Override
    public Department update(Department department) {
        System.out.println("DepartmentService: Updating department with id: " + department.getDepartmentId());
        Department updated = departmentRepository.save(department);
        System.out.println("DepartmentService: Department updated successfully");
        return updated;
    }

    @Override
    public void delete(Long id) {
        System.out.println("DepartmentService: Deleting department with id: " + id);
        departmentRepository.deleteById(id);
        System.out.println("DepartmentService: Department deleted successfully");
    }

    @Override
    public List<Department> getAll() {
        System.out.println("DepartmentService: Fetching all departments");
        List<Department> departments = departmentRepository.findAll();
        System.out.println("DepartmentService: Found " + departments.size() + " departments");
        return departments;
    }
}
