package sg.gov.tech.restaurant.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "session_restaurant",
        uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "restaurant_id"})
)
public class SessionRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    public SessionRestaurant() {
    }

    public SessionRestaurant(UUID sessionId, Integer restaurantID) {
        this.sessionId = sessionId;
        this.restaurantId = restaurantID;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
