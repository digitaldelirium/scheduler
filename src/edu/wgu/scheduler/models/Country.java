package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    private SimpleStringProperty country;
    private String createdBy = new String();
    private SimpleObjectProperty<ZonedDateTime> lastUpdate;
    private SimpleStringProperty lastUpdateBy;

    public Country(String country) {
        this(country, null);
    }

    public Country(String country, String createdBy) {
        this.country = new SimpleStringProperty(country);
        this.createdBy = createdBy;
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.createdDate = ZonedDateTime.now(ZoneId.systemDefault());
    }

    public Country(int countryId, String country, ZonedDateTime createdDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.createdDate = createdDate;
        this.countryId = countryId;
        this.country = new SimpleStringProperty(country);
        this.createdBy = createdBy;
        this.lastUpdate = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    public Country(int countryId, String country, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.createdDate = ZonedDateTime.ofInstant(createdDate.toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        this.countryId = countryId;
        this.country = new SimpleStringProperty(country);
        this.createdBy = createdBy;
        System.out.println(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdate = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
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

    public StringProperty getCountryProperty(){
        return country;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
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

    public SimpleObjectProperty<ZonedDateTime> getLastUpdateProperty(){
        return lastUpdate;
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
