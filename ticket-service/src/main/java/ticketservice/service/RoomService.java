package ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.model.Room;
import ticketservice.repository.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room roomCreator(String name, int rows, int columns) {
        return Room.builder()
                .name(name)
                .rows(rows)
                .columns(columns)
                .build();
    }

    public void createRoom(Room room) throws AlreadyExistsException {
        if (roomRepository.existsByName(room.getName())) {
            throw new AlreadyExistsException(room.getName() + " already exists!");
        } else {
            roomRepository.save(room);
        }
    }

    public void updateRoom(Room room) throws DoesNotExistsException {
        if (!roomRepository.existsByName(room.getName())) {
            throw new DoesNotExistsException(room.getName() + " does not exists!");
        } else {
            roomRepository.update(room.getName(),room.getRows(),room.getColumns());
        }
    }

    public void deleteRoom(String name) throws DoesNotExistsException {
        if (!roomRepository.existsByName(name)) {
            throw new DoesNotExistsException(name + " does not exists!");
        } else {
            roomRepository.deleteByName(name);
        }
    }

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

}
