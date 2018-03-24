package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hotelbooking.model.entities.Room;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> , MyRoomRepository {

    List<Room> findRoomsByCategory(String category);

    Room findByNumber(int number);

}
