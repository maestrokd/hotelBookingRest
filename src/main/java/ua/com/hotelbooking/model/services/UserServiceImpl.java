package ua.com.hotelbooking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.hotelbooking.model.dto.UserDTO;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.repositories.UserRepository;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    // Fields
    private UserRepository userRepository;


    // Getters and Setters
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Methods
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public User createUser(UserDTO userDTO) {

        if(this.isUserExist(userDTO.getLogin())){
            return null;
        }
        User user = this.makeUser(userDTO);
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


    private User makeUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        return user;
    }
}
