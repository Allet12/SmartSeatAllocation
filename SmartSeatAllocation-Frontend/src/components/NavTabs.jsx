const NAV_ITEMS = [
  { id: 'dashboard', icon: '◈', label: 'Dashboard' },
  { id: 'assign', icon: '⊕', label: 'Assign' },
  { id: 'roster', icon: '≡', label: 'Roster' },
]

export default function NavTabs({ activeView, onChange }) {
  return (
    <nav className="nav-tabs" aria-label="Main views">
      {NAV_ITEMS.map((item) => (
        <button
          key={item.id}
          type="button"
          className={`nav-tab ${activeView === item.id ? 'active' : ''}`}
          onClick={() => onChange(item.id)}
        >
          <span>{item.icon}</span>
          {item.label}
        </button>
      ))}
    </nav>
  )
}
