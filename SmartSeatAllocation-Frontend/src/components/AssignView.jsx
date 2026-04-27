export default function AssignView({
  sessions,
  departments,
  selectedSession,
  selectedParticipant,
  filterDept,
  unassignedList,
  allocations,
  setSelectedSession,
  setSelectedParticipant,
  setFilterDept,
  assignParticipant,
  getAvailableSeats,
  getDeptAvailable,
  getSessionTotal,
}) {
  return (
    <section className="assign-grid">
      <article>
        <p className="section-label">1 - SELECT SESSION</p>
        <div className="stack-sm">
          {sessions.map((session) => {
            const isActive = selectedSession === session.id
            return (
              <button
                key={session.id}
                type="button"
                className={`session-button ${isActive ? 'active' : ''}`}
                onClick={() => setSelectedSession(session.id)}
              >
                <div className="small-row">
                  <strong>{session.label}</strong>
                  <strong className="mono">{getAvailableSeats(session.id)} free</strong>
                </div>
                <p className="muted mono">{session.time}</p>
                <p className="muted">{getSessionTotal(session.id)}/20 filled</p>

                {isActive && (
                  <div className="department-lines">
                    {departments.map((department) => (
                      <p key={`${session.id}-${department.id}`} className="small-row">
                        <span>{department.label}</span>
                        <span className="mono">
                          {getDeptAvailable(session.id, department.id)} left
                        </span>
                      </p>
                    ))}
                  </div>
                )}
              </button>
            )
          })}
        </div>
      </article>

      <article>
        <div className="small-row margin-bottom">
          <p className="section-label">2 - SELECT PARTICIPANT</p>
          <div className="filter-row">
            {['ALL', 'A', 'B', 'C'].map((deptId) => (
              <button
                key={deptId}
                type="button"
                className={`chip ${filterDept === deptId ? 'active' : ''}`}
                onClick={() => setFilterDept(deptId)}
              >
                {deptId === 'ALL' ? 'All' : `Dept ${deptId}`}
              </button>
            ))}
          </div>
        </div>

        <div className="participant-list">
          {unassignedList.length === 0 && <p className="muted center">All participants assigned</p>}

          {unassignedList.map((participant) => (
            <button
              key={participant.id}
              type="button"
              className={`participant-row ${selectedParticipant === participant.id ? 'active' : ''}`}
              onClick={() => setSelectedParticipant(participant.id)}
            >
              <span>{participant.name}</span>
              <span className="mono">Dept {participant.department}</span>
            </button>
          ))}
        </div>

        <p className="section-label">3 - CONFIRM</p>
        <div className="confirm-card">
          <p className="small-row">
            <span>Session</span>
            <strong>{selectedSession || 'none'}</strong>
          </p>
          <p className="small-row">
            <span>Participant</span>
            <strong>{selectedParticipant || 'none'}</strong>
          </p>
        </div>

        <button type="button" className="action-button" onClick={assignParticipant}>
          ASSIGN
        </button>
      </article>
    </section>
  )
}
