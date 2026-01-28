package sg.gov.tech.restaurant.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.gov.tech.restaurant.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

        Optional<Session> findBySessionToken(String sessionToken);

}
