package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class City {
    @NotNull
    private final ZonedDateTime createDate;
    private int cityId;
    @NotNull
    private StringProperty city;
    @NotNull
    private SimpleIntegerProperty countryId;
    private StringProperty createdBy;
    private ObjectProperty<Timestamp> lastUpdate;
    private StringProperty lastUpdateBy;


    public City(String city, int countryId) {
        this(city, countryId, null);
    }

    public City(String city, int countryId, String createdBy) {
        this.city.setValue(city);
        this.countryId.setValue(countryId);
        this.createdBy.setValue(createdBy);
        this.lastUpdateBy.setValue(createdBy);
        this.createDate = ZonedDateTime.now(ZoneId.of("UTC"));
    }


    public int getCityId() {
        return cityId;
    }

    public String getCity() {
        return city.getValue();
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public int getCountryId() {
        return countryId.getValue();
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }
}
