package ua.com.hotelbooking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.hotelbooking.controllers.AppController;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.BookingService;
import ua.com.hotelbooking.model.services.BookingServiceImpl;
import ua.com.hotelbooking.model.services.RoomService;
import ua.com.hotelbooking.model.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class AppControllerTest {


    @MockBean
    private UserService userServiceMock;
    @MockBean
    private BookingService bookingServiceMock;
    @MockBean
    private RoomService roomServiceMock;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setup(){

        List<Room> roomListEmpty = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room());

        given(roomServiceMock.getAllRoomsByCategory(anyString())).willReturn(roomListEmpty);
        given(roomServiceMock.getAllRoomsByCategory("CorrectCategory")).willReturn(roomList);

        given(roomServiceMock.getAllRooms()).willReturn(roomList);

        User userMock = mock(User.class);
        UserDTO userDTO = new UserDTO("user1", "pass1", "Name1");
        User user = new User("user1", "pass1", "Name1");
//        given(userServiceMock.createUser(userDTO)).willReturn(userMock);
        given(userServiceMock.createUser(userDTO)).willReturn(user);
    }


    @Test
    public void getRoomsByCategoryWithCorrectParam() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/roomsbycategory/CorrectCategory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


    @Test
    public void getRoomsByCategoryWithInvalidParam() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/roomsbycategory/InvalidCategory"))
                .andDo(print())
                .andExpect(status().isNotFound())
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



//    @Test
//    public void createUser_thenCreatedSuccessfully() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
////        objectMapper.writeValueAsString(new UserDTO());
////        UserDTO userDtoMock = mock(UserDTO.class);
//        MvcResult mvcResult = mockMvc
//                .perform(post("/api/usercreate")
//                        .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(userDtoMock)))
//                .content("{\"login\":\"user1\",\"password\":\"pass1\",\"name\":\"Name1\"}"))
//                .andDo(print())
//                .andExpect(status().isCreated())
////                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andReturn()
//                ;
//        assertEquals("User created successfully", mvcResult.getResponse().getContentAsString());
//    }


    @Test
    public void createUser_thenOK2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValueAsString(new UserDTO());

        MvcResult mvcResult = mockMvc
                .perform(post("/api/usercreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDTO())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                ;
    }


    @Test
    public void createUser_thenCreatedFail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValueAsString(new UserDTO());
//        UserDTO userDtoMock = mock(UserDTO.class);
        MvcResult mvcResult = mockMvc
                .perform(post("/api/usercreate")
                        .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDtoMock)))
                        .content("{\"login\":\"user1\",\"password\":\"pass1\",\"name\":\"Name1\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
        assertEquals("User creation failed", mvcResult.getResponse().getContentAsString());
    }

}
