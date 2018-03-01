package ua.com.hotelbooking.model.dto;

public class BookingDTO {

    private String userLogin;

    private String roomNumber;

    private String startBookingDate;

    private String endBookingDate;

    private String[] additionalOptions;


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
}
