package edu.wgu.scheduler.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:44.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface IAddress {
    String getPostalCode();

    void setPostalCode(String postalCode);

    String getPhone();

    void setPhone(String phone);

    String getLastUpdateBy();

    void setLastUpdateBy(String lastUpdateBy);

    ZonedDateTime getCreatedDate();

    String getCreatedBy();

    String getAddress();

    void setAddress(String address);

    String getAddress2();

    void setAddress2(String address2);

    int getAddressId();
}
