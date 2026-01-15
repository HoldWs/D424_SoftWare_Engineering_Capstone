package com.android.d308_pa.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacations {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private double price;

    public Vacations(int vacationID, String vacationName, double price) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.price = price;
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
}
