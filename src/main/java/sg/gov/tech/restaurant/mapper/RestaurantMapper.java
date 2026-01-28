package sg.gov.tech.restaurant.mapper;

import sg.gov.tech.restaurant.dto.response.RestaurantResponse;
import sg.gov.tech.restaurant.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {

    private RestaurantMapper() {
    }

    /**
     * Maps Restaurant entity to RestaurantResponse DTO.
     */
    public static RestaurantResponse toResponse(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(restaurant.getRestaurantId());
        response.setRestaurantName(restaurant.getRestaurantName());
        response.setLatitude(restaurant.getLatitude());
        response.setLongitude(restaurant.getLongitude());
        response.setSubmittedBy(restaurant.getSubmittedBy());
        response.setSubmittedAt(restaurant.getSubmittedAt());

        return response;
    }

    public static List<RestaurantResponse> toResponse(List<Restaurant> restaurants) {
        List<RestaurantResponse> response = restaurants.stream()
                .map(r -> new RestaurantResponse(
                        r.getRestaurantId(),
                        r.getRestaurantName(),
                        r.getLatitude(),
                        r.getLongitude(),
                        r.getSubmittedBy(),
                        r.getSubmittedAt()
                ))
                .collect(Collectors.toList());
        return response;
    }
}
