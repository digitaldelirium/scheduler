package edu.wgu.scheduler.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/18/2017 at 20:39.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface IAppointmentView {

    String getTitle();

    String getDescription();

    String getLocation();

    String getContact();

    String getUrl();

    String getCustomerName();

    ZonedDateTime getStart();

    ZonedDateTime getEnd();

    LocalDate getCreateDate();

    String getCreatedBy();

    ZonedDateTime getLastUpdate();
}