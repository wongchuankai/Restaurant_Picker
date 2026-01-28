package sg.gov.tech.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.gov.tech.restaurant.model.PredefinedUser;

import java.util.Optional;

@Repository
public interface PredefinedUserRepository extends JpaRepository<PredefinedUser, Long> {

//    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);

    Optional<PredefinedUser> findByUserId(String userId);

}
