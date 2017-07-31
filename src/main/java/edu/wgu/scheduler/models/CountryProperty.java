package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CountryProperty extends SimpleObjectProperty<Country> implements ICountry {
    public int getCountryId() {
        return 0;
    }

    public String getCountry() {
        return null;
    }

    public void setCountry(String country) {

    }

    public String getCreatedBy() {
        return null;
    }

    public LocalDate getCreatedDate() {
        return null;
    }

    public String getLastUpdateBy() {
        return null;
    }

    public void setLastUpdateBy(String lastUpdateBy) {

    }
}
