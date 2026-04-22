package org.smartSeatAllocation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long departmentId;
    private String departmentName;  // "Division A", "Division B", "Division C"
    private int totalParticipants;  // 24, 18, 18
    private int maxPerSession;      // 8, 6, 6

    public Department() {
    }

    public Department(String departmentName, int totalParticipants, int maxPerSession) {
        this.departmentName = departmentName;
        this.totalParticipants = totalParticipants;
        this.maxPerSession = maxPerSession;
    }

    public Department(DepartmentBuilder departmentBuilder) {
        this.departmentName = departmentBuilder.departmentName;
        this.totalParticipants = departmentBuilder.totalParticipants;
        this.maxPerSession = departmentBuilder.maxPerSession;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public int getMaxPerSession() {
        return maxPerSession;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", totalParticipants=" + totalParticipants +
                ", maxPerSession=" + maxPerSession +
                '}';
    }

    public static class DepartmentBuilder {
        private String departmentName;
        private int totalParticipants;
        private int maxPerSession;

        public DepartmentBuilder setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public DepartmentBuilder setTotalParticipants(int totalParticipants) {
            this.totalParticipants = totalParticipants;
            return this;
        }

        public DepartmentBuilder setMaxPerSession(int maxPerSession) {
            this.maxPerSession = maxPerSession;
            return this;
        }

        public DepartmentBuilder copy(Department department) {
            this.departmentName = department.departmentName;
            this.totalParticipants = department.totalParticipants;
            this.maxPerSession = department.maxPerSession;
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }
}