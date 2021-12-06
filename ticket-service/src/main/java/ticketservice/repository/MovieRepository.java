package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticketservice.model.Movie;

import javax.transaction.Transactional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.genre = :genre, m.length = :length WHERE m.title = :title")
    void update(@Param("title") String title, @Param("genre") String genre, @Param("length") Integer length);

    @Transactional
    Long deleteByTitle(String title);

    boolean existsByTitle(String title);

    Movie findByTitle(String title);



}