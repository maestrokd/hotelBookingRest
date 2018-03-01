package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;

import java.util.Date;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

//    @Query("SELECT r FROM ua.com.hotelbooking.model.entities.Room r " +
//            " JOIN ua.com.hotelbooking.model.entities.Booking b ON b.room.id = r.id "
//            +
//            " WHERE :startBookingDate BETWEEN b.startBookingDate AND b.endBookingDate " +
//            " OR :endBookingDate BETWEEN b.startBookingDate AND b.endBookingDate "
//    )
//    List<Room> findBookingByStartBookingDateAndEndBookingDate(@Param("startBookingDate") Date startBookingDate, @Param("endBookingDate") Date endBookingDate);
//    List<Room> findBookingRoomsByDate();

    List<Booking> findBookingsByUserLogin (String login);

    List<Booking> findByStartBookingDateBetweenOrEndBookingDateBetween (Date startBookingDate, Date endBookingDate, Date startBookingDate2, Date endBookingDate2);

}
