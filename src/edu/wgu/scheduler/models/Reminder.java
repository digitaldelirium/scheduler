package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Reminder implements IReminder {
    private int reminderId;
    @NotNull
    private int appointmentId;
    @NotNull
    private final String createdBy;
    @NotNull
    private final LocalDate createdDate;
    @NotNull
    private ObjectProperty<ZonedDateTime> reminderDate;
    private StringProperty remindercol;
    @NotNull
    private SimpleIntegerProperty snoozeIncrement;
    @NotNull
    private SimpleIntegerProperty snoozeIncrementTypeId;

    public Reminder(int reminderId, int appointmentId, String createdBy, LocalDate createdDate, ZonedDateTime reminderDate, String remindercol, int snoozeIncrement, int snoozeIncrementTypeId) {
        this.reminderId = reminderId;
        this.appointmentId = appointmentId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.reminderDate.set(reminderDate);
        this.remindercol.set(remindercol);
        this.snoozeIncrement.set(snoozeIncrement);
        this.snoozeIncrementTypeId.set(snoozeIncrementTypeId);
    }

    public Reminder(int appointmentId, String createdBy, LocalDate createdDate, ZonedDateTime reminderDate, int snoozeIncrement, int snoozeIncrementTypeId) {
        this.appointmentId = appointmentId;
        this.createdBy = createdBy;
        this.createdDate = LocalDate.now(ZoneId.of("UTC"));
        this.reminderDate.setValue(reminderDate);
        this.snoozeIncrement.setValue(snoozeIncrement);
        this.snoozeIncrementTypeId.setValue(snoozeIncrementTypeId);
    }

    @Override
    public int getReminderId() {
        return reminderId;
    }

    @Override
    public int getAppointmentId() {
        return appointmentId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public String getRemindercol() {
        return remindercol.getValueSafe();
    }

    @Override
    public void setRemindercol(String remindercol) {
        this.remindercol.setValue(remindercol);
    }

    @Override
    public int getSnoozeIncrement() {
        return snoozeIncrement.getValue();
    }

    @Override
    public void setSnoozeIncrement(int snoozeIncrement) {
        this.snoozeIncrement.setValue(snoozeIncrement);
    }

    @Override
    public int getSnoozeIncrementTypeId() {
        return snoozeIncrementTypeId.getValue();
    }

    @Override
    public void setSnoozeIncrementTypeId(int snoozeIncrementTypeId) {
        this.snoozeIncrementTypeId.setValue(snoozeIncrementTypeId);
    }

    @Override
    public ZonedDateTime getReminderDate() {
        return reminderDate.get();
    }

    @Override
    public void setReminderDate(ZonedDateTime reminderDate) {
        this.reminderDate.setValue(reminderDate);
    }

    public ObjectProperty<ZonedDateTime> reminderDateProperty() {
        return reminderDate;
    }

    public StringProperty remindercolProperty() {
        return remindercol;
    }

    public SimpleIntegerProperty snoozeIncrementProperty() {
        return snoozeIncrement;
    }

    public SimpleIntegerProperty snoozeIncrementTypeIdProperty() {
        return snoozeIncrementTypeId;
    }

    @Override
    public ZonedDateTime getReminderDate(Locale aDefault) {
        return reminderDate.getValue();
    }
    @Override
    public String toString() {
        StringBuilder reminder = new StringBuilder("Reminder:\t")
                .append("Reminder Date:\t")
                .append(format("%s\n", getReminderDate(Locale.getDefault())))
                .append("Created Date:\t")
                .append(format("%s\n", getCreatedDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .append("Created By:\t")
                .append(createdBy + "\n");

        if (getRemindercol() != null) {
            reminder.append("Reminder Column:\t")
                    .append(remindercol);
        }
        reminder.append("Snooze Increment:\t")
                .append(snoozeIncrement + "\n");

        return reminder.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reminder reminder = (Reminder) o;

        if (getReminderId() != reminder.getReminderId()) return false;
        return getAppointmentId() == reminder.getAppointmentId();
    }

    @Override
    public int hashCode() {
        return getReminderId();
    }
}
