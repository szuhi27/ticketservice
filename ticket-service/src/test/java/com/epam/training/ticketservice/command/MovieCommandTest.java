package com.epam.training.ticketservice.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.command.MovieCommand;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.repository.MovieRepository;
import ticketservice.service.AccountService;
import ticketservice.service.MovieService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieCommandTest {

    private Movie testMovie;
    private String title, genre;
    private int length;

    @Mock
    private MovieService movieService;
    @Mock
    private AccountService accountService;
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieCommand movieCommand;

    @BeforeEach
    private void initMovie() {
        title = "testMovie";
        genre = "testGenre";
        length = 100;

        testMovie = Movie.builder()
                .title(title)
                .genre(genre)
                .length(length)
                .build();
    }

    @Test
    public void testCreateMovie() throws AlreadyExistsException {
        // Given
        // When
        when(movieService.movieCreator(title, genre, length)).thenReturn(testMovie);
        movieCommand.createMovie(title, genre, length);

        // Then
        verify(movieService, times(1)).createMovie(any(Movie.class));
    }

    @Test
    public void testCreateMovieShouldNotCreate() throws AlreadyExistsException {

        when(movieService.movieCreator(title, genre, length)).thenReturn(testMovie);
        doThrow(AlreadyExistsException.class).when(movieService).createMovie(any(Movie.class));
        movieCommand.createMovie(title, genre, length);

        verify(movieRepository, times(0)).save(any(Movie.class));
    }

    @Test
    public void testUpdateMovie() throws DoesNotExistsException {

        when(movieService.movieCreator(title, genre, length)).thenReturn(testMovie);
        movieCommand.updateMovie(title, genre, length);

        verify(movieService, times(1)).updateMovie(any(Movie.class));
    }

    @Test
    public void testUpdateMovieShouldNotUpdate() throws DoesNotExistsException {

        when(movieService.movieCreator(title, genre, length)).thenReturn(testMovie);
        doThrow(DoesNotExistsException.class).when(movieService).updateMovie(any(Movie.class));
        movieCommand.updateMovie(title, genre, length);

        verify(movieRepository, times(0)).update(anyString(), anyString(), anyInt());
    }

    @Test
    public void testDeleteMovie() throws DoesNotExistsException {

        movieCommand.deleteMovie(title);

        verify(movieService, times(1)).deleteMovie(title);
    }

    @Test
    public void testDeleteMovieShouldNotDelete() throws DoesNotExistsException {

        doThrow(DoesNotExistsException.class).when(movieService).deleteMovie(title);
        movieCommand.deleteMovie(title);

        verify(movieRepository, times(0)).deleteByTitle(title);
    }

    @Test
    public void testListMovie() {
        String exp = testMovie + "\n" + testMovie;

        when(movieService.listMovies()).thenReturn(List.of(testMovie, testMovie));
        String actual = movieCommand.listMovie();

        assertEquals(actual, exp);
    }

    @Test
    public void testListMovieShouldReturnErrorMsg() {
        String errMsg = "There are no movies at the moment";

        when(movieService.listMovies()).thenReturn(Collections.emptyList());
        String actualString = movieCommand.listMovie();

        assertEquals(errMsg, actualString);
    }

}
