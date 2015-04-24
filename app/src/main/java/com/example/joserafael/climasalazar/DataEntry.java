package com.example.joserafael.climasalazar;

/**
 * Created by joserafael on 24/04/2015.
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataEntry implements Serializable {

    private String main;
    private String description;
    private String temp;
    private String humidity;
    private String day;

    public DataEntry() {

    }

    public DataEntry(String main, String description, String temp, String humidity, String day) {
        this.main = main;
        this.description = description;
        this.temp = temp;
        this.humidity = humidity;
        this.day = day;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String gethumidity() {
        return humidity;
    }

    public void sethumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }
}
