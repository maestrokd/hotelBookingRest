package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hotelbooking.model.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);



}
