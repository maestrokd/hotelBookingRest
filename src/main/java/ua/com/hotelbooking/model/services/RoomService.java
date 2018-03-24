package ua.com.hotelbooking.model.services;

import ua.com.hotelbooking.model.entities.Room;

import java.util.Date;
import java.util.List;

public interface RoomService {
    public List<Room> getAllRooms();

    public List<Room> getAllRoomsByCategory(String category);

    public List<Room> getAvailableRoomsByStartBookingDateAndEndBookingDate(Date startBookingDate, Date endBookingDate);


}
