package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticketservice.model.Screening;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Transactional
    long deleteByMovieAndRoomAndDate(String movie, String room, LocalDateTime date);

    boolean existsByMovieAndRoomAndDate(String movie, String room, LocalDateTime date);

}