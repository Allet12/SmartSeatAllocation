import { DEPARTMENTS, SESSIONS } from '../data/allocationConfig'

export const generateParticipants = () => {
  const participants = []

  DEPARTMENTS.forEach((department) => {
    for (let i = 1; i <= department.total; i += 1) {
      const suffix = String(i).padStart(3, '0')
      participants.push({
        id: `${department.id}${suffix}`,
        name: `Participant ${department.id}${suffix}`,
        department: department.id,
        assignedSession: null,
      })
    }
  })

  return participants
}

export const createInitialAllocations = () =>
  Object.fromEntries(
    SESSIONS.map((session) => [
      session.id,
      Object.fromEntries(DEPARTMENTS.map((department) => [department.id, 0])),
    ]),
  )
