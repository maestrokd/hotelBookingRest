package ua.com.hotelbooking.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.entities.AdditionalOption;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.repositories.AdditionalOptionRepository;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.repositories.UserRepository;
import ua.com.hotelbooking.model.services.BookingServiceImpl;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceImplTest {

    // Fields
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private AdditionalOptionRepository additionalOptionRepositoryMock;
    @Mock
    private BookingRepository bookingRepositoryMock;
    @Mock
    private Booking bookingMock;

    private Date startBookingDate, endBookingDate;
    private User user1ForCreateBooking;
    private BookingServiceImpl bookingServiceImpl;
    private BookingDTO bookingDtoWithCorrectValuesForMakeBooking, bookingDtoWithIncorrectValuesForMakeBooking, bookingDtoWithCorrectValuesForCreateBooking;
    private Room roomNumber2ForCreateBooking, roomNumber1ForMakeBooking;
    private Booking bookingForMakeBooking, bookingForCreateBooking;
    private AdditionalOption additionalOption1, additionalOption2;

    {
        startBookingDate = AssemblerDate.getInstance().getDateByString("13-03-2018");
        endBookingDate  = AssemblerDate.getInstance().getDateByString("14-03-2018");

        user1ForCreateBooking = new User("user1", "pass1", "Name1");

        bookingDtoWithCorrectValuesForMakeBooking = new BookingDTO("user1", "1", "13-03-2018", "14-03-2018", new String[]{"breakfast", "cleaning"});
        bookingDtoWithIncorrectValuesForMakeBooking = new BookingDTO("IncorrectUserLogin", "0", "13-03-2018", "14-03-2018", new String[]{"anyString"});

        bookingDtoWithCorrectValuesForCreateBooking = new BookingDTO("user1", "2", "13-03-2018", "14-03-2018", new String[]{"breakfast", "cleaning"});

        roomNumber1ForMakeBooking = new Room(1, "CorrectCategory", 500);
        roomNumber2ForCreateBooking = new Room(2, "CorrectCategory", 500);

        bookingForMakeBooking = new Booking(user1ForCreateBooking, roomNumber1ForMakeBooking, startBookingDate, endBookingDate);
        bookingForCreateBooking = new Booking(user1ForCreateBooking, roomNumber2ForCreateBooking, startBookingDate, endBookingDate);

        additionalOption1 = new AdditionalOption("breakfast", 50);
        additionalOption2 = new AdditionalOption("cleaning", 100);
    }


    // Methods
    @Before
    public void setupRepositories(){
        when(userRepositoryMock.findByLogin("user1")).thenReturn(user1ForCreateBooking);
        when(userRepositoryMock.findByLogin("IncorrectUserLogin")).thenReturn(null);

//        when(roomRepositoryMock.findByNumber(anyInt())).thenReturn(null);
        when(roomRepositoryMock.findByNumber(0)).thenReturn(null);
        when(roomRepositoryMock.findByNumber(1)).thenReturn(roomNumber1ForMakeBooking);
        when(roomRepositoryMock.findByNumber(2)).thenReturn(roomNumber2ForCreateBooking);

        when(additionalOptionRepositoryMock.findByTitle(anyString())).thenReturn(null);
        when(additionalOptionRepositoryMock.findByTitle("breakfast")).thenReturn(additionalOption1);
        when(additionalOptionRepositoryMock.findByTitle("cleaning")).thenReturn(additionalOption2);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(bookingMock);

        when(bookingRepositoryMock.findBookingsByRoomWhereStartBookingDateOrEndBookingDateBetweenDates(
                roomNumber1ForMakeBooking, startBookingDate, endBookingDate)).thenReturn(bookingList);
        when(bookingRepositoryMock.findBookingsByRoomWhereStartBookingDateOrEndBookingDateBetweenDates(
                roomNumber2ForCreateBooking, startBookingDate, endBookingDate)).thenReturn(new ArrayList<>());

        when(bookingRepositoryMock.save(bookingForMakeBooking)).thenReturn(bookingForMakeBooking);
        when(bookingRepositoryMock.save(bookingForCreateBooking)).thenReturn(bookingForCreateBooking);

        bookingServiceImpl = new BookingServiceImpl();
        bookingServiceImpl.setUserRepository(userRepositoryMock);
        bookingServiceImpl.setRoomRepository(roomRepositoryMock);
        bookingServiceImpl.setAdditionalOptionRepository(additionalOptionRepositoryMock);
        bookingServiceImpl.setBookingRepository(bookingRepositoryMock);
    }


    @Test
    public void makeBookingByCorrectParameter_thenNotNull(){
        assertNotNull(bookingServiceImpl.makeBooking(bookingDtoWithCorrectValuesForMakeBooking));
        assertEquals(bookingForMakeBooking, bookingServiceImpl.makeBooking(bookingDtoWithCorrectValuesForMakeBooking));
    }


    @Test
    public void makeBookingByCorrectParameter_andEqualReturnedBooking_thenNotNullAndEqual(){
        assertNotNull(bookingServiceImpl.makeBooking(bookingDtoWithCorrectValuesForCreateBooking));
        assertEquals(bookingForCreateBooking, bookingServiceImpl.makeBooking(bookingDtoWithCorrectValuesForCreateBooking));
    }


    @Test
    public void makeBookingByIncorrectParameter_thenNull(){
        assertNull(bookingServiceImpl.makeBooking(bookingDtoWithIncorrectValuesForMakeBooking));
    }


    @Test
    public void getBookingTotalPrice_thenEquals(){
        assertEquals(1300f, bookingServiceImpl.getBookingTotalPrice(bookingDtoWithCorrectValuesForMakeBooking), 0f);
    }


    @Test
    public void createExistedBooking_thenNull(){
        assertNull(bookingServiceImpl.createBooking(bookingDtoWithCorrectValuesForMakeBooking));
    }


    @Test
    public void createNotExistedBooking_thenNotNull(){
        assertNotNull(bookingServiceImpl.createBooking(bookingDtoWithCorrectValuesForCreateBooking));
    }

}
