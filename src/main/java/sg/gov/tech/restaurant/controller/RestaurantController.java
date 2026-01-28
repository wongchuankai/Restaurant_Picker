//package sg.gov.tech.restaurant.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sg.gov.tech.restaurant.dto.request.RestaurantRequest;
//import sg.gov.tech.restaurant.dto.request.UsernameRequest;
//import sg.gov.tech.restaurant.dto.response.RestaurantResponse;
//import sg.gov.tech.restaurant.mapper.RestaurantMapper;
//import sg.gov.tech.restaurant.model.Restaurant;
//import sg.gov.tech.restaurant.service.RestaurantService;
//import sg.gov.tech.restaurant.service.SessionService;
//
//import java.util.List;
//
///**
// * Controller to handle Restaurant-related operations.
// */
//@RestController
//@RequestMapping("/restaurants")
//public class RestaurantController {
//
//    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
//
//    private final RestaurantService restaurantService;
//
//    private final SessionService sessionService;
//
//    public RestaurantController(RestaurantService restaurantService, SessionService sessionService) {
//        this.restaurantService = restaurantService;
//        this.sessionService = sessionService;
//    }
//
//    /**
//     * List all restaurants submitted to a session using session token.
//     *
//     * @param sessionToken session token
//     * @return List of RestaurantEntry objects for the session
//     */
//    @GetMapping("/{sessionToken}/result")
//    public ResponseEntity<RestaurantResponse> getResult(
//            @PathVariable String sessionToken, @RequestBody UsernameRequest request) {
//        logger.info("RestaurantController: listRestaurants: {}", sessionToken);
//        Restaurant resultRestaurant = restaurantService.getChosenRestaurant(sessionToken, request.getUsername());
//        RestaurantResponse response = RestaurantMapper.toResponse(resultRestaurant);
//
//        return ResponseEntity.ok(response);
//    }
//}
