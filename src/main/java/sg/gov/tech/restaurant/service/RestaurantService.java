package sg.gov.tech.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sg.gov.tech.restaurant.dto.request.RestaurantRequest;
import sg.gov.tech.restaurant.enums.SessionStatus;
import sg.gov.tech.restaurant.model.Restaurant;
import sg.gov.tech.restaurant.model.Session;
import sg.gov.tech.restaurant.model.SessionRestaurant;
import sg.gov.tech.restaurant.repository.RestaurantRepository;
import sg.gov.tech.restaurant.repository.SessionParticipantRepository;
import sg.gov.tech.restaurant.repository.SessionRestaurantRepository;
import sg.gov.tech.restaurant.utils.GeoValidator;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;

/**
 * Service layer for handling Restaurant-related operations.
 * Includes adding restaurants, fetching restaurants, and finalizing sessions with random selection.
 */
@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    private SessionRestaurantRepository sessionRestaurantRepository;

    private SessionParticipantRepository sessionParticipantRepository;

    private SessionService sessionService;

    private static final String SESSION_ALREADY_CLOSED_ERROR = "Session is already locked";

    private static final String REQUESTEDUSER_IS_NOT_CREATOR_ERROR = "User did not create the session";

    public RestaurantService(RestaurantRepository restaurantRepository, SessionService sessionService,
                             SessionRestaurantRepository sessionRestaurantRepository,
                             SessionParticipantRepository sessionParticipantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.sessionService = sessionService;
        this.sessionRestaurantRepository = sessionRestaurantRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
    }

    /**
     * Adds a restaurant to a session identified by the session token.
     *
     * Rules:
     * - Only sessions with status OPEN may accept new restaurants
     * - Submissions are rejected once the session is LOCKED
     *
     * @param sessionToken     Public token for session
     * @param addRestaurantRequest addRestaurantRequest
     * @return Restaurant entity
     * @throws IllegalStateException if the session is not OPEN
     */
    public Restaurant addRestaurant(String sessionToken, RestaurantRequest addRestaurantRequest) {
        Session session = sessionService.findBySessionToken(sessionToken);
        if(session.getStatus() != SessionStatus.OPEN) {
            throw new IllegalStateException("Session is closed, cannot add restaurants");
        }
        String restaurantName = addRestaurantRequest.getRestaurantName();
        double latitude = addRestaurantRequest.getLatitude();
        double longitude = addRestaurantRequest.getLongitude();
        String submittedBy = addRestaurantRequest.getSubmittedBy();

        if (!GeoValidator.isValidCoordinate(latitude, longitude)) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }

        if(!sessionParticipantRepository.existsBySessionIdAndUsername(session.getSessionID(), submittedBy)) {
            throw new IllegalStateException("User is not part of the session");
        }
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setSessionId(session.getSessionID());
        newRestaurant.setRestaurantName(restaurantName);
        newRestaurant.setLatitude(latitude);
        newRestaurant.setLongitude(longitude);
        newRestaurant.setSubmittedBy(submittedBy);
        newRestaurant.setSubmittedAt(Instant.now());
        return restaurantRepository.save(newRestaurant);
    }

    /**
     * Randomly selects a restaurant from all submitted choices and finalizes the session.
     *
     * Rules:
     * - The session must be in OPEN state
     * - At least one restaurant must have been submitted
     * - Once selected, the session is LOCKED and no further submissions are allowed
     *
     * @param sessionToken   Public token for session
     * @param requestedBy Username of the user requesting the random selection
     * @return The randomly chosen Restaurant
     * @throws IllegalStateException if the session is already LOCKED or has no restaurants
     */
    @Transactional
    public Restaurant randomSelectRestaurant(String sessionToken, String requestedBy) {
        Session session = sessionService.findBySessionToken(sessionToken);

        if (session.getStatus() == SessionStatus.LOCKED) {
            throw new IllegalStateException(SESSION_ALREADY_CLOSED_ERROR);
        }

        if (requestedBy != null && !requestedBy.equals(session.getCreatedByUserId())) {
            throw new IllegalStateException(REQUESTEDUSER_IS_NOT_CREATOR_ERROR + ": " + requestedBy);
        }

        List<Restaurant> restaurants =
                getAllRestaurantsGivenSessionToken(session);

        if (restaurants.isEmpty()) {
            throw new IllegalStateException("No restaurants submitted");
        }

        Restaurant chosen =
                restaurants.get(new SecureRandom().nextInt(restaurants.size()));

        session.setStatus(SessionStatus.LOCKED);
        SessionRestaurant createSessionRestaurant = new SessionRestaurant(session.getSessionID(),
                chosen.getRestaurantId());
        sessionRestaurantRepository.save(createSessionRestaurant);
        session.setEndedAt(Instant.now());
        return chosen;
    }

    public List<Restaurant> getRestaurants(String sessionToken,
            String username) {
        Session session = sessionService.findBySessionToken(sessionToken);

        boolean isParticipant =
                sessionParticipantRepository
                        .existsBySessionIdAndUsername(session.getSessionID(), username);

        if (!isParticipant) {
            throw new IllegalStateException("User not part of session");
        }

        return restaurantRepository.findBySessionId(session.getSessionID());
    }

    public Restaurant getChosenRestaurant(String sessionToken,
                                           String username) {
        Session session = sessionService.findBySessionToken(sessionToken);

        boolean isParticipant =
                sessionParticipantRepository
                        .existsBySessionIdAndUsername(session.getSessionID(), username);

        if (!isParticipant) {
            throw new IllegalStateException("User not part of session");
        }

        SessionRestaurant chosenRestaurant = sessionRestaurantRepository.findBySessionId(session.getSessionID()).orElseThrow(
                () -> {
                    throw new IllegalStateException("Restaurant is not selected yet.");
                }
        );

        return restaurantRepository.findByRestaurantId(chosenRestaurant.getRestaurantId()).orElseThrow(
                () -> {
                    throw new IllegalStateException("cannot find restaurant");
                }
        );
    }

    /**
     * Retrieves all restaurants submitted for a given session.
     *
     * @param session Session
     * @return List of restaurants submitted to the session
     */

    public List<Restaurant> getAllRestaurantsGivenSessionToken(Session session) {
        return restaurantRepository.findBySessionId(session.getSessionID());
    }
}
