package edu.wgu.scheduler.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:38.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public interface IReminder {
    int getReminderId();

    int getAppointmentId();

    String getCreatedBy();

    LocalDate getCreatedDate();

    String getRemindercol();

    void setRemindercol(String remindercol);

    int getSnoozeIncrement();

    void setSnoozeIncrement(int snoozeIncrement);

    int getSnoozeIncrementTypeId();

    void setSnoozeIncrementTypeId(int snoozeIncrementTypeId);

    ZonedDateTime getReminderDate();

    void setReminderDate(ZonedDateTime reminderDate);

    ZonedDateTime getReminderDate(Locale aDefault);
}
