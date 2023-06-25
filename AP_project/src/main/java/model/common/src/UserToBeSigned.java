package main.java.model.common.src;
import java.io.Serializable;
import java.util.Date;

public class UserToBeSigned implements Serializable {
    private String username, password, firstName, lastName, email, phoneNumber;
    private Date birthDate;
    private int userDbId;

    public UserToBeSigned() {
    }

    public UserToBeSigned(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserToBeSigned(String username, String password, String firstName, String lastName, String email, String phoneNumber, Date birthDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = (java.sql.Date) birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserDbId(int userDbId) {
        this.userDbId = userDbId;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
