package ua.com.hotelbooking.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.services.RoomServiceImpl;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomServiceImplTestWithTruthRepositories {

    @Autowired
    private RoomServiceImpl roomServiceImpl;


    @Test
    public void getAvailableRoomsByDateByCorrectParameter1(){
        Date startBookingDate = AssemblerDate.getInstance().getDateByString("10-03-2018");
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString("10-03-2018");
        assertEquals(7, roomServiceImpl.getAvailableRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate).size());
    }


    @Test
    public void getAvailableRoomsByDateByCorrectParameter2(){
        Date startBookingDate = AssemblerDate.getInstance().getDateByString("11-03-2018");
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString("12-03-2018");
        assertEquals(9, roomServiceImpl.getAvailableRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate).size());
    }


    @Test
    public void getAvailableRoomsByDateByCorrectParameter3(){
        Date startBookingDate = AssemblerDate.getInstance().getDateByString("14-03-2018");
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString("15-03-2018");
        assertEquals(9, roomServiceImpl.getAvailableRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate).size());
    }


    @Test
    public void getRoomsCategoryIncorrect(){
        assertEquals(0, roomServiceImpl.getAllRoomsByCategory("Incorrect").size());
    }


    @Test
    public void getRoomsCategoryA(){
        assertEquals(4, roomServiceImpl.getAllRoomsByCategory("A").size());
    }


}
