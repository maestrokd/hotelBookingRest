package ua.com.hotelbooking.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.hotelbooking.model.dto.BookingDTO;
import ua.com.hotelbooking.model.repositories.AdditionalOptionRepository;
import ua.com.hotelbooking.model.repositories.BookingRepository;
import ua.com.hotelbooking.model.repositories.RoomRepository;
import ua.com.hotelbooking.model.repositories.UserRepository;
import ua.com.hotelbooking.model.services.BookingServiceImpl;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceImplTestWithTruthRepositories {

    // Fields
    private BookingServiceImpl bookingServiceImpl;


    // Getters and Setters
    @Autowired
    public void setBookingServiceImpl(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }


    //Methods
    @Test
    public void createExistedBooking_thenNull(){
        BookingDTO bookingDTO = new BookingDTO("user1", "3", "10-03-2018", "10-03-2018", new String[]{});
        assertNull(bookingServiceImpl.createBooking(bookingDTO));
    }


}
