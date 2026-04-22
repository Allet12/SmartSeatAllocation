package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Department;

import java.util.List;

public interface IServiceDepartment extends IService<Department, Long> {
    List<Department> getAll();

}
