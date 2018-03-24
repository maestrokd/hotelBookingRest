package ua.com.hotelbooking.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private float price;

    @JsonBackReference
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "room", fetch = FetchType.EAGER)
    private List<Booking> bookingList = new ArrayList<>();


    // Constructors
    public Room() {
    }

    public Room(int number, String category, float price) {
        this.number = number;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }


    // Methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return number == room.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
