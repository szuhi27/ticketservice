package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketservice.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Long deleteByTitle(String title);

    boolean existsByTitle(String title);

}