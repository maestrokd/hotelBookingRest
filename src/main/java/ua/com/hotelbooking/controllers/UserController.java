package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.BookingService;
import ua.com.hotelbooking.model.services.UserService;

import java.util.List;

@RestController
@Api(value = "usersgateway", description = "Users related operations")
public class UserController {

    // Fields
    private UserService userService;
    private BookingService bookingService;


    // Setters
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    // Methods
    /**
     * 3. Create user.
     */
    @PostMapping(
            value = "/api/users/create"
            , headers = {"Accept=text/plain; charset=utf-8"}
            )

    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User userFromDB = userService.createUser(userDTO);
        if (userFromDB != null) {
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User creation failed", HttpStatus.BAD_REQUEST);
    }


    /**
     * 5. User can view his booking.
     */
    @RequestMapping(
            value = "/api/users/{userlogin}/bookings"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable("userlogin") String userlogin) {
        List<Booking> bookingList = bookingService.getAllBookingByUser(userlogin);
        if (!bookingList.isEmpty()) {
            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
