package edu.wgu.scheduler.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.time.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CountryProperty extends SimpleObjectProperty<Country> implements ICountry {

    private int countryId;
    private SimpleStringProperty country;
    private String createBy;
    private ZonedDateTime createDate;
    private ObjectProperty<ZonedDateTime> lastUpdate;
    private SimpleStringProperty lastUpdatedBy;

    /**
     * The constructor of {@code ObjectProperty}
     */
    public CountryProperty(int countryId, String country, String createBy, Instant createDate, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country.set(country);
        this.createBy = createBy;
        this.createDate = ZonedDateTime.ofInstant(createDate, ZoneId.systemDefault());
        this.lastUpdate.setValue(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country.getValueSafe();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCreatedBy() {
        return createBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createDate;
    }

    public String getLastUpdateBy() {
        return null;
    }

    public void setLastUpdateBy(String lastUpdateBy) {

    }
}
