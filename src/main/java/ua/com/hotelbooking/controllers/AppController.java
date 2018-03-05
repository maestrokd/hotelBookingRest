package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.BookingService;
import ua.com.hotelbooking.model.services.RoomService;
import ua.com.hotelbooking.model.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "bookingsgateway", description = "Holel booking related operations")
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
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

        List<Room> roomList = roomService.getAvailableRoomsByDate(startBookingDate, endBookingDate);
        if (roomList != null) {
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


    /**
     * 3. Create user.
     */
    @PostMapping
            (value = "/api/usercreate", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createUser(@RequestBody UserDTO userDTO) {
        User userFromDB = userService.createUser(userDTO);
        if (userFromDB != null) {
            return "User created successfully";
        }
        return "User creation failed";
    }


    /**
     * 4. User can book the room for specified days.
     */
    @PostMapping
            (value = "/api/bookingcreate", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking bookingDB = bookingService.createBooking(bookingDTO);
        if (bookingDB != null) {
            return "Booking created successfully";
        }
        return "Booking creation failed";
    }


    /**
     * 5. User can view his booking.
     */
    @RequestMapping(
            value = "/api/bookingsbyuser/{userlogin}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable("userlogin") String userlogin) {
        List<Booking> bookingList = bookingService.getAllBookingByUser(userlogin);
        if (!bookingList.isEmpty()) {
            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * 6. User can get the total price of the booking (room for dates period + cost of additional options).
     */
    @PostMapping
            (value = "/api/bookingtotalprice", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public float getTotalBookingPrice(@RequestBody BookingDTO bookingDTO) {
        float totalPrice = bookingService.getBookingTotalPrice(bookingDTO);
        return totalPrice;
    }


    /**
     * 7. View all bookings for the hotel.
     */
    @RequestMapping(
            value = "/api/bookings"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Booking>> getBookings() {
        List<Booking> bookingList = bookingService.getAllBooking();
        if (bookingList != null) {
            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // +8
    @RequestMapping(
            value = "/api/bookings/{startBookingDate}/{endBookingDate}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable("startBookingDate") String startBookingDateStr, @PathVariable("endBookingDate") String endBookingDateStr) {

        Date startBookingDate = AssemblerDate.getInstance().getDateByString(startBookingDateStr);
        Date endBookingDate  = AssemblerDate.getInstance().getDateByString(endBookingDateStr);

        List<Booking> bookingList = bookingService.getBookingByDate(startBookingDate, endBookingDate);
        if (bookingList != null) {
            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
