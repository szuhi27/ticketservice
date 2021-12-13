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
    private List<Screening> testList, testListBad;
    private Movie testMovie;
    private LocalDateTime testDate;
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
        LocalDateTime date = LocalDateTime.parse("2021-03-14 16:00", formatter);
        LocalDateTime date2 = LocalDateTime.parse("2021-01-01 16:00", formatter);

        testDate = LocalDateTime.parse("2000-01-01 16:00", formatter);

        testScreening = Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(date)
                .build();

        testList = List.of(Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(date2)
                .build());

        testListBad = List.of(Screening.builder()
                .movie("testMovie")
                .room("testRoom")
                .date(date)
                .build());

        testMovie = Movie.builder()
                .title("testMovie")
                .genre("testGenre")
                .length(5)
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
    public void testCreateScreeningShouldThrowDNEExc() {

        when(movieRepository.existsByTitle(testScreening.getMovie())).thenReturn(true);
        when(roomRepository.existsByName(testScreening.getRoom())).thenReturn(true);
        when(screeningRepository.findAll()).thenReturn(testListBad);
        when(movieRepository.findByTitle(testMovie.getTitle())).thenReturn(testMovie);

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
