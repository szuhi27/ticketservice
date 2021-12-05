package ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.repository.MovieRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie movieCreator(String title, String genre, int length){
        return Movie.builder()
                .title(title)
                .genre(genre)
                .length(length)
                .build();
    }

    public void createMovie(Movie movie) throws AlreadyExistsException {
        if(movieRepository.existsByTitle(movie.getTitle())){
            throw new AlreadyExistsException(movie.getTitle()+" already exists!");
        } else{
            movieRepository.save(movie);
        }
    }

    public void updateMovie(Movie movie) throws DoesNotExistsException {
        if(!movieRepository.existsByTitle(movie.getTitle())){
            throw new DoesNotExistsException(movie.getTitle()+" does not exists!");
        }else{
            movieRepository.update(movie.getTitle(),movie.getGenre(),movie.getLength());
        }
    }

    public void deleteMovie(String title) throws DoesNotExistsException{
        if(!movieRepository.existsByTitle(title)){
            throw new DoesNotExistsException(title+" does not exists!");
        }else{
            movieRepository.deleteByTitle(title);
        }
    }

    public List<Movie> listMovies(){
        return movieRepository.findAll();
    }

}
