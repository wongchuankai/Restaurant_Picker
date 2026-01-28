package sg.gov.tech.restaurant.dto.request;

public class SessionRequest {

    private String userId;

    public SessionRequest() {
    }

    public SessionRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
