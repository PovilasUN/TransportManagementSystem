package com.tms.transportmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.mapping.Constraint;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Enumerated(EnumType.STRING)
    private TruckModel truckModel;
    private LocalDate makeDate;
    private LocalDate technicalCheckExpirationDate;
    private double truckWeight;
    private int horsePower;
    @OneToOne
    private Driver driver;
    @ManyToOne
    private Manager manager;
    private double fuelTankCapacity;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "truck", cascade = CascadeType.DETACH)
    private List<Destination> destinations;

    public Truck(TruckModel truckModel, LocalDate makeDate, LocalDate technicalCheckExpirationDate, double truckWeight, int horsePower, double fuelTankCapacity, Driver driver) {
        this.truckModel = truckModel;
        this.makeDate = makeDate;
        this.technicalCheckExpirationDate = technicalCheckExpirationDate;
        this.truckWeight = truckWeight;
        this.horsePower = horsePower;
        this.fuelTankCapacity = fuelTankCapacity;
        this.driver = driver;
    }

    @Override
    public String toString() {
        return getTruckModel() + " " + getMakeDate() + " " + getTruckWeight() + "kg " + getHorsePower() + "HP " + getFuelTankCapacity() + "l.";
    }
}
