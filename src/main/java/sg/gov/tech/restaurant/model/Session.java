package sg.gov.tech.restaurant.model;

import java.time.Instant;
import java.util.*;


import jakarta.persistence.*;
import sg.gov.tech.restaurant.enums.SessionStatus;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sessionID;

    private String createdByUserId;

    @Column(unique = true, nullable = false)
    private String sessionToken;

    @Enumerated(EnumType.STRING)
    private SessionStatus status; // OPEN, LOCKED

    private Instant createdAt;

    private Instant endedAt;

    public Session() {
        this.status = SessionStatus.OPEN;
	}

	/**
	 * @return the sessionID
	 */
	public UUID getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(UUID sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * @return the hostUser
	 */
	public String getCreatedByUserId() {
		return createdByUserId;
	}

	/**
	 * @param createdByUserId the hostUser to set
	 */
	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	/**
	 * @return the status
	 */
	public SessionStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
    }

}