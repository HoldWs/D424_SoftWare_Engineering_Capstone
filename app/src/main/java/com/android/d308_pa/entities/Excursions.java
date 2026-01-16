package com.android.d308_pa.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursions {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;

    private String excursionName;
    private double price;
    private int vacationID;


    private String excursionDate;

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
    public Excursions(int excursionID, String excursionName, double price, int vacationID, String excursionDate) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.price = price;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }
}
