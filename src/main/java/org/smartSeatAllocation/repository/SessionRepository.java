package org.smartSeatAllocation.repository;

import org.smartSeatAllocation.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
