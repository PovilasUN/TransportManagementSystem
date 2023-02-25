package com.tms.transportmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String checkpointLocation;
    private String usedAmountOfFuel;
    private LocalDateTime checkpointTime;
    @ManyToOne
    private Destination destination;

    public Checkpoint(String checkpointLocation, String usedAmountOfFuel, LocalDateTime checkpointTime, Destination destination) {
        this.checkpointLocation = checkpointLocation;
        this.usedAmountOfFuel = usedAmountOfFuel;
        this.checkpointTime = checkpointTime;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return getCheckpointLocation() + " " + getUsedAmountOfFuel() + "l. " + getCheckpointTime();
    }
}
