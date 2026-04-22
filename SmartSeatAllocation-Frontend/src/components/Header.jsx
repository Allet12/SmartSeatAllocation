export default function Header({ totalAssigned, totalParticipants, completionPercent }) {
  return (
    <header className="header">
      <div className="header-inner">
        <div>
          <p className="header-badge">DERIVCO GRADUATE PROGRAMME · CAPE TOWN COLAB 2026</p>
          <h1>Smart Seat Allocation</h1>
          <p className="header-sub">Training Session Management Platform</p>
        </div>

        <div className="header-stats">
          <div className="stat-pill">
            <span className="stat-number">{totalAssigned}</span>
            <span className="stat-label">ASSIGNED</span>
          </div>
          <div className="stat-pill">
            <span className="stat-number">{totalParticipants - totalAssigned}</span>
            <span className="stat-label">PENDING</span>
          </div>
          <div className="stat-pill stat-pill-highlight">
            <span className="stat-number stat-number-highlight">{completionPercent}%</span>
            <span className="stat-label">COMPLETE</span>
          </div>
        </div>
      </div>
    </header>
  )
}
