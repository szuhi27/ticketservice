package com.epam.training.ticketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketservice.exception.AlreadyExistsException;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Room;
import ticketservice.repository.RoomRepository;
import ticketservice.service.RoomService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private Room testRoom;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void initRoom() {
        testRoom = Room.builder()
                .name("testRoom")
                .rows(1)
                .columns(1)
                .build();
    }

    @Test
    public void testCreateRoom() throws AlreadyExistsException {
        //Given

        //When
        roomService.createRoom(testRoom);

        //Then
        verify(roomRepository, times(1)).save(testRoom);
    }

    @Test
    public void testCreateRoomShouldThrowAlExExc(){

        when(roomRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> roomService.createRoom(testRoom));
        verify(roomRepository, times(0)).save(testRoom);
    }

    @Test
    public void testUpdateRoom() throws DoesNotExistsException {

        when(roomRepository.existsByName(testRoom.getName())).thenReturn(true);
        roomService.updateRoom(testRoom);

        verify(roomRepository, times(1)).update(
                testRoom.getName(),
                testRoom.getRows(),
                testRoom.getColumns());
    }

    @Test
    public void testUpdateRoomShouldThrowDNEExc(){

        when(roomRepository.existsByName(testRoom.getName())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> roomService.updateRoom(testRoom));
        verify(roomRepository, times(0)).update(
                testRoom.getName(),
                testRoom.getRows(),
                testRoom.getColumns());
    }

    @Test
    public void testDeleteRoom() throws DoesNotExistsException{

        when(roomRepository.existsByName(testRoom.getName())).thenReturn(true);
        roomService.deleteRoom(testRoom.getName());

        verify(roomRepository, times(1)).deleteByName(testRoom.getName());
    }

    @Test
    public void testDeleteRoomShouldThrowDNEExc(){

        when(roomRepository.existsByName(testRoom.getName())).thenReturn(false);

        assertThrows(DoesNotExistsException.class, () -> roomService.deleteRoom(testRoom.getName()));
        verify(roomRepository, times(0)).deleteByName(testRoom.getName());
    }

    @Test
    public void testListRoom() {
        List<Room> expectList = List.of(testRoom);

        when(roomRepository.findAll()).thenReturn(expectList);
        List<Room> realList = roomService.listRooms();

        assertEquals(expectList, realList);
    }

}
