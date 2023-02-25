package com.tms.transportmanagementsystem.model;

public enum CargoType {
    CAR_PARTS,
    ELECTRONICS,
    HOUSE_HOLD_ITEMS,
    FURNITURE,
    CLOTHES;

    @Override
    public String toString() {
        return name().replace('_', ' ');
    }
}
