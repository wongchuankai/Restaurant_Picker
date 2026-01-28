package sg.gov.tech.restaurant.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="restaurant")
public class Restaurant {
	
	@Id
    @GeneratedValue
    private Integer restaurantId;

	private UUID sessionId;

    private String restaurantName;

    private double latitude;

    private double longitude;

	private String submittedBy;
	
	private Instant submittedAt;

	public Restaurant() {
		super();
	}

	/**
	 * @return the restaurantID
	 */
	public Integer getRestaurantId() {
		return restaurantId;
	}

	/**
	 * @param restaurantId the restaurantID to set
	 */
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}

	/**
	 * @param restaurantName the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	/**
	 * @return the submittedBy
	 */
	public String getSubmittedBy() {
		return submittedBy;
	}

	/**
	 * @param submittedBy the submittedBy to set
	 */
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	/**
	 * @return the submittedAt
	 */
	public Instant getSubmittedAt() {
		return submittedAt;
	}

	/**
	 * @param submittedAt the submittedAt to set
	 */
	public void setSubmittedAt(Instant submittedAt) {
		this.submittedAt = submittedAt;
	}

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
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
