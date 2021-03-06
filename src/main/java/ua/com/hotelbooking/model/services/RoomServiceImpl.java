package ua.com.hotelbooking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.RoomRepository;

import java.util.Date;
import java.util.List;

@Service(value = "roomService")
public class RoomServiceImpl implements RoomService {

    // Fields
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;


    // Getters and Setters
    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    // Methods
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    @Override
    public List<Room> getAllRoomsByCategory(String category) {
        return roomRepository.findRoomsByCategory(category);
    }


    @Override
    public List<Room> getAvailableRoomsByStartBookingDateAndEndBookingDate(Date startBookingDate, Date endBookingDate) {
        List<Room> roomList2 = roomRepository.findFreeRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate);
        return roomList2;
    }


    @Deprecated
    public List<Room> getAvailableRoomsByStartBookingDateAndEndBookingDateOld(Date startBookingDate, Date endBookingDate) {

        // TODO One request with LEFT JOIN to the database (at RoomRepositoryImpl)
        List<Room> roomList = this.getAllRooms();
        List<Booking> bookingList = bookingRepository.findByStartBookingDateBetweenOrEndBookingDateBetween(startBookingDate, endBookingDate, startBookingDate, endBookingDate);

        for (Booking booking : bookingList) {
            Room roomTemp = booking.getRoom();
            if (roomList.contains(roomTemp)) {
                roomList.remove(roomTemp);
            }
        }

        // Second version
        List<Room> roomList2 = roomRepository.findFreeRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate);

        return roomList;
    }

}
