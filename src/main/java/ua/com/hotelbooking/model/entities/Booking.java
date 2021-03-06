package ua.com.hotelbooking.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @JsonManagedReference
    @ManyToOne
    private User user;

    @JsonManagedReference
    @ManyToOne
    private Room room;

    @JsonIgnore
    @Column(name = "start_booking_date")
    private Date startBookingDate;

    @JsonIgnore
    @Column(name = "end_booking_date")
    private Date endBookingDate;

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "bookings_additional_options",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_option_id"))
    private Set<AdditionalOption> additionalOptions= new HashSet<>();


    // Constructors
    public Booking() {
    }

    public Booking(User user, Room room, Date startBookingDate, Date endBookingDate) {
        this.user = user;
        this.room = room;
        this.startBookingDate = startBookingDate;
        this.endBookingDate = endBookingDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getStartBookingDate() {
        return startBookingDate;
    }


    public String getStartBookingDateAsString () {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return  simpleDateFormat.format(this.startBookingDate);
    }

    public void setStartBookingDate(Date startBookingDate) {
        this.startBookingDate = startBookingDate;
    }

    public void setStartBookingDateAsString(String startBookingDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.startBookingDate = simpleDateFormat.parse(startBookingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getEndBookingDate() {
        return endBookingDate;
    }

    public String getEndBookingDateAsString () {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return  simpleDateFormat.format(this.endBookingDate);
    }

    public void setEndBookingDate(Date endBookingDate) {
        this.endBookingDate = endBookingDate;
    }

    public void setEndBookingDateAsString(String endBookingDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.endBookingDate = simpleDateFormat.parse(endBookingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Set<AdditionalOption> getAdditionalOptions() {
        return additionalOptions;
    }

    public void setAdditionalOptions(Set<AdditionalOption> additionalOptions) {
        this.additionalOptions = additionalOptions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (user != null ? !user.equals(booking.user) : booking.user != null) return false;
        if (room != null ? !room.equals(booking.room) : booking.room != null) return false;
        if (startBookingDate != null ? !startBookingDate.equals(booking.startBookingDate) : booking.startBookingDate != null)
            return false;
        return endBookingDate != null ? endBookingDate.equals(booking.endBookingDate) : booking.endBookingDate == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (startBookingDate != null ? startBookingDate.hashCode() : 0);
        result = 31 * result + (endBookingDate != null ? endBookingDate.hashCode() : 0);
        return result;
    }
}
