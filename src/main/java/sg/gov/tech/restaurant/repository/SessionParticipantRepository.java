package sg.gov.tech.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.gov.tech.restaurant.model.SessionParticipant;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Integer> {

    Optional<SessionParticipant> findBySessionIdAndUsername(UUID sessionId, String username);

    boolean existsBySessionIdAndUsername(UUID sessionId, String username);

}
