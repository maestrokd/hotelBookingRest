package ua.com.hotelbooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.BookingDTO;
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
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;


    // 1
    @RequestMapping(
            value = "/api/freerooms/{startBookingDate}/{endBookingDate}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Room>> getFreeRoomsByDate(@PathVariable("startBookingDate") String startBookingDateS, @PathVariable("endBookingDate") String endBookingDateS) {

        // TODO in other method
        Date startBookingDate = null;
        Date endBookingDate  = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            startBookingDate = simpleDateFormat.parse(startBookingDateS);
            endBookingDate = simpleDateFormat.parse(endBookingDateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Room> roomList = roomService.getAvailableRoomsByDate(startBookingDate, endBookingDate);
        if (roomList != null) {
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // 2
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


    // 3
    @PostMapping
            (value = "/api/usercreate", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createUser(@RequestBody User user) {
        User userFromDB = userService.createUser(user);
        if (userFromDB != null) {
            return "User created successfully";
        }
        return "User creation failed";
    }


    // 4
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


    // 5
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


    // 6
    @PostMapping
            (value = "/api/bookingtotalprice", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public float getTotalBookingPrice(@RequestBody BookingDTO bookingDTO) {
        float totalPrice = bookingService.getBookingTotalPrice(bookingDTO);
        return totalPrice;
    }


    // 7
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
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable("startBookingDate") String startBookingDateS, @PathVariable("endBookingDate") String endBookingDateS) {

        // TODO in other method
        Date startBookingDate = null;
        Date endBookingDate  = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            startBookingDate = simpleDateFormat.parse(startBookingDateS);
            endBookingDate = simpleDateFormat.parse(endBookingDateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Booking> bookingList = bookingService.getBookingByDate(startBookingDate, endBookingDate);
        if (bookingList != null) {
            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
