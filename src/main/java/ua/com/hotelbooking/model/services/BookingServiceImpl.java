package ua.com.hotelbooking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.entities.AdditionalOption;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.entities.User;
import ua.com.hotelbooking.model.repositories.AdditionalOptionRepository;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.repositories.UserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "bookingService")
public class BookingServiceImpl implements BookingService {

    // Fields
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AdditionalOptionRepository additionalOptionRepository;


    // Getters and Setters
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void setAdditionalOptionRepository(AdditionalOptionRepository additionalOptionRepository) {
        this.additionalOptionRepository = additionalOptionRepository;
    }


    // Methods
    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookingByUser(String userLogin) {
        return bookingRepository.findBookingsByUserLogin(userLogin);
    }

    @Override
    public List<Booking> getBookingByDate(Date startBookingDate, Date endBookingDate) {
        return bookingRepository.findByStartBookingDateBetweenOrEndBookingDateBetween(startBookingDate, endBookingDate, startBookingDate, endBookingDate);
    }

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {

        Booking booking = this.makeBooking(bookingDTO);

        // TODO send message about invalid parameter
        if(booking == null){
            return null;
        }

        // TODO send message "user does not exist"
        if(booking.getUser() == null) {
            return null;
        }

        // TODO if booking the room for this date exist
        List<Booking> bookingList = bookingRepository.findByStartBookingDateBetweenOrEndBookingDateBetween(booking.getStartBookingDate(), booking.getEndBookingDate(), booking.getStartBookingDate(), booking.getEndBookingDate());
        for(Booking b : bookingList) {

            // TODO send message "the room already booked"
            if(b.getRoom().getNumber() == booking.getRoom().getNumber()) {
                return null;
            }
        }

        return bookingRepository.save(booking);
    }


    @Override
    public float getBookingTotalPrice(BookingDTO bookingDTO) {

        Booking booking = this.makeBooking(bookingDTO);

        // TODO send message about invalid parameter
        if(booking == null){
            return -1;
        }

        // TODO New version without deprecated methods
        int numberOfDays = (booking.getEndBookingDate().getDate()-booking.getStartBookingDate().getDate()) + 1;
        float totalPrice;
        totalPrice = booking.getRoom().getPrice();
        for (AdditionalOption additionalOption : booking.getAdditionalOptions()) {
            totalPrice += additionalOption.getPrice();
        }
        totalPrice *= numberOfDays;
        return totalPrice;
    }

    @Override
    public Booking makeBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        if (bookingDTO.getUserLogin() != null) {
            User user = userRepository.findByLogin(bookingDTO.getUserLogin());
            booking.setUser(user);
        }
        Room room = roomRepository.findByNumber(Integer.parseInt(bookingDTO.getRoomNumber()));

        // TODO send message "the room does not exist"
        if(room == null ){
            return null;
        }

        booking.setRoom(room);
        booking.setStartBookingDateAsString(bookingDTO.getStartBookingDate());
        booking.setEndBookingDateAsString(bookingDTO.getEndBookingDate());
        if(bookingDTO.getAdditionalOptions().length != 0) {
            Set<AdditionalOption> additionalOptionSet = new HashSet<>();
            for (String str : bookingDTO.getAdditionalOptions()){
                AdditionalOption additionalOption = additionalOptionRepository.findByTitle(str);

                // TODO send message "the additional option does not exist"
                if(additionalOption == null){
                    return null;
                }

                additionalOptionSet.add(additionalOption);
            }
            booking.setAdditionalOptions(additionalOptionSet);
        }
        return booking;
    }
}
