package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Country implements ICountry {
    @NotNull
    private final ZonedDateTime createdDate;
    private int countryId;
    @NotNull
    private StringProperty country;
    private StringProperty createdBy;
    private ObjectProperty<ZonedDateTime> lastUpdate;
    private StringProperty lastUpdateBy;

    public Country(String country) {
        this(country, null);
    }

    public Country(String country, String createdBy) {
        this.country.setValue(country);
        this.createdBy.setValue(createdBy);
        this.lastUpdateBy.setValue(createdBy);
        this.createdDate = ZonedDateTime.now(ZoneId.systemDefault());
    }

    public Country(int countryId, String country, ZonedDateTime createdDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.createdDate = createdDate;
        this.countryId = countryId;
        this.country.set(country);
        this.createdBy.set(createdBy);
        this.lastUpdate.set(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdateBy.set(lastUpdateBy);
    }

    @Override
    public int getCountryId() {
        return countryId;
    }

    @Override
    public String getCountry() {
        return country.getValue();
    }

    @Override
    public void setCountry(String country) {
        this.country.setValue(country);
    }

    @Override
    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    @Override
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }

    @Override
    public String toString() {
        return "Country{" +
                "createdDate=" + createdDate +
                ", countryId=" + countryId +
                ", country=" + country +
                ", createdBy=" + createdBy +
                ", lastUpdate=" + lastUpdate +
                ", lastUpdateBy=" + lastUpdateBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country1 = (Country) o;

        if (getCountryId() != country1.getCountryId()) return false;
        return getCountry().equals(country1.getCountry());
    }

    @Override
    public int hashCode() {
        return getCountryId();
    }
}
