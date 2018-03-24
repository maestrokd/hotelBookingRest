package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hotelbooking.model.entities.Booking;

import java.util.Date;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long>, MyBookingRepository {

    List<Booking> findBookingsByUserLogin (String login);

    List<Booking> findByStartBookingDateBetweenOrEndBookingDateBetween (Date startBookingDate, Date endBookingDate, Date startBookingDate2, Date endBookingDate2);

}
