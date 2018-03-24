//package ua.com.hotelbooking.repositories;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mapstruct.BeanMapping;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//import ua.com.hotelbooking.model.dto.AssemblerDate;
//import ua.com.hotelbooking.model.entities.Booking;
//import ua.com.hotelbooking.model.entities.Room;
//import ua.com.hotelbooking.model.repositories.MyRoomRepository;
//import ua.com.hotelbooking.model.repositories.RoomRepository;
//import ua.com.hotelbooking.model.repositories.RoomRepositoryImpl;
//
//import static org.junit.Assert.*;
//
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class RoomRepositoryTest {
//
//
////    @TestConfiguration
////    static class MyRoomRepositoryTestContextConfiguration {
////
////        @Bean
////        public MyRoomRepository myRoomRepository() {
////            return new RoomRepositoryImpl();
////        }
////    }
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//
////    @Test
////    public void whenFindFreeByBookingDates_thenReturnRooms() {
////        // given
////        Date startBookingDate = AssemblerDate.getInstance().getDateByString("10-03-2018");
////        Date endBookingDate  = AssemblerDate.getInstance().getDateByString("10-03-2018");
////        Room room1 = new Room();
////        room1.setNumber(101);
////
////        Booking booking1 = new Booking();
////        booking1.setStartBookingDate(startBookingDate);
////        booking1.setEndBookingDate(endBookingDate);
////        booking1.setRoom(room1);
////
////        testEntityManager.persist(room1);
////        testEntityManager.persist(booking1);
////        testEntityManager.flush();
////
////
////        // when
////        List<Room> freeRooms = roomRepository.findFreeRoomsByStartBookingDateAndEndBookingDate(startBookingDate, endBookingDate);
////
////        // then
////        assertEquals(1, freeRooms.size());
////
////    }
//
//}
