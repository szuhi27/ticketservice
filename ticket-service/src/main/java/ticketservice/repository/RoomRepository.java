package ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticketservice.model.Room;

import javax.transaction.Transactional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.rows = :rows, r.columns = :columns WHERE r.name = :name ")
    void update(@Param("name") String name, @Param("rows") int rows, @Param("columns") int columns);

    @Transactional
    Long deleteByName(String name);

    boolean existsByName(String name);

}