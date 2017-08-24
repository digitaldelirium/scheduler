package edu.wgu.scheduler.models;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:40.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface ICity {
    int getCityId();

    String getCity();

    void setCity(String city);

    int getCountryId();

    ZonedDateTime getCreateDate();

    String getCreatedBy();

    String getLastUpdateBy();

    void setLastUpdateBy(String lastUpdateBy);

    ZonedDateTime getLastUpdate();

    void setLastUpdate(Timestamp lastUpdate);
}
