package sg.gov.tech.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.gov.tech.restaurant.model.SessionRestaurant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRestaurantRepository extends JpaRepository<SessionRestaurant, Integer> {

    Optional<SessionRestaurant> findBySessionIdAndRestaurantId(UUID sessionId, Integer restaurantId);
    Optional<SessionRestaurant> findBySessionId(UUID sessionId);

}
