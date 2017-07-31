package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 21:04.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewProperty extends SimpleObjectProperty<CustomerView> implements ICustomerView {

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int getAddressId() {
        return 0;
    }

    @Override
    public LocalDate getCreateDate() {
        return null;
    }

    @Override
    public String getCreatedBy() {
        return null;
    }

    @Override
    public String getCustomerName() {
        return null;
    }

    @Override
    public String getLastUpdateBy() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getPostalCode() {
        return null;
    }
}
