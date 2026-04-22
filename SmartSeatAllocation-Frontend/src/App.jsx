import AssignView from './components/AssignView'
import DashboardView from './components/DashboardView'
import FeedbackToast from './components/FeedbackToast'
import Header from './components/Header'
import NavTabs from './components/NavTabs'
import RosterView from './components/RosterView'
import { useSeatAllocation } from './hooks/useSeatAllocation'
import './styles/stylng.css'

function App() {
  const { data, state, actions, selectors } = useSeatAllocation()

  return (
    <div className="root">
      <Header
        totalAssigned={state.totalAssigned}
        totalParticipants={data.totalParticipants}
        completionPercent={state.completionPercent}
      />

      <NavTabs activeView={state.activeView} onChange={actions.setActiveView} />
      <FeedbackToast feedback={state.feedback} />

      <main className="main">
        {state.activeView === 'dashboard' && (
          <DashboardView
            sessions={data.sessions}
            departments={data.departments}
            allocations={state.allocations}
            getAvailableSeats={selectors.getAvailableSeats}
            getSessionTotal={selectors.getSessionTotal}
          />
        )}

        {state.activeView === 'assign' && (
          <AssignView
            sessions={data.sessions}
            departments={data.departments}
            selectedSession={state.selectedSession}
            selectedParticipant={state.selectedParticipant}
            filterDept={state.filterDept}
            unassignedList={state.unassignedList}
            allocations={state.allocations}
            setSelectedSession={actions.setSelectedSession}
            setSelectedParticipant={actions.setSelectedParticipant}
            setFilterDept={actions.setFilterDept}
            assignParticipant={actions.assignParticipant}
            getAvailableSeats={selectors.getAvailableSeats}
            getDeptAvailable={selectors.getDeptAvailable}
            getSessionTotal={selectors.getSessionTotal}
          />
        )}

        {state.activeView === 'roster' && (
          <RosterView
            sessions={data.sessions}
            assignedList={state.assignedList}
            unassignParticipant={actions.unassignParticipant}
          />
        )}

        <section className="hard-limits" aria-label="Hard limits enforced">
          <h2>Hard Limits Enforced</h2>
          <ul>
            <li>Maximum 20 participants per session</li>
            <li>A participant can only be assigned once</li>
            <li>Division caps per session: A=8, B=6, C=6</li>
          </ul>
        </section>
      </main>
    </div>
  )
}

export default App
