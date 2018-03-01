package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.hotelbooking.model.entities.Room;

import java.util.Date;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {

//    @Query("SELECT r FROM ua.com.hotelbooking.model.entities.Room r "
//            + " JOIN ua.com.hotelbooking.model.entities.Booking b ON b.room.id = r.id "
//            + " WHERE :startBookingDate BETWEEN b.startBookingDate AND b.endBookingDate " +
//            " OR :endBookingDate BETWEEN b.startBookingDate AND b.endBookingDate ")
//    List<Room> findByBooking_startBookingDateAndBooking_endBookingDate(@Param("startBookingDate") Date startBookingDate, @Param("endBookingDate") Date endBookingDate);


    @Query("SELECT r FROM ua.com.hotelbooking.model.entities.Room r ")
    List<Room> fall();

    List<Room> findRoomsByCategory(String category);

    Room findByNumber(int number);

}
