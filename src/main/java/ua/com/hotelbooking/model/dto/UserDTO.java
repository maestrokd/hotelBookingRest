package ua.com.hotelbooking.model.dto;

public class UserDTO {

    // Fields
    private String login;
    private String password;
    private String name;


    // Constructors
    public UserDTO() {
    }

    public UserDTO(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }


    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
