package com.epam.training.ticketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.model.Screening;
import ticketservice.repository.MovieRepository;
import ticketservice.repository.RoomRepository;
import ticketservice.repository.ScreeningRepository;
import ticketservice.service.ScreeningService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {

    private Screening testScreening;
    private List<Screening> testList;
    private Movie testMovie;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RoomRepository roomRepository;


    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void initScreening() {
        testScreening = Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(LocalDateTime.parse("2021-03-14 16:00", formatter))
                .build();

        testList = List.of(Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(LocalDateTime.parse("2021-01-01 16:00", formatter))
                .build());

        testMovie = Movie.builder()
                .title("testMovie")
                .genre("testGenre")
                .length(50)
                .build();
    }

    @Test
    public void testCreateScreening() throws DoesNotExistsException {

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(true);
        when(roomRepository.existsByName(testScreening.getRoom())).thenReturn(true);
        when(screeningRepository.findAll()).thenReturn(testList);
        when(movieRepository.findByTitle(testMovie.getTitle())).thenReturn(testMovie);
        screeningService.createScreening(testScreening);


        verify(screeningRepository, times(1)).save(testScreening);
    }

    @Test
    public void testCreateScreeningShouldThrowDNEExcWrongTimeBefore() {
        List<Screening> testListBad = List.of(Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(LocalDateTime.parse("2021-03-14 15:55", formatter))
                .build());

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(true);
        when(roomRepository.existsByName(testScreening.getRoom())).thenReturn(true);
        when(screeningRepository.findAll()).thenReturn(testListBad);
        when(movieRepository.findByTitle(testMovie.getTitle())).thenReturn(testMovie);

        assertThrows(DoesNotExistsException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);

    }

    @Test
    public void testCreateScreeningShouldThrowDNEExcWrongTimeAfter() {
        List<Screening> testListBad = List.of(Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(LocalDateTime.parse("2021-03-14 16:05", formatter))
                .build());

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(true);
        when(roomRepository.existsByName(testScreening.getRoom())).thenReturn(true);
        when(screeningRepository.findAll()).thenReturn(testListBad);
        when(movieRepository.findByTitle(testMovie.getTitle())).thenReturn(testMovie);

        assertThrows(DoesNotExistsException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);

    }

    @Test
    public void testCreateScreeningShouldThrowDNNEExcNoMovie() {

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);
    }

    @Test
    public void testCreateScreeningShouldThrowDNNEExcNoRoom() {

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(true);
        when(roomRepository.existsByName(testScreening.getRoom())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);
    }

    @Test
    public void testDeleteScreening() throws DoesNotExistsException{

        when(screeningRepository.existsByMovieAndRoomAndDate(testScreening.getMovie(),
                testScreening.getRoom(),
                testScreening.getDate())).thenReturn(true);
        screeningService.deleteScreening(testScreening);

        verify(screeningRepository, times(1)).deleteByMovieAndRoomAndDate(
                testScreening.getMovie(),
                testScreening.getRoom(),
                testScreening.getDate());
    }

    @Test
    public void testDeleteRoomShouldThrowDNEExc(){

        when(screeningRepository.existsByMovieAndRoomAndDate(testScreening.getMovie(),
                testScreening.getRoom(),
                testScreening.getDate())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> screeningService.deleteScreening(testScreening));
        verify(screeningRepository, times(0)).deleteByMovieAndRoomAndDate(
                testScreening.getMovie(),
                testScreening.getRoom(),
                testScreening.getDate());
    }

    @Test
    public void testListScreening() {
        List<Screening> expectList = List.of(testScreening);

        when(screeningRepository.findAll()).thenReturn(expectList);
        List<Screening> realList = screeningService.listScreenings();

        assertEquals(expectList, realList);
    }

}
