package sg.gov.tech.restaurant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sg.gov.tech.restaurant.enums.SessionStatus;
import sg.gov.tech.restaurant.model.PredefinedUser;
import sg.gov.tech.restaurant.model.Session;
import sg.gov.tech.restaurant.model.SessionParticipant;
import sg.gov.tech.restaurant.repository.PredefinedUserRepository;
import sg.gov.tech.restaurant.repository.SessionParticipantRepository;
import sg.gov.tech.restaurant.repository.SessionRepository;
import sg.gov.tech.restaurant.utils.SessionTokenGenerator;

import java.time.Instant;
import java.util.UUID;

/**
 * Service layer for handling Session-related operations.
 * Handles creation of sessions, fetching sessions, and enforcing predefined user rules.
 */
@Service
public class SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private SessionRepository sessionRepository;

    private PredefinedUserRepository predefinedUserRepository;

    private SessionParticipantRepository sessionParticipantRepository;

    private static final int MAX_RETRIES = 5;


    private static final String INVALID_SESSION_SESSION_TOKEN = "Session not found for sessionToken: ";

    private static final String USER_NOT_ALLOW_CREATE_SESSION = "User is not allowed to create a session: ";

    private static final String CANNOT_CREATE_UNIQUE_SESSION = "Failed to generate unique session token";

    private static final Integer SESSION_TOKEN_LENGTH = 6;

    public SessionService(SessionRepository sessionRepository, PredefinedUserRepository predefinedUserRepository, SessionParticipantRepository sessionParticipantRepository) {
		this.sessionRepository = sessionRepository;
        this.predefinedUserRepository = predefinedUserRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
    }

    /**
     * Finds a session entity by its session token.
     *
     * This method is commonly used by guests to:
     * - Join a session
     * - Check session status (OPEN or LOCKED)
     * - View the chosen restaurant if the session is finalized
     *
     * @param sessionToken The public token of the session
     * @return The Session entity
     * @throws IllegalArgumentException if session with the given session token does not exist
     */
    @Transactional(readOnly = true)
    public Session findBySessionToken(String sessionToken) {
        return validateSession(sessionToken);
    }

    /**
     * Creates a new session for a predefined user (host).
     *
     * Rules:
     * - Only users preloaded at application startup may create sessions
     * - Each session is initialized with status OPEN
     * - Unique sessionToken is generated
     * - Token generation is retried in case of collisions
     *
     * @param hostUserId id of the host creating the session
     * @return The newly created Session entity
     * @throws IllegalArgumentException if the user is not predefined
     * @throws IllegalStateException    if unable to generate a unique session after retries
     */
    public Session createSession(String hostUserId) {

        PredefinedUser host = predefinedUserRepository.findByUserId(hostUserId).orElseThrow(
                () -> new IllegalArgumentException(USER_NOT_ALLOW_CREATE_SESSION + hostUserId));

        Session newSession = new Session();
        newSession.setCreatedByUserId(hostUserId);
        newSession.setStatus(SessionStatus.OPEN);
        newSession.setCreatedAt(Instant.now());

        for (int i = 0;i < MAX_RETRIES; i++) {
            try {
                String sessionTokenGenerated = SessionTokenGenerator.generateToken(SESSION_TOKEN_LENGTH);
                logger.info("Generated sessionToken: {}", sessionTokenGenerated);

                newSession.setSessionToken(sessionTokenGenerated);
                newSession.setCreatedAt(Instant.now());
                Session createdSession = sessionRepository.save(newSession);

//                hostUserId
                joinSession(sessionTokenGenerated, host.getUsername());
                return createdSession;
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                // retry
                logger.warn("Duplicate sessionToken detected, retrying...");
            }
        }
        throw new IllegalStateException(CANNOT_CREATE_UNIQUE_SESSION);
    }


    public void joinSession(String sessionToken, String username) {
        Session session = validateSession(sessionToken);
        UUID sessionId = session.getSessionID();

        if(session.getStatus() != SessionStatus.OPEN) {
            throw new IllegalStateException("Session is closed, cannot add user");
        }

        // Check if user already joined the session
        sessionParticipantRepository.findBySessionIdAndUsername(sessionId, username)
                .ifPresent(p -> {
                    throw new IllegalStateException("User already joined this session");
                });
        // Create new participant
        SessionParticipant participant = new SessionParticipant(sessionId, username);
        sessionParticipantRepository.save(participant);
    }

    private Session validateSession(String sessionToken) {
        return sessionRepository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_SESSION_SESSION_TOKEN + sessionToken));
    }

}
