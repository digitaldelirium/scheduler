package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Country {
    @NotNull
    private final LocalDate createdDate;
    private int countryId;
    @NotNull
    private StringProperty country;
    private StringProperty createdBy;
    private ObjectProperty<Timestamp> lastUpdate;
    private StringProperty lastUpdateBy;

    public Country(String country) {
        this(country, null);
    }

    public Country(String country, String createdBy) {
        this.country.setValue(country);
        this.createdBy.setValue(createdBy);
        this.lastUpdateBy.setValue(createdBy);
        this.createdDate = LocalDate.now(ZoneId.of("UTC"));
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country.getValue();
    }

    public void setCountry(String country) {
        this.country.setValue(country);
    }

    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }
}
