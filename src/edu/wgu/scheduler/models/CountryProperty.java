package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CountryProperty extends SimpleObjectProperty<Country> implements ICountry {

    /**
     * The constructor of {@code ObjectProperty}
     */
    public CountryProperty(int countryId, String country, String createBy, Date createDate, Timestamp lastUpdate, String lastUpdatedBy) {
        super(
                new Country(
                    countryId,
                    country,
                    createDate,
                    createBy,
                    lastUpdate,
                    lastUpdatedBy
            )
        );
    }

    public CountryProperty(Country country){
        super(country);
    }

    public int getCountryId() {
        return getValue().getCountryId();
    }

    public String getCountry() {
        return getValue().getCountry();
    }

    public void setCountry(String country) {
        getValue().setCountry(country);
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public ZonedDateTime getCreatedDate() {
        return getValue().getCreatedDate();
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

}
