package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.AssemblerDate;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.services.BookingService;
import java.util.Date;
import java.util.List;


@RestController
@Api(value = "bookingsgateway", description = "Booking related operations")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    /**
     * 4. User can book the room for specified days.
     */
    @PostMapping
            (value = "/api/bookingcreate", headers = {"Accept=application/json; charset=utf-8"})
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking bookingDB = bookingService.createBooking(bookingDTO);
        if (bookingDB != null) {
            return new ResponseEntity<>("Booking created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Booking creation failed", HttpStatus.BAD_REQUEST);
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
    @PostMapping(value = "/api/bookingtotalprice"
                    , headers = {"Accept=application/json; charset=utf-8"})
    public ResponseEntity<Float> getTotalBookingPrice(@RequestBody BookingDTO bookingDTO) {
        float totalPrice = bookingService.getBookingTotalPrice(bookingDTO);
        if (totalPrice == (-1)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
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
        if (!bookingList.isEmpty()) {
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
