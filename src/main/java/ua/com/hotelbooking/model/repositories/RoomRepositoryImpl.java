package ua.com.hotelbooking.model.repositories;

import org.springframework.stereotype.Component;
import ua.com.hotelbooking.model.entities.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Component
public class RoomRepositoryImpl implements MyRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Room> findBookedRoomsByStartBookingDateAndEndBookingDate(Date startBookingDate, Date endBookingDate) {

        List<Room> roomList = entityManager.createQuery(
                "SELECT r FROM ua.com.hotelbooking.model.entities.Room r "
                        + " JOIN r.bookingList b "
                        + " WHERE :startBookingDate BETWEEN b.startBookingDate AND b.endBookingDate "
                        + " OR :endBookingDate BETWEEN b.startBookingDate AND b.endBookingDate")
                .setParameter("startBookingDate", startBookingDate)
                .setParameter("endBookingDate", endBookingDate)
                .getResultList();

        return roomList;
    }


    @Override
    public List<Room> findFreeRoomsByStartBookingDateAndEndBookingDate(Date startBookingDate, Date endBookingDate) {
        List<Room> roomList = entityManager.createQuery(
                "SELECT r FROM ua.com.hotelbooking.model.entities.Room r " +
                        " WHERE r.id NOT IN " +
                        " ( SELECT b.room.id FROM ua.com.hotelbooking.model.entities.Booking b " +
                        " WHERE  :startBookingDate BETWEEN b.startBookingDate AND b.endBookingDate " +
                        " OR :endBookingDate BETWEEN b.startBookingDate AND b.endBookingDate" +
                        " OR (:startBookingDate < b.startBookingDate AND :endBookingDate > b.endBookingDate) " +
                        " )"
        )
                .setParameter("startBookingDate", startBookingDate)
                .setParameter("endBookingDate", endBookingDate)
                .getResultList();
        return roomList;
    }

}
