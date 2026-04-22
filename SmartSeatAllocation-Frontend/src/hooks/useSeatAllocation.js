import { useMemo, useState } from 'react'
import {
  DEPARTMENTS,
  DEPARTMENT_MAP,
  SESSIONS,
  SESSION_MAP,
  TOTAL_PARTICIPANTS,
} from '../data/allocationConfig'
import { createInitialAllocations, generateParticipants } from '../utils/participants'

const FEEDBACK_TIMEOUT_MS = 3000

export const useSeatAllocation = () => {
  const [participants, setParticipants] = useState(generateParticipants)
  const [allocations, setAllocations] = useState(createInitialAllocations)
  const [selectedSession, setSelectedSession] = useState(null)
  const [selectedParticipant, setSelectedParticipant] = useState(null)
  const [filterDept, setFilterDept] = useState('ALL')
  const [activeView, setActiveView] = useState('dashboard')
  const [feedback, setFeedback] = useState(null)

  const showFeedback = (message, type) => {
    setFeedback({ message, type })
    window.clearTimeout(showFeedback.timeoutId)
    showFeedback.timeoutId = window.setTimeout(() => setFeedback(null), FEEDBACK_TIMEOUT_MS)
  }

  const getSessionTotal = (sessionId) =>
    Object.values(allocations[sessionId]).reduce((sum, count) => sum + count, 0)

  const getAvailableSeats = (sessionId) => SESSION_MAP[sessionId].capacity - getSessionTotal(sessionId)

  const getDeptAvailable = (sessionId, deptId) =>
    DEPARTMENT_MAP[deptId].maxPerSession - allocations[sessionId][deptId]

  const assignParticipant = () => {
    if (!selectedParticipant || !selectedSession) {
      showFeedback('Select both a participant and a session.', 'warn')
      return
    }

    const participant = participants.find((item) => item.id === selectedParticipant)
    if (!participant) {
      showFeedback('Participant not found.', 'error')
      return
    }

    if (participant.assignedSession) {
      showFeedback(
        `${participant.name} is already assigned to ${SESSION_MAP[participant.assignedSession].label}.`,
        'error',
      )
      return
    }

    if (getAvailableSeats(selectedSession) <= 0) {
      showFeedback('Hard limit reached: this session is full.', 'error')
      return
    }

    if (getDeptAvailable(selectedSession, participant.department) <= 0) {
      showFeedback(
        `Hard limit reached: ${DEPARTMENT_MAP[participant.department].label} has no seats left in this session.`,
        'error',
      )
      return
    }

    setParticipants((prev) =>
      prev.map((item) =>
        item.id === participant.id ? { ...item, assignedSession: selectedSession } : item,
      ),
    )

    setAllocations((prev) => ({
      ...prev,
      [selectedSession]: {
        ...prev[selectedSession],
        [participant.department]: prev[selectedSession][participant.department] + 1,
      },
    }))

    showFeedback(`Allocated ${participant.name} to ${SESSION_MAP[selectedSession].label}.`, 'success')
    setSelectedParticipant(null)
  }

  const unassignParticipant = (participantId) => {
    const participant = participants.find((item) => item.id === participantId)
    if (!participant || !participant.assignedSession) {
      return
    }

    const sessionId = participant.assignedSession

    setParticipants((prev) =>
      prev.map((item) =>
        item.id === participantId ? { ...item, assignedSession: null } : item,
      ),
    )

    setAllocations((prev) => ({
      ...prev,
      [sessionId]: {
        ...prev[sessionId],
        [participant.department]: prev[sessionId][participant.department] - 1,
      },
    }))

    showFeedback(`Removed ${participant.name} from ${SESSION_MAP[sessionId].label}.`, 'warn')
  }

  const assignedList = useMemo(
    () => participants.filter((participant) => participant.assignedSession),
    [participants],
  )

  const unassignedList = useMemo(
    () =>
      participants.filter(
        (participant) =>
          !participant.assignedSession &&
          (filterDept === 'ALL' || participant.department === filterDept),
      ),
    [participants, filterDept],
  )

  const totalAssigned = assignedList.length
  const completionPercent = Math.round((totalAssigned / TOTAL_PARTICIPANTS) * 100)

  return {
    data: {
      sessions: SESSIONS,
      departments: DEPARTMENTS,
      totalParticipants: TOTAL_PARTICIPANTS,
    },
    state: {
      activeView,
      selectedSession,
      selectedParticipant,
      filterDept,
      feedback,
      allocations,
      unassignedList,
      assignedList,
      totalAssigned,
      completionPercent,
    },
    actions: {
      setActiveView,
      setSelectedSession,
      setSelectedParticipant,
      setFilterDept,
      assignParticipant,
      unassignParticipant,
    },
    selectors: {
      getSessionTotal,
      getAvailableSeats,
      getDeptAvailable,
    },
  }
}
