package ua.com.hotelbooking.model.services;

import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();

    public User createUser(UserDTO userDTO);

    public boolean isUserExist(String login);

    public User makeUser(UserDTO userDTO);
}
