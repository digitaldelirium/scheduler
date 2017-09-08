package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    
    private final ZonedDateTime createdDate;
    private int countryId;
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

    public Country(int countryId, String country, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.createdDate = ZonedDateTime.ofInstant(createdDate.toInstant(), ZoneId.systemDefault());
        this.countryId = countryId;
        this.country = new SimpleStringProperty(country);
        this.createdBy = createdBy;
        this.lastUpdate = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    public Country(int countryId, String country, String createdBy, Timestamp createDate, Timestamp lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = new SimpleStringProperty(country);
        this.createdBy = createdBy;
        this.createdDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.systemDefault());
        this.lastUpdate = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }

    @Override
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public int getCountryId() {
        return countryId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate.get();
    }

    public SimpleObjectProperty<ZonedDateTime> lastUpdateProperty() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public SimpleStringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
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
