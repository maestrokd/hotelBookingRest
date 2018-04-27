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
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.BookingService;
import ua.com.hotelbooking.model.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // Fields
    @MockBean
    private UserService userServiceMock;
    @MockBean
    private BookingService bookingServiceMock;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;
    UserDTO userDTO;


    //Methods
    @Before
    public void setup(){
        objectMapper = new ObjectMapper();

        userDTO = new UserDTO("user1", "pass1", "Name1");
        User user = new User("user1", "pass1", "Name1");

        given(userServiceMock.createUser(userDTO)).willReturn(user);

//        User userMock = mock(User.class);
//        given(userServiceMock.createUser(userDTO)).willReturn(userMock);
//        when(userServiceMock.createUser(userDTO)).thenReturn(userMock);


        User user102 = new User("login102", "password102", "Name102");
        Room room = new Room(102, "newCategory", 300);
        Date startDate = mock(Date.class);
        Date endDate = mock(Date.class);
        Booking booking = new Booking(user102, room, startDate, endDate);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);

        given(bookingServiceMock.getAllBookingByUser("CorrectUserName")).willReturn(bookingList);
        given(bookingServiceMock.getAllBookingByUser("IncorrectUserName")).willReturn(new ArrayList<>());

    }


    @Test
    public void createUser_thenCreatedSuccessfully() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"user1\",\"password\":\"pass1\",\"name\":\"Name1\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                ;
        assertEquals("User created successfully", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void createUser_thenOK2() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                ;
        assertEquals("User created successfully", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void createUser_thenCreatedFail() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"user2\",\"password\":\"pass2\",\"name\":\"Name2\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
        assertEquals("User creation failed", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void createUser_thenCreatedFail2() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDTO())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
        assertEquals("User creation failed", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void getBookingsByUser_thenOK() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/users/CorrectUserName/bookings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


    @Test
    public void getBookingsByUser_thenNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/users/IncorrectUserName/bookings"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                ;
    }


}
