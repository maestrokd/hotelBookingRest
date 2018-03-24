package ua.com.hotelbooking.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.services.RoomServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomServiceImplTest {

    // Fields
    @MockBean
    private RoomRepository roomRepositoryMock;

    private List<Room> notEmptyRoomList;
    private Date correctDateStart, correctDateEnd, incorrectDateStart, incorrectDateEnd;

    @Autowired
    private RoomServiceImpl roomServiceImpl;


    // Methods
    @Before
    public void setup(){
        notEmptyRoomList = new ArrayList<>();
        notEmptyRoomList.add(mock(Room.class));
        when(roomRepositoryMock.findAll()).thenReturn(notEmptyRoomList);
        when(roomRepositoryMock.findRoomsByCategory("CorrectCategory")).thenReturn(notEmptyRoomList);
        when(roomRepositoryMock.findRoomsByCategory("IncorrectCategory")).thenReturn(new ArrayList<>());

        correctDateStart = AssemblerDate.getInstance().getDateByString("01-01-2001");
        correctDateEnd = AssemblerDate.getInstance().getDateByString("02-01-2001");
        incorrectDateStart = AssemblerDate.getInstance().getDateByString("02-02-2002");
        incorrectDateEnd = AssemblerDate.getInstance().getDateByString("03-02-2002");
        when(roomRepositoryMock.findFreeRoomsByStartBookingDateAndEndBookingDate(correctDateStart, correctDateEnd)).thenReturn(notEmptyRoomList);
        when(roomRepositoryMock.findFreeRoomsByStartBookingDateAndEndBookingDate(incorrectDateStart, incorrectDateEnd)).thenReturn(new ArrayList<>());
    }


    @Test
    public void getAllRooms_thenTruth() {
        assertNotEquals(0, roomServiceImpl.getAllRooms().size());
    }


    @Test
    public void getRoomsByIncorrectCategory(){
        assertEquals(0, roomServiceImpl.getAllRoomsByCategory("IncorrectCategory").size());
    }


    @Test
    public void getRoomsByCategoryA(){
        assertNotEquals(0, roomServiceImpl.getAllRoomsByCategory("CorrectCategory").size());
    }


    @Test
    public void getAvailableRoomsByStartBookingDateAndEndBookingDate_thenNotEmptyList () {
        assertNotEquals(0, roomServiceImpl.getAvailableRoomsByStartBookingDateAndEndBookingDate(correctDateStart, correctDateEnd).size());
    }


    @Test
    public void getAvailableRoomsByStartBookingDateAndEndBookingDate_thenEmptyList () {
        assertEquals(0, roomServiceImpl.getAvailableRoomsByStartBookingDateAndEndBookingDate(incorrectDateStart, incorrectDateEnd).size());
    }

}
