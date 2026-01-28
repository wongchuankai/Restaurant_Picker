package sg.gov.tech.restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.gov.tech.restaurant.dto.request.RestaurantRequest;
import sg.gov.tech.restaurant.dto.request.SessionRequest;
import sg.gov.tech.restaurant.dto.request.UsernameRequest;
import sg.gov.tech.restaurant.dto.response.RestaurantResponse;
import sg.gov.tech.restaurant.dto.response.SessionPickedRestaurantResponse;
import sg.gov.tech.restaurant.dto.response.SessionResponse;
import sg.gov.tech.restaurant.mapper.RestaurantMapper;
import sg.gov.tech.restaurant.mapper.SessionMapper;
import sg.gov.tech.restaurant.model.Restaurant;
import sg.gov.tech.restaurant.model.Session;
import sg.gov.tech.restaurant.service.RestaurantService;
import sg.gov.tech.restaurant.service.SessionService;

import java.util.List;

/**
 * Controller to handle Session-related operations.
 */
@RestController
@RequestMapping("/sessions")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;

    private final RestaurantService restaurantService;

    public SessionController(SessionService sessionService, RestaurantService restaurantService) {
        this.sessionService = sessionService;
        this.restaurantService = restaurantService;
    }

    /**
     * Create session - used for grouping user to select restaurant
     * Only predefined users are allowed to create a session.
     *
     * @param request JSON body containing the username
     * @return created session response
     */
    @PostMapping
    public ResponseEntity<SessionResponse > createSession(
            @RequestBody SessionRequest request) {

        logger.info("SessionController: create session request: {}",request);

        Session createdSession = sessionService.createSession(request.getUserId());

        // Map to SessionResponse (winner will be null initially)
        SessionResponse response = SessionMapper.toResponse(createdSession);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Find session using session token
     * @param sessionToken session token
     * @return SessionResponse object including hostUser, status, and chosen restaurant
     */
    @GetMapping("/{sessionToken}")
    public ResponseEntity<SessionResponse> findSessionBySessionToken(
            @PathVariable String sessionToken) {

        logger.info("SessionController: findSessionBySessionToken: sessionToken: {}", sessionToken);

        // Fetch the session entity
        Session found = sessionService.findBySessionToken(sessionToken);

        SessionResponse response = SessionMapper.toResponse(found);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{sessionToken}/join")
    public ResponseEntity<SessionResponse > joinSession(
            @PathVariable String sessionToken, @RequestBody UsernameRequest request) {

        logger.info("SessionController: join session sessionToken : {}, request: {}", sessionToken, request);

        sessionService.joinSession(sessionToken, request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    /**
     * Randomly select a restaurant and session will be locked after.
     * @param sessionToken
     * @param request
     * @return
     */
    @PostMapping("/{sessionToken}/end")
    public ResponseEntity<SessionPickedRestaurantResponse> randomPick(
            @PathVariable String sessionToken,
            @RequestBody SessionRequest request) {
        logger.info("SessionController: randomPick: sessionToken: {}, requestedBy: {}", sessionToken, request);
        String requestedBy = request.getUserId();
        Restaurant chosen = restaurantService.randomSelectRestaurant(sessionToken, requestedBy);
        logger.info("RandomPick: session={}, winner={}", sessionToken, chosen.getRestaurantName());

        // Map to SessionResponse including winner
        SessionPickedRestaurantResponse response = new SessionPickedRestaurantResponse(RestaurantMapper.toResponse(chosen));

        return ResponseEntity.ok(response);
    }

    /**
     * Submit a restaurant to a specific session.
     * Submissions are rejected if a random choice has already been made.
     *
     * @param sessionToken session token
     * @param request   JSON body containing username and restaurant name
     */
    @PostMapping("/{sessionToken}/restaurants")
    public ResponseEntity<RestaurantResponse> submitRestaurant(
            @PathVariable String sessionToken,
            @RequestBody RestaurantRequest request) {
        logger.info("RestaurantController: submitRestaurant, sessionToken: {}, request: {}", sessionToken, request);
        Restaurant addedRestaurant = restaurantService.addRestaurant(sessionToken, request);
        RestaurantResponse response =
                RestaurantMapper.toResponse(addedRestaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * List all restaurants submitted to a session using session token.
     *
     * @param sessionToken session token
     * @return List of RestaurantEntry objects for the session
     */
    @GetMapping("/{sessionToken}/restaurants")
    public ResponseEntity<List<RestaurantResponse>> listRestaurants(
            @PathVariable String sessionToken, @RequestBody UsernameRequest request) {
        logger.info("RestaurantController: listRestaurants: {}", sessionToken);
        List<Restaurant> restaurants = restaurantService.getRestaurants(sessionToken, request.getUsername());
        List<RestaurantResponse> response = RestaurantMapper.toResponse(restaurants);

        return ResponseEntity.ok(response);
    }

    /**
     * List all restaurants submitted to a session using session token.
     *
     * @param sessionToken session token
     * @return List of RestaurantEntry objects for the session
     */
    @GetMapping("/{sessionToken}/result")
    public ResponseEntity<SessionPickedRestaurantResponse> getResult(
            @PathVariable String sessionToken, @RequestBody UsernameRequest request) {
        logger.info("RestaurantController: listRestaurants: {}", sessionToken);
        Restaurant resultRestaurant = restaurantService.getChosenRestaurant(sessionToken, request.getUsername());
        SessionPickedRestaurantResponse response = new SessionPickedRestaurantResponse(RestaurantMapper.toResponse(resultRestaurant));

        return ResponseEntity.ok(response);
    }

}
