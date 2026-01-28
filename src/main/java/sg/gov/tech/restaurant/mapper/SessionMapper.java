package sg.gov.tech.restaurant.mapper;

import sg.gov.tech.restaurant.dto.response.SessionResponse;
import sg.gov.tech.restaurant.model.Session;

public class SessionMapper {

    private SessionMapper() {
    }

    /**
     * Maps Session entity to SessionResponse DTO.
     * Includes sessionToken, hostUser, status, and winner if session is locked.
     */
    public static SessionResponse toResponse(Session session) {
        SessionResponse response = new SessionResponse();

        response.setSessionToken(session.getSessionToken());
        response.setHostUser(session.getCreatedByUserId());
        response.setStatus(session.getStatus());
        response.setCreatedAt(session.getCreatedAt());

        return response;
    }
}
