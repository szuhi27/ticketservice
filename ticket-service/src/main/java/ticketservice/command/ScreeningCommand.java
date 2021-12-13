package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.exception.NotAdminException;
import ticketservice.model.Screening;
import ticketservice.service.AccountService;
import ticketservice.service.ScreeningService;


@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {

    private final ScreeningService screeningService;
    private final AccountService accountService;

    @ShellMethod(value = "create a screening", key = "create screening")
    public String createScreening(String movie, String room, String date) {
        try {
            accountService.isAdmin();
            try {
                Screening screening = screeningService.screeningCreator(movie, room, date);
                screeningService.createScreening(screening);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException b) {
            return b.getMessage();
        }
        return "Screening created";
    }

    @ShellMethod(value = "delete a screening", key = "delete screening")
    public String deleteScreening(String movie, String room, String date) {
        try {
            accountService.isAdmin();
            try {
                Screening screening = screeningService.screeningCreator(movie, room, date);
                screeningService.deleteScreening(screening);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException e) {
            return e.getMessage();
        }
        return "Screening deleted";
    }

    @ShellMethod(value = "delete a screening", key = "list screenings")
    public String listScreening() {
        StringBuilder sb = new StringBuilder();

        if (!screeningService.listScreenings().isEmpty()) {
            screeningService.listScreenings().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no screenings at the moment";
        }
    }

}
