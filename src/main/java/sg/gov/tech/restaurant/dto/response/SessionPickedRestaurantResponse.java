package sg.gov.tech.restaurant.dto.response;

import sg.gov.tech.restaurant.mapper.SessionMapper;
import sg.gov.tech.restaurant.model.Restaurant;
import sg.gov.tech.restaurant.model.Session;

public class SessionPickedRestaurantResponse {

//    private SessionResponse session;

    private RestaurantResponse pickedRestaurant;

    public SessionPickedRestaurantResponse(RestaurantResponse pickedRestaurant) {
        this.pickedRestaurant = pickedRestaurant;
    }


    public RestaurantResponse getPickedRestaurant() {
        return pickedRestaurant;
    }

    public void setPickedRestaurant(RestaurantResponse pickedRestaurant) {
        this.pickedRestaurant = pickedRestaurant;
    }
}
