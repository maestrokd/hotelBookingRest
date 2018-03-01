package ua.com.hotelbooking.model.services;

import ua.com.hotelbooking.model.entities.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();

    public User createUser(User user);

    public boolean isUserExist(String login);
}
