package com.tms.transportmanagementsystem.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String loginName;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private boolean isAdmin;

    public User(String loginName, String password, String name, String surname, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}
