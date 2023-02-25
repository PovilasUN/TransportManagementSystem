package com.tms.transportmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Driver extends User {
    private String driverLicenceNumber;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "driver", cascade = CascadeType.DETACH)
    private List<Destination> destinations;
    @OneToOne
    private Truck truck;

    public Driver(String loginName, String password, String name, String surname, String email, String phoneNumber, LocalDate dateOfBirth, String driverLicenceNumber) {
        super(loginName, password, name, surname, email, phoneNumber, dateOfBirth);
        this.driverLicenceNumber = driverLicenceNumber;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + " " + getEmail() + " " + driverLicenceNumber;
    }
}


