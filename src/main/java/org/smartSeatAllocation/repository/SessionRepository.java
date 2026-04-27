package org.smartSeatAllocation.repository;

import org.smartSeatAllocation.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

	Optional<Session> findBySessionNameIgnoreCase(String sessionName);
}
