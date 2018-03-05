package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;

import java.util.Date;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> , MyRoomRepository {

//    @Query(
//            value =
//                    "SELECT r FROM ua.com.hotelbooking.model.entities.Room r "
//            + " JOIN ua.com.hotelbooking.model.entities.Booking b ON b.room.id = r.id "
//            + " JOIN r.bookingList b ON b.room.id = r.id "
//            + " WHERE :startBookingDate BETWEEN b.startBookingDate AND b.endBookingDate "
//            + " OR :endBookingDate BETWEEN b.startBookingDate AND b.endBookingDate "
//            , nativeQuery = true
//    )
//    List<Room> findByBookingList();
//    List<Room> findByBookingList(@Param("startBookingDate") Date startBookingDate, @Param("endBookingDate") Date endBookingDate);
////    List<Room> findByBooking_startBookingDateAndBooking_endBookingDate(@Param("startBookingDate") Date startBookingDate, @Param("endBookingDate") Date endBookingDate);
//    List<Room> FindAllWithDescriptionQuery(@Param("startBookingDate") Date startBookingDate, @Param("endBookingDate") Date endBookingDate);

//    List<Room> findByBookingListIsNotContaining(List<Booking> bookingList);



//    @Query("SELECT r FROM ua.com.hotelbooking.model.entities.Room r ")
//    List<Room> fall();

    List<Room> findRoomsByCategory(String category);

    Room findByNumber(int number);

}
