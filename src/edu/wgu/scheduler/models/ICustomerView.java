package edu.wgu.scheduler.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/18/2017 at 22:12.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface ICustomerView {

    boolean isActive();

    int getAddressId();

    int getCustomerId();

    LocalDate getCreateDate();

    String getCreatedBy();

    String getCustomerName();

    String getLastUpdateBy();

    String getAddress();

    String getAddress2();

    String getCity();

    String getCountry();

    String getPhone();

    String getPostalCode();

    ZonedDateTime getLastUpdate();
}
