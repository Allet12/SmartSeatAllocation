package org.smartSeatAllocation.service;

import org.smartSeatAllocation.domain.Session;

import java.util.List;

public interface IServiceSession extends IService<Session, Long> {
    List<Session> getAll();
}
