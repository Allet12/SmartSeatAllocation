export const SESSIONS = [
  { id: 'morning', label: 'Morning', time: '09:00 - 10:30', capacity: 20 },
  { id: 'midday', label: 'Midday', time: '11:00 - 12:30', capacity: 20 },
  { id: 'afternoon', label: 'Afternoon', time: '13:00 - 14:30', capacity: 20 },
]

export const DEPARTMENTS = [
  { id: 'A', label: 'Division A', total: 24, maxPerSession: 8, color: '#00D4FF' },
  { id: 'B', label: 'Division B', total: 18, maxPerSession: 6, color: '#FF6B35' },
  { id: 'C', label: 'Division C', total: 18, maxPerSession: 6, color: '#7FFF6B' },
]

export const TOTAL_PARTICIPANTS = DEPARTMENTS.reduce(
  (sum, department) => sum + department.total,
  0,
)

export const SESSION_MAP = Object.fromEntries(
  SESSIONS.map((session) => [session.id, session]),
)

export const DEPARTMENT_MAP = Object.fromEntries(
  DEPARTMENTS.map((department) => [department.id, department]),
)
