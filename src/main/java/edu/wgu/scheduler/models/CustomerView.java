package edu.wgu.scheduler.models;

import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:52.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerView implements ICustomerView {
    public boolean isActive() {
        return false;
    }

    public int getAddressId() {
        return 0;
    }

    public LocalDate getCreateDate() {
        return null;
    }

    public String getCreatedBy() {
        return null;
    }

    public String getCustomerName() {
        return null;
    }

    public String getLastUpdateBy() {
        return null;
    }

    public String getAddress() {
        return null;
    }

    public String getCity() {
        return null;
    }

    public String getCountry() {
        return null;
    }

    public String getPhone() {
        return null;
    }

    public String getPostalCode() {
        return null;
    }
}
