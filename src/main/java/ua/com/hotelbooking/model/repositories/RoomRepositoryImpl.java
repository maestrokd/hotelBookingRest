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
    public List<Room> findByBooking_startBookingDateAndBooking_endBookingDate(Date startBookingDate, Date endBookingDate) {

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


    // TODO Correct Query for find free rooms
    @Override
    public List<Room> findFreeByBooking_startBookingDateAndBooking_endBookingDate(Date startBookingDate, Date endBookingDate) {

        List<Room> roomList = entityManager.createQuery(
                "SELECT r FROM ua.com.hotelbooking.model.entities.Room r " +
                        " LEFT JOIN r.bookingList b " +
                        " WHERE ( " +
                            "(" +
                                " :startBookingDate NOT BETWEEN b.startBookingDate AND b.endBookingDate " +
                                " AND :endBookingDate NOT BETWEEN b.startBookingDate AND b.endBookingDate" +
                            ")" +
                            " AND ((:startBookingDate < b.startBookingDate AND :endBookingDate < b.startBookingDate) " +
                                " OR (:startBookingDate > b.endBookingDate AND :endBookingDate > b.endBookingDate)) " +
                            " )" +
                        " OR b.room IS NULL " +
                        "GROUP BY r.id"
        )
                .setParameter("startBookingDate", startBookingDate)
                .setParameter("endBookingDate", endBookingDate)
                .getResultList();

        return roomList;
    }

//    @Override
//    public List<Room> findFreeByBooking_startBookingDateAndBooking_endBookingDate(Date startBookingDate, Date endBookingDate) {
//
//        List<Room> roomList = entityManager.createQuery(
//                "SELECT r FROM ua.com.hotelbooking.model.entities.Room r " +
//                        " LEFT JOIN " +
//                        "( SELECT b " +
//                        " FROM r.bookingList b" +
//                        " WHERE :startBookingDate  BETWEEN b.start_booking_date AND b.end_booking_date " +
//                        " OR :endBookingDate  BETWEEN b.start_booking_date AND b.end_booking_date " +
//                        " OR (:startBookingDate < b.start_booking_date " +
//                        " AND :endBookingDate > b.end_booking_date) " +
//                        ")" +
//                        " bu " +
//                        " WHERE bu.room IS NULL "
//        )
//                .setParameter("startBookingDate", startBookingDate)
//                .setParameter("endBookingDate", endBookingDate)
//                .getResultList();
//
//        return roomList;
//    }

}
