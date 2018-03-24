package ua.com.hotelbooking.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

}
