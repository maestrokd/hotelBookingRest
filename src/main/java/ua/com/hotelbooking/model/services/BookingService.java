package ua.com.hotelbooking.model.services;

import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.entities.Booking;


import java.util.Date;
import java.util.List;

public interface BookingService {
    public List<Booking> getAllBooking();

    public List<Booking> getAllBookingByUser(String userLogin);

    public List<Booking> getBookingByDate(Date startBookingDate, Date endBookingDate);

    public Booking createBooking(BookingDTO bookingDTO);

    public float getBookingTotalPrice(BookingDTO bookingDTO);

}
