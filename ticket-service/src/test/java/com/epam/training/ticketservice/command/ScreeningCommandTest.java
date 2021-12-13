package com.epam.training.ticketservice.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.command.ScreeningCommand;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.model.Screening;
import ticketservice.repository.ScreeningRepository;
import ticketservice.service.AccountService;
import ticketservice.service.ScreeningService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScreeningCommandTest {

    private Screening testScreening;
    private String movie, room, date;
    private LocalDateTime localDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Mock
    private ScreeningService screeningService;
    @Mock
    private AccountService accountService;
    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningCommand screeningCommand;

    @BeforeEach
    private void initMovie() {
        movie = "testMovie";
        room = "testGenre";
        date = "2021-03-14 16:00";
        localDate = LocalDateTime.parse(date, formatter);;

        testScreening = Screening.builder()
                .movie(movie)
                .room(room)
                .date(localDate)
                .build();
    }

    @Test
    public void testCreateScreening() throws DoesNotExistsException {
        // Given
        // When
        when(screeningService.screeningCreator(movie, room, date)).thenReturn(testScreening);
        screeningCommand.createScreening(movie, room, date);

        // Then
        verify(screeningService, times(1)).createScreening(any(Screening.class));
    }

    @Test
    public void testCreateScreeningShouldNotCreate() throws DoesNotExistsException {

        when(screeningService.screeningCreator(movie, room, date)).thenReturn(testScreening);
        doThrow(DoesNotExistsException.class).when(screeningService).createScreening(any(Screening.class));
        screeningCommand.createScreening(movie, room, date);

        verify(screeningRepository, times(0)).save(any(Screening.class));
    }

    @Test
    public void testDeleteScreening() throws DoesNotExistsException {

        when(screeningService.screeningCreator(movie, room, date)).thenReturn(testScreening);
        screeningCommand.deleteScreening(movie, room, date);

        verify(screeningService, times(1)).deleteScreening(testScreening);
    }

    @Test
    public void testDeleteScreeningShouldNotDelete() throws DoesNotExistsException {

        when(screeningService.screeningCreator(movie, room, date)).thenReturn(testScreening);
        doThrow(DoesNotExistsException.class).when(screeningService).deleteScreening(testScreening);
        screeningCommand.deleteScreening(movie, room, date);

        verify(screeningRepository, times(0)).deleteByMovieAndRoomAndDate(movie, room, localDate);
    }

    @Test
    public void testListScreening() {
        String exp = testScreening + "\n" + testScreening;

        when(screeningService.listScreenings()).thenReturn(List.of(testScreening, testScreening));
        String actual = screeningCommand.listScreening();

        assertEquals(actual, exp);
    }

    @Test
    public void testListScreeningShouldReturnErrorMsg() {
        String errMsg = "There are no screenings at the moment";

        when(screeningService.listScreenings()).thenReturn(Collections.emptyList());
        String actualString = screeningCommand.listScreening();

        assertEquals(errMsg, actualString);
    }

}
