package ua.com.hotelbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.BookingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    // Fields
    @MockBean
    private BookingService bookingServiceMock;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;
    BookingDTO bookingDTO;


    // Methods
    @Before
    public void setup(){

        objectMapper = new ObjectMapper();

        bookingDTO = new BookingDTO("login102", "102", "01-01-2018", "02-01-2018", new String[]{});

        User user = new User("login102", "password102", "Name102");
        Room room = new Room(102, "newCategory", 300);
        Date startDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Booking booking = new Booking(user, room, startDate, endDate);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);

        given(bookingServiceMock.getAllBooking()).willReturn(bookingList);

        given(bookingServiceMock.getAllBookingByUser("CorrectUserName")).willReturn(bookingList);
        given(bookingServiceMock.getAllBookingByUser("IncorrectUserName")).willReturn(new ArrayList<>());

        given(bookingServiceMock.getBookingTotalPrice(bookingDTO)).willReturn(300F);
        given(bookingServiceMock.getBookingTotalPrice(new BookingDTO())).willReturn(-1F);

        given(bookingServiceMock.createBooking(bookingDTO)).willReturn(booking);

    }


    @Test
    public void createBooking_thenOK() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/bookingcreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                ;

        assertEquals("Booking created successfully", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void createBooking_thenBadRequest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/bookingcreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookingDTO())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                ;

        assertEquals("Booking creation failed", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void getBookingsByUser_thenOK() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/bookingsbyuser/CorrectUserName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


    @Test
    public void getBookingsByUser_thenNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/bookingsbyuser/IncorrectUserName"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                ;
    }


    @Test
    public void getTotalBookingPrice_thenBadRequest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/bookingtotalprice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookingDTO())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
    }


    @Test
    public void getTotalBookingPrice_thenOK() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/bookingtotalprice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


    @Test
    public void getAllBookings_thenOK() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/bookings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


}
