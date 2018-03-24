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
public class BookingServiceImplWithManyMocksTest {


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
    @Mock
    private Room roomMock;
    @Mock
    private AdditionalOption additionalOptionMock;
    @Mock
    private AdditionalOption additionalOptionMock2;

    private BookingServiceImpl bookingServiceImpl;
    private BookingDTO bookingDtoWithCorrectValuesForMakeBooking, bookingDtoWithIncorrectValuesForMakeBooking, bookingDtoWithCorrectValuesForCreateBooking;
    private Room roomNumber2ForCreationBooking;
    private Booking bookingForCreateBooking;
    private Set<AdditionalOption> additionalOptionMockSet = mock(HashSet.class);


    @Before
    public void setupRepositories(){
        Date startBookingDate = AssemblerDate.getInstance().getDateByString("13-03-2018");
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString("14-03-2018");
        bookingDtoWithCorrectValuesForMakeBooking = new BookingDTO("user1", "1", "13-03-2018", "14-03-2018", new String[]{"breakfast", "cleaning"});
        bookingDtoWithIncorrectValuesForMakeBooking = new BookingDTO("", "0", "13-03-2018", "14-03-2018", new String[]{"anyString"});

        User userMock = mock(User.class);

        bookingDtoWithCorrectValuesForCreateBooking = new BookingDTO("user1", "2", "13-03-2018", "14-03-2018", new String[]{"breakfast", "cleaning"});
        roomNumber2ForCreationBooking = new Room(2, "CorrectCategory", 500);
        bookingForCreateBooking = new Booking(userMock, roomNumber2ForCreationBooking, startBookingDate, endBookingDate);
        bookingForCreateBooking.setAdditionalOptions(additionalOptionMockSet);

        when(userRepositoryMock.findByLogin("user1")).thenReturn(userMock);
        when(userRepositoryMock.findByLogin("IncorrectUserLogin")).thenReturn(null);

        when(roomRepositoryMock.findByNumber(0)).thenReturn(null);
        when(roomRepositoryMock.findByNumber(1)).thenReturn(roomMock);
        when(roomRepositoryMock.findByNumber(2)).thenReturn(roomNumber2ForCreationBooking);

        when(additionalOptionRepositoryMock.findByTitle(anyString())).thenReturn(null);
        when(additionalOptionRepositoryMock.findByTitle("breakfast")).thenReturn(additionalOptionMock);
        when(additionalOptionRepositoryMock.findByTitle("cleaning")).thenReturn(additionalOptionMock2);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(bookingMock);
        when(bookingRepositoryMock.findBookingsByRoomWhereStartBookingDateOrEndBookingDateBetweenDates(
                roomMock, startBookingDate, endBookingDate)).thenReturn(bookingList);
        when(bookingRepositoryMock.findBookingsByRoomWhereStartBookingDateOrEndBookingDateBetweenDates(
                roomNumber2ForCreationBooking, startBookingDate, endBookingDate)).thenReturn(new ArrayList<>());

        when(bookingRepositoryMock.save(bookingForCreateBooking)).thenReturn(bookingForCreateBooking);

        bookingServiceImpl = new BookingServiceImpl();
        bookingServiceImpl.setUserRepository(userRepositoryMock);
        bookingServiceImpl.setRoomRepository(roomRepositoryMock);
        bookingServiceImpl.setAdditionalOptionRepository(additionalOptionRepositoryMock);
        bookingServiceImpl.setBookingRepository(bookingRepositoryMock);

    }

    @Before
    public void setupBooking(){

        when(bookingMock.getRoom()).thenReturn(roomMock);
        when(bookingMock.getAdditionalOptions()).thenReturn(additionalOptionMockSet);
        when(bookingMock.getStartBookingDate()).thenReturn(AssemblerDate.getInstance().getDateByString("13-03-2018"));
        when(bookingMock.getEndBookingDate()).thenReturn(AssemblerDate.getInstance().getDateByString("14-03-2018"));
    }

    @Before
    public void setupRoom(){
        when(roomMock.getPrice()).thenReturn(500f);
    }

    @Before
    public void setupAdditionalOption(){
        additionalOptionMockSet.add(additionalOptionMock);
        additionalOptionMockSet.add(additionalOptionMock2);

        when(additionalOptionMock.getPrice()).thenReturn(100f);
        when(additionalOptionMock2.getPrice()).thenReturn(80f);
    }


    @Test
    public void makeBookingByCorrectParameter_thenNotNull(){
        assertNotNull(bookingServiceImpl.makeBooking(bookingDtoWithCorrectValuesForMakeBooking));
    }


    @Test
    public void makeBookingByInvalidParameter_thenNull(){
        assertNull(bookingServiceImpl.makeBooking(bookingDtoWithIncorrectValuesForMakeBooking));
    }


    @Test
    public void getBookingTotalPrice_thenEquals(){
        assertEquals(1360f, bookingServiceImpl.getBookingTotalPrice(bookingDtoWithCorrectValuesForMakeBooking), 0f);
    }


    @Test
    public void createExistedBooking_thenNull(){
        assertNull(bookingServiceImpl.createBooking(bookingDtoWithCorrectValuesForMakeBooking));
    }


    @Test
    public void createNotExistedBooking_thenNotNull(){
        assertNotNull(bookingServiceImpl.createBooking(bookingDtoWithCorrectValuesForCreateBooking));
        assertEquals(bookingForCreateBooking, bookingServiceImpl.createBooking(bookingDtoWithCorrectValuesForCreateBooking));
    }

}
