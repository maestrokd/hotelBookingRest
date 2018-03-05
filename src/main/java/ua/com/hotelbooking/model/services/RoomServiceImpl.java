package ua.com.hotelbooking.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.hotelbooking.model.entities.Booking;
import ua.com.hotelbooking.model.entities.Room;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.repositories.RoomRepositoryImpl;

import java.util.Date;
import java.util.List;

@Service(value = "roomService")
public class RoomServiceImpl implements RoomService {

    // Fields
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomRepositoryImpl roomRepositoryImpl;
    @Autowired
    private BookingRepository bookingRepository;


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
    public List<Room> getAvailableRoomsByDate(Date startBookingDate, Date endBookingDate) {

        // TODO One request with LEFT JOIN to the database (in RoomRepositoryImpl)
        List<Room> roomList = this.getAllRooms();
        List<Booking> bookingList = bookingRepository.findByStartBookingDateBetweenOrEndBookingDateBetween(startBookingDate, endBookingDate, startBookingDate, endBookingDate);
        System.out.println("Size bookingList: " + bookingList.size());
        for (Booking booking : bookingList) {
            Room roomTemp = booking.getRoom();
            if (roomList.contains(roomTemp)) {
                roomList.remove(roomTemp);
            }
        }
        return roomList;
    }
}
