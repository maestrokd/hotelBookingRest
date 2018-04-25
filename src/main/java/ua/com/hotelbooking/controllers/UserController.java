package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.services.UserService;


@RestController
@Api(value = "usersgateway", description = "Users related operations")
public class UserController {

    @Autowired
    private UserService userService;


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

}
