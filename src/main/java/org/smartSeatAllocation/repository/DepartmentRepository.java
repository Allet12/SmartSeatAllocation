package org.smartSeatAllocation.repository;

import org.smartSeatAllocation.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
