package ua.com.hotelbooking.model.dto;

import java.util.Arrays;

public class BookingDTO {

    // Fields
    private String userLogin;
    private String roomNumber;
    private String startBookingDate;
    private String endBookingDate;
    private String[] additionalOptions;


    // Constructors
    public BookingDTO() {
    }

    public BookingDTO(String userLogin, String roomNumber, String startBookingDate, String endBookingDate, String[] additionalOptions) {
        this.userLogin = userLogin;
        this.roomNumber = roomNumber;
        this.startBookingDate = startBookingDate;
        this.endBookingDate = endBookingDate;
        this.additionalOptions = additionalOptions;
    }


    // Getters and Setters
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartBookingDate() {
        return startBookingDate;
    }

    public void setStartBookingDate(String startBookingDate) {
        this.startBookingDate = startBookingDate;
    }

    public String getEndBookingDate() {
        return endBookingDate;
    }

    public void setEndBookingDate(String endBookingDate) {
        this.endBookingDate = endBookingDate;
    }

    public String[] getAdditionalOptions() {
        return additionalOptions;
    }

    public void setAdditionalOptions(String[] additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingDTO that = (BookingDTO) o;

        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) return false;
        if (roomNumber != null ? !roomNumber.equals(that.roomNumber) : that.roomNumber != null) return false;
        if (startBookingDate != null ? !startBookingDate.equals(that.startBookingDate) : that.startBookingDate != null)
            return false;
        if (endBookingDate != null ? !endBookingDate.equals(that.endBookingDate) : that.endBookingDate != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(additionalOptions, that.additionalOptions);
    }

    @Override
    public int hashCode() {
        int result = userLogin != null ? userLogin.hashCode() : 0;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + (startBookingDate != null ? startBookingDate.hashCode() : 0);
        result = 31 * result + (endBookingDate != null ? endBookingDate.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(additionalOptions);
        return result;
    }
}
