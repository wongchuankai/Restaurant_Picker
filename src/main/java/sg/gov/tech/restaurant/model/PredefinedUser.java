package sg.gov.tech.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PREDEFINED_USER")
public class PredefinedUser {

    @Id
    private String userId;

    private String username;

    public PredefinedUser () {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PredefinedUser(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
