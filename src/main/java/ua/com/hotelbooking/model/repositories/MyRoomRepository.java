package ua.com.hotelbooking.model.repositories;

import org.springframework.data.repository.query.Param;
import ua.com.hotelbooking.model.entities.Room;

import java.util.Date;
import java.util.List;

public interface MyRoomRepository {
    List<Room> findBookedRoomsByStartBookingDateAndEndBookingDate(
            @Param("startBookingDate") Date startBookingDate
            , @Param("endBookingDate") Date endBookingDate);


    List<Room> findFreeRoomsByStartBookingDateAndEndBookingDate(
            @Param("startBookingDate") Date startBookingDate
            , @Param("endBookingDate") Date endBookingDate);

}
