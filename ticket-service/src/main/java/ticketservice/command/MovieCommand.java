package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.exception.NotAdminException;
import ticketservice.model.Movie;
import ticketservice.service.AccountService;
import ticketservice.service.MovieService;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {

    private final MovieService movieService;
    private final AccountService accountService;

    @ShellMethod(value = "create a movie", key = "create movie")
    public String createMovie(String title, String genre, int length) {
        try {
            accountService.isAdmin();
            try {
                Movie movie = movieService.movieCreator(title, genre, length);
                movieService.createMovie(movie);
            } catch (AlreadyExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException b) {
            return b.getMessage();
        }
        return title + " movie created";
    }

    @ShellMethod(value = "update a movie", key = "update movie")
    public String updateMovie(String title, String genre, int length) {
        try {
            accountService.isAdmin();
            try {
                Movie movie = movieService.movieCreator(title, genre, length);
                movieService.updateMovie(movie);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }

        } catch (NotAdminException e) {
            return e.getMessage();
        }
        return title + " movie updated";
    }

    @ShellMethod(value = "delete a movie", key = "delete movie")
    public String deleteMovie(String title) {
        try {
            accountService.isAdmin();
            try {
                movieService.deleteMovie(title);
            } catch (DoesNotExistsException e) {
                return e.getMessage();
            }
        } catch (NotAdminException e) {
            return e.getMessage();
        }
        return title + " movie deleted";
    }

    @ShellMethod(value = "delete a movie", key = "list movies")
    public String listMovie() {
        StringBuilder sb = new StringBuilder();

        if (!movieService.listMovies().isEmpty()) {
            movieService.listMovies().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no movies at the moment";
        }
    }
}
