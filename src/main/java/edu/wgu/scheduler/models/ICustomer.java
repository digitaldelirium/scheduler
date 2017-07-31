package edu.wgu.scheduler.models;

import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:47.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface ICustomer {
    int getCustomerId();

    boolean isActive();

    void setActive(boolean active);

    int getAddressId();

    LocalDate getCreateDate();

    String getCreatedBy();

    String getCustomerName();

    void setCustomerName(String customerName);

    String getLastUpdateBy();

    void setLastUpdateBy(String lastUpdateBy);
}
