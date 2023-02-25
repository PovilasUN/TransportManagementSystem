package com.tms.transportmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private CargoType cargoType;
    private String numberOfProducts;
    private String cargoWeight;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.REMOVE)
    private List<Destination> destinations;

    public Cargo(CargoType cargoType, String numberOfProducts, String cargoWeight) {
        this.cargoType = cargoType;
        this.numberOfProducts = numberOfProducts;
        this.cargoWeight = cargoWeight;
    }

    @Override
    public String toString() {
        return getCargoType() + " " + getNumberOfProducts() + "qty. " + getCargoWeight() + "kg.";
    }
}
