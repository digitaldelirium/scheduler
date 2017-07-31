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
public class City implements ICity {
    @NotNull
    private final ZonedDateTime createDate;
    private int cityId;
    @NotNull
    private StringProperty city;
    @NotNull
    private SimpleIntegerProperty countryId;
    private String createdBy;
    private ObjectProperty<Timestamp> lastUpdate;
    private StringProperty lastUpdateBy;

    public City(String city, int countryId) {
        this(city, countryId, null);
    }

    public City(String city, int countryId, String createdBy) {
        this.city.setValue(city);
        this.countryId.setValue(countryId);
        this.createdBy = createdBy;
        this.lastUpdateBy.setValue(createdBy);
        this.createDate = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public City(int cityId, String city, int countryId, String createdBy, Timestamp lastUpdate, String lastUpdateBy, ZonedDateTime createDate) {
        this.cityId = cityId;
        this.city.setValue(city);
        this.countryId.setValue(countryId);
        this.createdBy = createdBy;
        this.lastUpdate.setValue(lastUpdate);
        this.lastUpdateBy.setValue(lastUpdateBy);
        this.createDate = createDate;
    }

    @Override
    public int getCityId() {
        return cityId;
    }

    @Override
    public String getCity() {
        return city.getValue();
    }

    @Override
    public void setCity(String city) {
        this.city.setValue(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @Override
    public int getCountryId() {
        return countryId.getValue();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    @Override
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    @Override
    public Timestamp getLastUpdate() {
        return lastUpdate.get();
    }

    @Override
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public ObjectProperty<Timestamp> lastUpdateProperty() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("createDate=").append(createDate);
        sb.append(", cityId=").append(cityId);
        sb.append(", city=").append(city);
        sb.append(", countryId=").append(countryId);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append(", lastUpdateBy=").append(lastUpdateBy);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return getCityId() == city.getCityId();
    }

    @Override
    public int hashCode() {
        return getCityId();
    }
}
