export default function RosterView({ sessions, assignedList, unassignParticipant }) {
  return (
    <section className="view-stack">
      <p className="section-label">ASSIGNED PARTICIPANTS ({assignedList.length}/60)</p>

      {sessions.map((session) => {
        const sessionParticipants = assignedList.filter(
          (participant) => participant.assignedSession === session.id,
        )

        return (
          <article key={session.id} className="roster-block">
            <div className="small-row roster-head">
              <div>
                <strong>{session.label}</strong>
                <p className="muted mono">{session.time}</p>
              </div>
              <span className="mono">{sessionParticipants.length}/20</span>
            </div>

            {sessionParticipants.length === 0 ? (
              <p className="muted center">No participants assigned</p>
            ) : (
              <div className="roster-grid">
                {sessionParticipants.map((participant) => (
                  <div key={participant.id} className="roster-card">
                    <div>
                      <strong>{participant.name}</strong>
                      <p className="muted">Div {participant.department}</p>
                    </div>
                    <button
                      type="button"
                      className="remove-button"
                      onClick={() => unassignParticipant(participant.id)}
                    >
                      Remove
                    </button>
                  </div>
                ))}
              </div>
            )}
          </article>
        )
      })}
    </section>
  )
}
