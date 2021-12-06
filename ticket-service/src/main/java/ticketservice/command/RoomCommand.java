package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.exception.NotAdminException;
import ticketservice.model.Movie;
import ticketservice.model.Room;
import ticketservice.service.AccountService;
import ticketservice.service.RoomService;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand {

    private final RoomService roomService;
    private final AccountService accountService;

    @ShellMethod(value = "create a room", key = "create room")
    public String createMovie(String name, int rows, int columns) {
        try {
            accountService.isAdmin();
            try {
                Room room = roomService.roomCreator(name, rows, columns);
                roomService.createRoom(room);
            } catch (AlreadyExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException b) {
            return b.getMessage();
        }
        return new String(name + " room created");
    }

    @ShellMethod(value = "update a room", key = "update room")
    public String updateRoom(String name, int rows, int columns) {
        try {
            accountService.isAdmin();
            try {
                Room room = roomService.roomCreator(name, rows, columns);
                roomService.updateRoom(room);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }

        } catch (NotAdminException e) {
            return e.getMessage();
        }
        return new String(name + " room updated");
    }

    @ShellMethod(value = "delete a room", key = "delete room")
    public String deleteRoom(String name) {
        try {
            accountService.isAdmin();
            try {
                roomService.deleteRoom(name);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException e) {
            return e.getMessage();
        }
        return new String(name + " room deleted");
    }

    @ShellMethod(value = "delete a room", key = "list rooms")
    public String listRooms() {
        StringBuilder sb = new StringBuilder();

        if (!roomService.listRooms().isEmpty()) {
            roomService.listRooms().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no rooms at the moment";
        }
    }

}
