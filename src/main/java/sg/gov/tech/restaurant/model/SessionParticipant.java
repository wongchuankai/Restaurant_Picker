package sg.gov.tech.restaurant.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "session_participant",
        uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "username"})
)
public class SessionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(name = "username", nullable = false)
    private String username;

    private Instant joinedAt;

    public SessionParticipant() {
    }

    public SessionParticipant(UUID sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;
        this.joinedAt = Instant.now();
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }
}
