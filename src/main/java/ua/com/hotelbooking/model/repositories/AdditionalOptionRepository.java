package ua.com.hotelbooking.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hotelbooking.model.entities.AdditionalOption;
import ua.com.hotelbooking.model.entities.Booking;

import java.util.Date;
import java.util.List;


public interface AdditionalOptionRepository extends JpaRepository<AdditionalOption, Long> {

    AdditionalOption findByTitle(String title);

}
