package sg.gov.tech.restaurant.dto.response;

import sg.gov.tech.restaurant.enums.SessionStatus;

import java.time.Instant;

public class SessionResponse {

    private String sessionToken;   // public short code for guests
    private String hostUser;     // the user who created the session
    private SessionStatus status; // OPEN or LOCKED
    private Instant createdAt;

    public SessionResponse() {
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getHostUser() {
        return hostUser;
    }

    public void setHostUser(String hostUser) {
        this.hostUser = hostUser;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
