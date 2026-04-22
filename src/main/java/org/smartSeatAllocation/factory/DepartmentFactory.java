package org.smartSeatAllocation.factory;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.util.Helper;

public class DepartmentFactory {

    private DepartmentFactory() {
    }

    public static Department createDepartment(String departmentName, int totalParticipants, int maxPerSession) {

        if (Helper.isBlank(departmentName) || totalParticipants <= 0 || maxPerSession <= 0) {
            return null;
        }

        return new Department.DepartmentBuilder()
                .setDepartmentName(departmentName.trim())
                .setTotalParticipants(totalParticipants)
                .setMaxPerSession(maxPerSession)
                .build();
    }
}
