package sg.gov.tech.restaurant.dto.request;

public class RestaurantRequest {

    String restaurantName;

    double latitude;

    double longitude;

    String submittedBy;

    public RestaurantRequest() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Override
    public String toString() {
        return "AddRestaurantRequest{" +
                "restaurantName='" + restaurantName + '\'' +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }
}
