package edu.wgu.scheduler.models;

import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:46.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface ICountry {
    int getCountryId();

    String getCountry();

    void setCountry(String country);

    String getCreatedBy();

    ZonedDateTime getCreatedDate();

    String getLastUpdateBy();

    void setLastUpdateBy(String lastUpdateBy);
}
