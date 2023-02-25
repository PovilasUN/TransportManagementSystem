package com.tms.transportmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Manager extends User {
    private String companyName;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "manager", cascade = CascadeType.DETACH)
    private List<Truck> trucks;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "manager", cascade = CascadeType.DETACH)
    private List<Destination> destinations;

    public Manager(String loginName, String password, String name, String surname, String email, String phoneNumber, LocalDate dateOfBirth, String companyName) {
        super(loginName, password, name, surname, email, phoneNumber, dateOfBirth);
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + " " + getEmail() + " " + getCompanyName();
    }
}
