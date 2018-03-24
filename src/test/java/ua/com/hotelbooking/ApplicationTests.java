package ua.com.hotelbooking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.hotelbooking.controllers.BookingController;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private BookingController bookingController;


    @Test
    public void contextLoads() throws Exception {
        assertThat(bookingController).isNotNull();
    }


}
