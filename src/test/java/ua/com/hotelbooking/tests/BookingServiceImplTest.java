package ua.com.hotelbooking.tests;

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
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.repositories.UserRepository;
import ua.com.hotelbooking.model.services.BookingServiceImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceImplTest {


    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private AdditionalOptionRepository additionalOptionRepositoryMock;
    @Mock
    private BookingServiceImpl bookingServiceImplMock;
    @Mock
    private Booking bookingMock;
    @Mock
    private Room roomMock;
    @Mock
    private AdditionalOption additionalOptionMock;
    @Mock
    private AdditionalOption additionalOptionMock2;


    private BookingServiceImpl bookingServiceImpl;
    private BookingDTO bookingDtoWithCorrectValues, bookingDtoWithInvalidValues;


    @Before
    public void setupRepositories(){
        bookingDtoWithCorrectValues = new BookingDTO("user1", "1", "13-03-2018", "14-03-2018", new String[]{"breakfast", "cleaning"});
        bookingDtoWithInvalidValues = new BookingDTO("", "0", "13-03-2018", "14-03-2018", new String[]{"anyString"});

        User user = mock(User.class);

        when(userRepositoryMock.findByLogin("user1")).thenReturn(user);
        when(userRepositoryMock.findByLogin("IncorrectUserLogin")).thenReturn(null);

//        when(roomRepositoryMock.findByNumber(anyInt())).thenReturn(null);
        when(roomRepositoryMock.findByNumber(0)).thenReturn(null);
        when(roomRepositoryMock.findByNumber(1)).thenReturn(roomMock);

        when(additionalOptionRepositoryMock.findByTitle(anyString())).thenReturn(null);
        when(additionalOptionRepositoryMock.findByTitle("breakfast")).thenReturn(additionalOptionMock);
        when(additionalOptionRepositoryMock.findByTitle("cleaning")).thenReturn(additionalOptionMock2);

        bookingServiceImpl = new BookingServiceImpl();
        bookingServiceImpl.setUserRepository(userRepositoryMock);
        bookingServiceImpl.setRoomRepository(roomRepositoryMock);
        bookingServiceImpl.setAdditionalOptionRepository(additionalOptionRepositoryMock);

    }

    @Before
    public void setupBookingServiceImpl(){
        when(bookingServiceImplMock.makeBooking(bookingDtoWithCorrectValues)).thenReturn(bookingMock);
    }

    @Before
    public void setupBooking(){
        Set<AdditionalOption> additionalOptionSetMock = mock(HashSet.class);
        additionalOptionSetMock.add(additionalOptionMock);
        additionalOptionSetMock.add(additionalOptionMock2);

        when(bookingMock.getRoom()).thenReturn(roomMock);
        when(bookingMock.getAdditionalOptions()).thenReturn(additionalOptionSetMock);
        when(bookingMock.getStartBookingDate()).thenReturn(AssemblerDate.getInstance().getDateByString("13-03-2018"));
        when(bookingMock.getEndBookingDate()).thenReturn(AssemblerDate.getInstance().getDateByString("14-03-2018"));
    }

    @Before
    public void setupRoom(){
        when(roomMock.getPrice()).thenReturn(500f);
    }

    @Before
    public void setupAdditionalOption(){
        when(additionalOptionMock.getPrice()).thenReturn(100f);
        when(additionalOptionMock2.getPrice()).thenReturn(80f);
    }


    @Test
    public void makeBookingByCorrectParameter(){
        assertNotNull(bookingServiceImpl.makeBooking(bookingDtoWithCorrectValues));
    }


    @Test
    public void makeBookingByInvalidParameter(){
        assertNull(bookingServiceImpl.makeBooking(bookingDtoWithInvalidValues));
    }


    @Test
    public void getBookingTotalPrice(){
        assertEquals(1360f, bookingServiceImpl.getBookingTotalPrice(bookingDtoWithCorrectValues), 0f);
    }


}
