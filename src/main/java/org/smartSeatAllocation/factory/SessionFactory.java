package org.smartSeatAllocation.factory;

import org.smartSeatAllocation.domain.Session;
import org.smartSeatAllocation.util.Helper;

public class SessionFactory {

    private SessionFactory() {
    }

    public static Session createSession(String sessionName, String timeSlot, int maxCapacity) {

        if (Helper.isBlank(sessionName) || Helper.isBlank(timeSlot) || maxCapacity <= 0) {
            return null;
        }

        return new Session(sessionName.trim(), timeSlot.trim(), maxCapacity, 0);
    }
}
