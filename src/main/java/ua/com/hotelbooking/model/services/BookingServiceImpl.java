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
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
    private AdditionalOptionRepository additionalOptionRepository;


    // Getters and Setters
    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Autowired
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

        if (!this.validateBooking(booking)) {
            return null;
        }

        return bookingRepository.save(booking);
    }


    private boolean validateBooking(Booking booking) {
        if(booking == null){
            return false;
        }

        if(booking.getUser() == null) {
            return false;
        }

        List<Booking> bookingList = bookingRepository.findBookingsByRoomWhereStartBookingDateOrEndBookingDateBetweenDates(
                booking.getRoom(), booking.getStartBookingDate(), booking.getEndBookingDate());

        if(!bookingList.isEmpty()) {
            return false;
        }

        return true;
    }


    @Deprecated
    public Booking createBookingOld(BookingDTO bookingDTO) {

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
        List<Booking> bookingList = bookingRepository.findByStartBookingDateBetweenOrEndBookingDateBetween(
                booking.getStartBookingDate(), booking.getEndBookingDate(),
                booking.getStartBookingDate(), booking.getEndBookingDate());

        System.out.println("bookingList. size: " + bookingList.size());

        for(Booking b : bookingList) {

            // TODO send message "the room already booked"
            if(b.getRoom().getNumber() == booking.getRoom().getNumber()) {
                System.out.println("Come Null");
                return null;
            }
        }

        return bookingRepository.save(booking);
    }


    @Override
    public float getBookingTotalPrice(BookingDTO bookingDTO) {
        float totalPrice;
        int numberOfDays;
        Booking booking = this.makeBooking(bookingDTO);

        if(booking == null){
            return -1;
        }

        // TODO New version without deprecated methods
        numberOfDays = (booking.getEndBookingDate().getDate()-booking.getStartBookingDate().getDate()) + 1;

        totalPrice = booking.getRoom().getPrice();
        for (AdditionalOption additionalOption : booking.getAdditionalOptions()) {
            totalPrice += additionalOption.getPrice();
        }
        totalPrice *= numberOfDays;
        return totalPrice;
    }


    public Booking makeBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        if (bookingDTO.getUserLogin() != null) {
            String userLogin = bookingDTO.getUserLogin();
            User user = userRepository.findByLogin(userLogin);
            booking.setUser(user);
        }
        Room room = roomRepository.findByNumber(Integer.parseInt(bookingDTO.getRoomNumber()));

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
