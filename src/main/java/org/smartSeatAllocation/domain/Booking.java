package za.ac.smartseat.domain;

import jakarta.persistence.*;
import org.smartSeatAllocation.domain.Participant;

import java.util.Date;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;

    public Booking() {}

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.participant = builder.participant;
        this.session = builder.session;
        this.bookingDate = builder.bookingDate;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Session getSession() {
        return session;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    // 🔥 Builder Pattern
    public static class Builder {
        private Long bookingId;
        private Participant participant;
        private Session session;
        private Date bookingDate;

        public Builder setBookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setParticipant(Participant participant) {
            this.participant = participant;
            return this;
        }

        public Builder setSession(Session session) {
            this.session = session;
            return this;
        }

        public Builder setBookingDate(Date bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
