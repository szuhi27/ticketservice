package com.epam.training.ticketservice.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.command.RoomCommand;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Room;
import ticketservice.repository.RoomRepository;
import ticketservice.service.AccountService;
import ticketservice.service.RoomService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomCommandTest {

    private Room testRoom;
    private String name;
    private int columns, rows;

    @Mock
    private RoomService roomService;
    @Mock
    private AccountService accountService;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomCommand roomCommand;

    @BeforeEach
    private void initRoom() {
        name = "testRoom";
        rows = 5;
        columns = 5;

        testRoom = Room.builder()
                .name(name)
                .rows(rows)
                .columns(columns)
                .build();
    }

    @Test
    public void testCreateRoom() throws AlreadyExistsException {
        // Given
        // When
        when(roomService.roomCreator(name, rows, columns)).thenReturn(testRoom);
        roomCommand.createRoom(name, rows, columns);

        // Then
        verify(roomService, times(1)).createRoom(any(Room.class));
    }

    @Test
    public void testCreateRoomShouldNotCreate() throws AlreadyExistsException {

        when(roomService.roomCreator(name, rows, columns)).thenReturn(testRoom);
        doThrow(AlreadyExistsException.class).when(roomService).createRoom(any(Room.class));
        roomCommand.createRoom(name, rows, columns);

        verify(roomRepository, times(0)).save(any(Room.class));
    }

    @Test
    public void testUpdateRoom() throws DoesNotExistsException {

        when(roomService.roomCreator(name, rows, columns)).thenReturn(testRoom);
        roomCommand.updateRoom(name, rows, columns);

        verify(roomService, times(1)).updateRoom(any(Room.class));
    }

    @Test
    public void testUpdateRoomShouldNotUpdate() throws DoesNotExistsException {

        when(roomService.roomCreator(name, rows, columns)).thenReturn(testRoom);
        doThrow(DoesNotExistsException.class).when(roomService).updateRoom(any(Room.class));
        roomCommand.updateRoom(name, rows, columns);

        verify(roomRepository, times(0)).update(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testDeleteRoom() throws DoesNotExistsException {

        roomCommand.deleteRoom(name);

        verify(roomService, times(1)).deleteRoom(name);
    }

    @Test
    public void testDeleteRoomShouldNotDelete() throws DoesNotExistsException {

        doThrow(DoesNotExistsException.class).when(roomService).deleteRoom(name);
        roomCommand.deleteRoom(name);

        verify(roomRepository, times(0)).deleteByName(name);
    }

    @Test
    public void testListRoom() {
        String exp = testRoom + "\n" + testRoom;

        when(roomService.listRooms()).thenReturn(List.of(testRoom, testRoom));
        String actual = roomCommand.listRooms();

        assertEquals(actual, exp);
    }

    @Test
    public void testListRoomShouldReturnErrorMsg() {
        String errMsg = "There are no rooms at the moment";

        when(roomService.listRooms()).thenReturn(Collections.emptyList());
        String actualString = roomCommand.listRooms();

        assertEquals(errMsg, actualString);
    }

}
