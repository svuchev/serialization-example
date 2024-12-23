package com.stoyanvuchev.serializationexample;

import androidx.annotation.NonNull;

public class Plane implements java.io.Serializable {

    private String manufacturer;
    private String brand;
    private String model;
    private int manufacturedYear;
    private String tailNumber;
    private int lastAnnualService;
    private String ownerName;
    private String ownerAddress;
    private String currentLocation;
    private String homeLocation;
    private String otherNotes;
    private boolean isFlying;

    public Plane(
            String manufacturer,
            String brand,
            String model,
            int manufacturedYear,
            String tailNumber,
            int lastAnnualService,
            String ownerName,
            String ownerAddress,
            String currentLocation,
            String homeLocation,
            String otherNotes,
            boolean isFlying
    ) {
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.model = model;
        this.manufacturedYear = manufacturedYear;
        this.tailNumber = tailNumber;
        this.lastAnnualService = lastAnnualService;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.currentLocation = currentLocation;
        this.homeLocation = homeLocation;
        this.otherNotes = otherNotes;
        this.isFlying = isFlying;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufacturedYear() {
        return manufacturedYear;
    }

    public void setManufacturedYear(int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public int getLastAnnualService() {
        return lastAnnualService;
    }

    public void setLastAnnualService(int lastAnnualService) {
        this.lastAnnualService = lastAnnualService;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        this.isFlying = flying;
    }

    @NonNull
    @Override
    public String toString() {
        return "Plane [manufacturer = " + manufacturer +
                ", brand = " + brand +
                ", model = " + model +
                ", manufacturedYear = " + manufacturedYear +
                ", tailNumber = " + tailNumber +
                ", lastAnnualService = " + lastAnnualService +
                ", ownerName = " + ownerName +
                ", ownerAddress = " + ownerAddress +
                ", currentLocation = " + currentLocation +
                ", homeLocation = " + homeLocation +
                ", otherNotes = " + otherNotes +
                ", isFlying = " + isFlying + "]";
    }

}