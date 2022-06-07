package com.homework.springboot.persistence.model;

import com.homework.springboot.validation.PasswordMatches;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@PasswordMatches
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty(message = "Email is required.")
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "First name cannot be null")
    @Size(max = 50, message = "First name must be maximum length of 50 characteres")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotEmpty(message = "Last name cannot be null")
    @Size(max = 50, message = "Last name must be maximum length of 50 characteres")
    @Column(name = "lastName", nullable = false)
    private String lastName;

//    @Pattern(regexp = "^(\\+\\d[503]( )?)?(\\d{4}[ ]?)\\d{4}$")
//    ^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$"
    @Pattern(regexp="(^(\\+(503)( )?)(\\d{4}[ ]?)(\\d{4})$)", message = "Phone number should follow the next pattern +503 #### ####")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotEmpty(message = "Password is required.")
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;

    @Column(name = "createdDate")
    private Calendar created = Calendar.getInstance();
    //LocalDateTime usar


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public User() {

    }

}
