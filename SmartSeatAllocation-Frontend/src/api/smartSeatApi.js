const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:9091/smartseat'

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {}),
    },
    ...options,
  })

  if (response.status === 204) {
    return null
  }

  const contentType = response.headers.get('content-type') || ''
  const payload = contentType.includes('application/json') ? await response.json() : await response.text()

  if (!response.ok) {
    const message = typeof payload === 'string' && payload ? payload : response.statusText
    throw new Error(message || 'Request failed')
  }

  return payload
}

export const smartSeatApi = {
  getDepartments: () => request('/department'),
  getSessions: () => request('/session'),
  getParticipants: () => request('/api/participants'),
  getBookings: () => request('/api/bookings'),
  getAvailableSeats: (sessionId) => request(`/api/sessions/${sessionId}/available-seats`),
  createBooking: (participantId, sessionId) =>
    request('/api/bookings', {
      method: 'POST',
      body: JSON.stringify({ participantId, sessionId }),
    }),
  unassignParticipant: (participantId) =>
    request(`/api/bookings/participant/${participantId}`, {
      method: 'DELETE',
    }),
}

export { API_BASE_URL }

