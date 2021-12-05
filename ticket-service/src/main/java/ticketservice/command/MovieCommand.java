package ticketservice.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.model.Movie;
import ticketservice.service.MovieService;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {

    private final MovieService movieService;

    @ShellMethod(value="create a movie",key = "create movie")
    public String createMovie(String title, String genre, int length){
        try{
            Movie movie = movieService.movieCreator(title,genre,length);
            movieService.createMovie(movie);
        }catch (AlreadyExistsException e){
            return e.getMessage();
        }
        return new String(title +" movie created");
    }
}
