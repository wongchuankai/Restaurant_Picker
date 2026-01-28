package sg.gov.tech.restaurant.exception;

import java.time.Instant;

public class ErrorResponse {

    private String message;
    private Instant timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
