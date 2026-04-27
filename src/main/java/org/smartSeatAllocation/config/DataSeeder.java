package org.smartSeatAllocation.config;

import org.smartSeatAllocation.domain.Department;
import org.smartSeatAllocation.domain.Participant;
import org.smartSeatAllocation.domain.Session;
import org.smartSeatAllocation.repository.DepartmentRepository;
import org.smartSeatAllocation.repository.ParticipantRepository;
import org.smartSeatAllocation.repository.SessionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(DepartmentRepository departmentRepository,
                               SessionRepository sessionRepository,
                               ParticipantRepository participantRepository) {
        return args -> {
            seedDepartments(departmentRepository);
            seedSessions(sessionRepository);
            seedParticipants(departmentRepository, participantRepository);
        };
    }

    private void seedDepartments(DepartmentRepository departmentRepository) {
        if (departmentRepository.findByDepartmentNameIgnoreCase("Division A").isEmpty()) {
            departmentRepository.save(new Department.DepartmentBuilder()
                    .setDepartmentName("Division A")
                    .setTotalParticipants(24)
                    .setMaxPerSession(8)
                    .build());
        }

        if (departmentRepository.findByDepartmentNameIgnoreCase("Division B").isEmpty()) {
            departmentRepository.save(new Department.DepartmentBuilder()
                    .setDepartmentName("Division B")
                    .setTotalParticipants(18)
                    .setMaxPerSession(6)
                    .build());
        }

        if (departmentRepository.findByDepartmentNameIgnoreCase("Division C").isEmpty()) {
            departmentRepository.save(new Department.DepartmentBuilder()
                    .setDepartmentName("Division C")
                    .setTotalParticipants(18)
                    .setMaxPerSession(6)
                    .build());
        }
    }

    private void seedSessions(SessionRepository sessionRepository) {
        if (sessionRepository.findBySessionNameIgnoreCase("Morning").isEmpty()) {
            sessionRepository.save(new Session("Morning", "09:00 - 10:30", 20, 0));
        }

        if (sessionRepository.findBySessionNameIgnoreCase("Midday").isEmpty()) {
            sessionRepository.save(new Session("Midday", "11:00 - 12:30", 20, 0));
        }

        if (sessionRepository.findBySessionNameIgnoreCase("Afternoon").isEmpty()) {
            sessionRepository.save(new Session("Afternoon", "13:00 - 14:30", 20, 0));
        }
    }

    private void seedParticipants(DepartmentRepository departmentRepository, ParticipantRepository participantRepository) {
        Department divisionA = departmentRepository.findByDepartmentNameIgnoreCase("Division A").orElse(null);
        Department divisionB = departmentRepository.findByDepartmentNameIgnoreCase("Division B").orElse(null);
        Department divisionC = departmentRepository.findByDepartmentNameIgnoreCase("Division C").orElse(null);

        if (divisionA != null) {
            for (int i = 1; i <= 24; i++) {
                createParticipantIfMissing(participantRepository, "a" + String.format("%03d", i) + "@demo.com", divisionA);
            }
        }

        if (divisionB != null) {
            for (int i = 1; i <= 18; i++) {
                createParticipantIfMissing(participantRepository, "b" + String.format("%03d", i) + "@demo.com", divisionB);
            }
        }

        if (divisionC != null) {
            for (int i = 1; i <= 18; i++) {
                createParticipantIfMissing(participantRepository, "c" + String.format("%03d", i) + "@demo.com", divisionC);
            }
        }
    }

    private void createParticipantIfMissing(ParticipantRepository participantRepository, String email, Department department) {
        if (participantRepository.findByEmailIgnoreCase(email).isEmpty()) {
            participantRepository.save(new Participant.ParticipantBuilder()
                    .setEmail(email)
                    .setPassword("password123")
                    .setDepartment(department)
                    .build());
        }
    }
}
