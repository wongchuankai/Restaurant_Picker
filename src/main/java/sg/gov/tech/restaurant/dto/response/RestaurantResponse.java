package sg.gov.tech.restaurant.dto.response;

import java.time.Instant;

public class RestaurantResponse {

        private Integer restaurantId;

        private String restaurantName;

        private double latitude;

        private double longitude;

        private String submittedBy;

        private Instant submittedAt;

    public RestaurantResponse() {
    }

    public RestaurantResponse(Integer restaurantId, String restaurantName, double latitude, double longitude, String submittedBy, Instant submittedAt) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.submittedBy = submittedBy;
        this.submittedAt = submittedAt;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
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
}
