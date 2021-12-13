package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.exception.NotAdminException;
import ticketservice.model.Movie;
import ticketservice.model.Screening;
import ticketservice.service.AccountService;
import ticketservice.service.MovieService;
import ticketservice.service.ScreeningService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {

    private final ScreeningService screeningService;
    private final AccountService accountService;
    private final MovieService movieService;

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
            List<Movie> movieList = movieService.listMovies();
            Movie[] allMovie = new Movie[movieList.size()];
            movieList.toArray(allMovie);

            List<Screening> screeningList = screeningService.listScreenings();
            Screening[] allScr = new Screening[screeningList.size()];
            screeningList.toArray(allScr);

            for(int i = 0; i < allScr.length; i++){
                String line = "";
                Movie mov = currentMovie(allMovie, allScr[i].getMovie());

                line =  mov.getTitle() + " (" + mov.getGenre() + ", " + mov.getLength() + " minutes), "
                        + "screened in room " + allScr[i].getRoom() + ", at "
                        + allScr[i].getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                sb.append(line).append("\n");
            }
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no screenings";
        }
    }

    private Movie currentMovie(Movie[] movieArr, String  currentMovie){
        Movie movie = new Movie();
        for(int i = 0; i < movieArr.length; i++){
            if(Objects.equals(movieArr[i].getTitle(), currentMovie)){
                movie = movieArr[i];
                break;
            }
        }
        return movie;
    }

}
