package com.epam.training.ticketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.repository.MovieRepository;
import ticketservice.service.MovieService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    private Movie testMovie;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @BeforeEach
    void initMovie(){
        testMovie = Movie.builder()
                .title("testTitle")
                .genre("testGenre")
                .length(5)
                .build();
    }

    @Test
    public void testCreateMovie() throws AlreadyExistsException{
        //Given

        //When
        movieService.createMovie(testMovie);

        //Then
        verify(movieRepository, times(1)).save(testMovie);
    }

    @Test
    public void testCreateMovieShouldThrowAlExExc(){

        when(movieRepository.existsByTitle(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> movieService.createMovie(testMovie));
        verify(movieRepository, times(0)).save(testMovie);
    }

    @Test
    public void testUpdateMovie() throws DoesNotExistsException{

        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(true);
        movieService.updateMovie(testMovie);

        verify(movieRepository, times(1)).update(
                testMovie.getTitle(),
                testMovie.getGenre(),
                testMovie.getLength());
    }

    @Test
    public void testUpdateMovieShouldThrowDNEExc(){

        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> movieService.updateMovie(testMovie));
        verify(movieRepository, times(0)).update(
                testMovie.getTitle(),
                testMovie.getGenre(),
                testMovie.getLength());
    }

    @Test
    public void testDeleteMovie() throws DoesNotExistsException{

        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(true);
        movieService.deleteMovie(testMovie.getTitle());

        verify(movieRepository, times(1)).deleteByTitle(testMovie.getTitle());
    }

    @Test
    public void testDeleteMovieShouldThrowDNEExc(){

        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> movieService.deleteMovie(testMovie.getTitle()));
        verify(movieRepository, times(0)).deleteByTitle(testMovie.getTitle());
    }

    @Test
    public void testListMovie() throws DoesNotExistsException{
        List<Movie> expectList = List.of(testMovie);

        when(movieRepository.findAll()).thenReturn(expectList);
        List<Movie> realList = movieService.listMovies();

        assertEquals(expectList, realList);
    }

}
