package com.tms.transportmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String startLocation;
    private String endLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @ManyToOne
    private Truck truck;
    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Driver driver;
    @ManyToOne
    private Manager manager;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "destination", cascade = CascadeType.REMOVE)
    private List<Checkpoint> checkpoints;

    public Destination(String startLocation, String endLocation, LocalDate startDate, LocalDate endDate, Truck truck,
                       Cargo cargo, Driver driver) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.truck = truck;
        this.cargo = cargo;
        this.driver = driver;
    }

    @Override
    public String toString() {
        return getStartLocation() + " -> " + getEndLocation() + " " + getStartDate() + " " + getEndDate() + " " + getDeliveryStatus();
    }
}
