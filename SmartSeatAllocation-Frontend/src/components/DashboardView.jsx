export default function DashboardView({
  sessions,
  departments,
  allocations,
  getAvailableSeats,
  getSessionTotal,
}) {
  return (
    <section className="view-stack">
      <p className="section-label">SESSION OVERVIEW</p>
      <div className="sessions-grid">
        {sessions.map((session) => {
          const total = getSessionTotal(session.id)
          const percent = (total / session.capacity) * 100

          return (
            <article key={session.id} className="card">
              <div className="card-head">
                <div>
                  <h2>{session.label}</h2>
                  <p className="muted mono">{session.time}</p>
                </div>
                <p className="counter mono">{total}/20</p>
              </div>

              <div className="progress-bg">
                <div className="progress-fill" style={{ width: `${percent}%` }} />
              </div>

              <p className="small-row">
                <span>Available seats</span>
                <strong className="mono">{getAvailableSeats(session.id)}</strong>
              </p>

              <div className="department-lines">
                {departments.map((department) => (
                  <p key={`${session.id}-${department.id}`} className="small-row">
                    <span>{department.label}</span>
                    <span className="mono">
                      {allocations[session.id][department.id]}/{department.maxPerSession}
                    </span>
                  </p>
                ))}
              </div>
            </article>
          )
        })}
      </div>
    </section>
  )
}
