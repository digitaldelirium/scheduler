package edu.wgu.scheduler.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:34.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface IAppointment {
    int getAppointmentId();

    String getContact();

    void setContact(String contact);

    LocalDate getCreateDate();

    String getCreatedBy();

    int getCustomerId();

    void setCustomerId(int customerId);

    String getDescription();

    void setDescription(String description);

    ZonedDateTime getEnd();

    void setEnd(ZonedDateTime end);

    String getLastUpdateBy();

    void setLastUpdateBy(String lastUpdateBy);

    String getLocation();

    void setLocation(String location);

    ZonedDateTime getStart();

    void setStart(ZonedDateTime start);

    String getTitle();

    void setTitle(String title);

    String getUrl();

    void setUrl(String url);
}
