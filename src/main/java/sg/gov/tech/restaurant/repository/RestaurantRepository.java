package sg.gov.tech.restaurant.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.gov.tech.restaurant.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findBySessionId(UUID sessionId);
//
//	boolean existsBySessionId(UUID sessionId);
    Optional<Restaurant> findByRestaurantId(Integer restaurantId);

}

