package ua.com.hotelbooking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.UserRepository;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {

        // TODO User is exist?
        if(this.isUserExist(user.getLogin())){
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public boolean isUserExist(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return true;
        }
        return false;
    }
}
