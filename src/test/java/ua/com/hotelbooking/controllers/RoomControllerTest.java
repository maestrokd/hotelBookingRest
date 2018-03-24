package ua.com.hotelbooking.controllers;

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
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.services.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @MockBean
    private RoomService roomServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private Date startBookingDate;
    private Date endBookingDate;


    @Before
    public void setup(){

        startBookingDate = AssemblerDate.getInstance().getDateByString("01-01-2018");
        endBookingDate  = AssemblerDate.getInstance().getDateByString("02-01-2018");

        List<Room> roomListEmpty = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room());

        given(roomServiceMock.getAllRoomsByCategory(anyString())).willReturn(roomListEmpty);
        given(roomServiceMock.getAllRoomsByCategory("CorrectCategory")).willReturn(roomList);

        given(roomServiceMock.getAllRooms()).willReturn(roomList);

        given(roomServiceMock.getAvailableRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate)).willReturn(roomList);

    }


    @Test
    public void getRoomsByCategoryWithCorrectParam_thenOk() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/roomsbycategory/CorrectCategory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                ;
    }


    @Test
    public void getRoomsByCategoryWithInvalidParam_thenNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/roomsbycategory/InvalidCategory"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                ;
    }


    @Test
    public void getFreeRoomsByDate_thenOk() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/freerooms/01-01-2018/02-01-2018"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                ;
    }


    @Test
    public void getFreeRoomsByDate_thenNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/freerooms/22-02-2018/23-02-2018"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                ;
    }

}
