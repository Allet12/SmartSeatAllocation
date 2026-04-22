export default function FeedbackToast({ feedback }) {
  if (!feedback) {
    return null
  }

  return <div className={`toast toast-${feedback.type}`}>{feedback.message}</div>
}
