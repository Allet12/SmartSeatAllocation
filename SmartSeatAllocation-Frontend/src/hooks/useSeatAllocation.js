import { useEffect, useMemo, useState } from 'react'
import {
  DEPARTMENTS as FALLBACK_DEPARTMENTS,
  SESSIONS as FALLBACK_SESSIONS,
  TOTAL_PARTICIPANTS,
} from '../data/allocationConfig'
import { smartSeatApi } from '../api/smartSeatApi'

const FEEDBACK_TIMEOUT_MS = 3000

const FALLBACK_DEPARTMENT_CODES = {
  'Division A': 'A',
  'Division B': 'B',
  'Division C': 'C',
}

const getDepartmentCode = (departmentName, fallbackId) => {
  if (departmentName && FALLBACK_DEPARTMENT_CODES[departmentName]) {
    return FALLBACK_DEPARTMENT_CODES[departmentName]
  }

  if (typeof departmentName === 'string') {
    const match = departmentName.match(/([ABC])$/i)
    if (match) {
      return match[1].toUpperCase()
    }
  }

  return fallbackId
}

const normalizeSession = (session) => ({
  id: session.sessionId,
  label: session.sessionName,
  time: session.timeSlot,
  capacity: session.maxCapacity,
})

const normalizeDepartment = (department) => {
  const code = getDepartmentCode(department.departmentName, department.departmentId)
  return {
    id: code,
    backendId: department.departmentId,
    label: department.departmentName,
    total: department.totalParticipants,
    maxPerSession: department.maxPerSession,
    color: code === 'A' ? '#00D4FF' : code === 'B' ? '#FF6B35' : '#7FFF6B',
  }
}

const normalizeBooking = (booking) => ({
  bookingId: booking.bookingId,
  participantId: booking.participantId,
  participantEmail: booking.participantEmail,
  departmentId: booking.departmentId,
  departmentName: booking.departmentName,
  sessionId: booking.sessionId,
  sessionName: booking.sessionName,
  bookingDate: booking.bookingDate,
})

const buildAllocationMatrix = (sessions, departments) =>
  Object.fromEntries(
    sessions.map((session) => [
      session.id,
      Object.fromEntries(departments.map((department) => [department.id, 0])),
    ]),
  )

const buildFallbackData = () => ({
  sessions: FALLBACK_SESSIONS.map((session) => ({
    id: session.id,
    label: session.label,
    time: session.time,
    capacity: session.capacity,
  })),
  departments: FALLBACK_DEPARTMENTS.map((department) => ({
    id: department.id,
    backendId: department.id,
    label: department.label,
    total: department.total,
    maxPerSession: department.maxPerSession,
    color: department.color,
  })),
})

export const useSeatAllocation = () => {
  const fallbackData = useMemo(buildFallbackData, [])
  const [participants, setParticipants] = useState([])
  const [bookings, setBookings] = useState([])
  const [sessions, setSessions] = useState(fallbackData.sessions)
  const [departments, setDepartments] = useState(fallbackData.departments)
  const [selectedSession, setSelectedSession] = useState(null)
  const [selectedParticipant, setSelectedParticipant] = useState(null)
  const [filterDept, setFilterDept] = useState('ALL')
  const [activeView, setActiveView] = useState('dashboard')
  const [feedback, setFeedback] = useState(null)
  const [allocations, setAllocations] = useState(() =>
    buildAllocationMatrix(fallbackData.sessions, fallbackData.departments),
  )

  const showFeedback = (message, type) => {
    setFeedback({ message, type })
    window.clearTimeout(showFeedback.timeoutId)
    showFeedback.timeoutId = window.setTimeout(() => setFeedback(null), FEEDBACK_TIMEOUT_MS)
  }

  const refreshData = async () => {
    const [sessionData, departmentData, participantData, bookingData] = await Promise.all([
      smartSeatApi.getSessions(),
      smartSeatApi.getDepartments(),
      smartSeatApi.getParticipants(),
      smartSeatApi.getBookings(),
    ])

    const normalizedSessions = sessionData.map(normalizeSession)
    const normalizedDepartments = departmentData.map(normalizeDepartment)
    const bookingLookup = new Map(bookingData.map((booking) => [booking.participantId, normalizeBooking(booking)]))
    const departmentById = new Map(normalizedDepartments.map((department) => [department.backendId, department]))

    const normalizedParticipants = participantData.map((participant) => {
      const department = departmentById.get(participant.departmentId)
      const booking = bookingLookup.get(participant.participantId)

      return {
        id: participant.participantId,
        name: participant.email,
        email: participant.email,
        department: department?.id || getDepartmentCode(participant.departmentName, participant.departmentId),
        departmentId: department?.backendId || participant.departmentId,
        departmentName: department?.label || participant.departmentName,
        assignedSession: booking ? booking.sessionId : null,
        assignedSessionName: booking ? booking.sessionName : null,
      }
    })

    const freshAllocations = buildAllocationMatrix(normalizedSessions, normalizedDepartments)
    normalizedParticipants.forEach((participant) => {
      if (!participant.assignedSession || !freshAllocations[participant.assignedSession]) {
        return
      }
      freshAllocations[participant.assignedSession][participant.department] += 1
    })

    setSessions(normalizedSessions)
    setDepartments(normalizedDepartments)
    setParticipants(normalizedParticipants)
    setBookings(bookingData.map(normalizeBooking))
    setAllocations(freshAllocations)

    if (selectedSession && !normalizedSessions.some((session) => session.id === selectedSession)) {
      setSelectedSession(null)
    }

    if (selectedParticipant && !normalizedParticipants.some((participant) => participant.id === selectedParticipant)) {
      setSelectedParticipant(null)
    }
  }

  useEffect(() => {
    refreshData().catch((error) => {
      showFeedback(error.message || 'Failed to load backend data.', 'error')
    })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const getSessionTotal = (sessionId) =>
    Object.values(allocations[sessionId] || {}).reduce((sum, count) => sum + count, 0)

  const getAvailableSeats = (sessionId) => {
    const session = sessions.find((item) => item.id === sessionId)
    if (!session) {
      return 0
    }
    return session.capacity - getSessionTotal(sessionId)
  }

  const getDeptAvailable = (sessionId, deptCode) => {
    const department = departments.find((item) => item.id === deptCode)
    if (!department || !allocations[sessionId]) {
      return 0
    }
    return department.maxPerSession - (allocations[sessionId][deptCode] || 0)
  }

  const assignParticipant = async () => {
    if (!selectedParticipant || !selectedSession) {
      showFeedback('Select both a participant and a session.', 'warn')
      return
    }

    try {
      const response = await smartSeatApi.createBooking(selectedParticipant, selectedSession)
      if (!response?.valid) {
        showFeedback(response?.message || 'Unable to assign participant.', 'error')
        return
      }

      await refreshData()
      showFeedback(response.message || 'Participant assigned successfully.', 'success')
      setSelectedParticipant(null)
    } catch (error) {
      showFeedback(error.message || 'Unable to assign participant.', 'error')
    }
  }

  const unassignParticipant = async (participantId) => {
    try {
      await smartSeatApi.unassignParticipant(participantId)
      await refreshData()
      showFeedback('Participant removed from session.', 'warn')
    } catch (error) {
      showFeedback(error.message || 'Unable to remove participant.', 'error')
    }
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
      sessions,
      departments,
      totalParticipants: TOTAL_PARTICIPANTS,
      bookings,
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
