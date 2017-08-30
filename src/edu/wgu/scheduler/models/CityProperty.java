package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:46.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CityProperty extends SimpleObjectProperty<City> implements ICity {

    public CityProperty(String city, int countryId) {
        super(new City(city, countryId));
        init();
    }

    public CityProperty(String city, int countryId, String createdBy) {
        super(new City(city, countryId, createdBy));
        init();
    }

    public CityProperty(int cityId, String city, int countryId, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Timestamp createDate) {
        super(new City(cityId, city, countryId, createdBy, lastUpdate, lastUpdatedBy, createDate));
        init();
    }

    public CityProperty(City city) {
        super(city);
        init();
    }

    public int getCityId() {
        return getValue().getCityId();
    }

    public String getCity() {
        return getValue().getCity();
    }

    public void setCity(String city) {
        getValue().setCity(city);
    }

    public StringProperty cityProperty() {
        return getValue().cityProperty();
    }

    public int getCountryId() {
        return getValue().getCountryId();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return getValue().countryIdProperty();
    }

    public ZonedDateTime getCreateDate() {
        return getValue().getCreateDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    public String getLastUpdatedBy() {
        return getValue().getLastUpdateBy();
    }

    public StringProperty lastUpdateByProperty() {
        return getValue().lastUpdateByProperty();
    }

    public ZonedDateTime getLastUpdate() {
        return getValue().getLastUpdate();
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        getValue().setLastUpdate(lastUpdate);
    }

    public ObjectProperty<ZonedDateTime> lastUpdateProperty() {
        return getValue().lastUpdateProperty();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().lastUpdateProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdateByProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().countryIdProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().cityProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    /**
     * {@inheritDoc}
     *
     * @param newValue
     */
    @Override
    public void set(City newValue) {
        super.set(newValue);
        init();
    }

    /**
     * {@inheritDoc}
     *
     * @param v
     */
    @Override
    public void setValue(City v) {
        super.setValue(v);
        init();
    }

}
