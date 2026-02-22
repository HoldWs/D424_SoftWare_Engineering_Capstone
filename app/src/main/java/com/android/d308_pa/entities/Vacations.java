package com.android.d308_pa.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacations {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private double price;

    private String hotel;

    private String vacationStart;


    private String vacationEnd;

    private String chosenFlight;


    public Vacations(int vacationID, String vacationName, double price, String hotel, String vacationStart, String vacationEnd, String chosenFlight) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.price = price;
        this.hotel = hotel;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
        this.chosenFlight = chosenFlight;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(String vacationEnd) {
        this.vacationEnd = vacationEnd;
    }

    public String getVacationStart() {
        return vacationStart;
    }

    public void setVacationStart(String vacationStart) {
        this.vacationStart = vacationStart;
    }

    public String getChosenFlight() {
        return chosenFlight;
    }

    public void setChosenFlight(String chosenFlight) {
        this.chosenFlight = chosenFlight;
    }
}
