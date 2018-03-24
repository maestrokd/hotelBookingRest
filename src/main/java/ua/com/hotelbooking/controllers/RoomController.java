package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.services.RoomService;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "roomsgateway", description = "Rooms related operations")
public class RoomController {

    @Autowired
    private RoomService roomService;


    /**
     * 1. View list of available rooms (room have a number, category, price, additional options
     * like breakfast, cleaning with additional cost) for specified dates.
     */
    @RequestMapping(
            value = "/api/freerooms/{startBookingDate}/{endBookingDate}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Room>> getFreeRoomsByDate(
            @PathVariable("startBookingDate") String startBookingDateStr,
            @PathVariable("endBookingDate") String endBookingDateStr) {

        Date startBookingDate = AssemblerDate.getInstance().getDateByString(startBookingDateStr);
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString(endBookingDateStr);

        List<Room> roomList = roomService.getAvailableRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate);
        if (!roomList.isEmpty()) {
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * 2. View rooms filtered by category.
     */
    @RequestMapping(
            value = "/api/roomsbycategory/{category}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Room>> getRoomsByCategory(@PathVariable("category") String category) {
        List<Room> roomList = roomService.getAllRoomsByCategory(category);
        if (!roomList.isEmpty()) {
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
