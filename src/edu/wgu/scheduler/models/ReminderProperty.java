package edu.wgu.scheduler.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 17:40.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class ReminderProperty extends SimpleObjectProperty<Reminder> implements IReminder {

    public ReminderProperty(int reminderId, int appointmentId, String createdBy, Timestamp createdDate, Timestamp reminderDate, String remindercol, int snoozeIncrement, int snoozeIncrementTypeId) {
        super(new Reminder(reminderId, appointmentId, createdBy, createdDate, reminderDate, remindercol, snoozeIncrement, snoozeIncrementTypeId));
        init();
    }

    public ReminderProperty(Reminder reminder) {
        super(reminder);
        init();
    }

    @Override
    public int getReminderId() {
        return getValue().getReminderId();
    }

    @Override
    public int getAppointmentId() {
        return getValue().getAppointmentId();
    }

    @Override
    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    @Override
    public ZonedDateTime getCreatedDate() {
        return getValue().getCreatedDate();
    }

    @Override
    public String getRemindercol() {
        return getValue().getRemindercol();
    }

    @Override
    public void setRemindercol(String remindercol) {
        getValue().setRemindercol(remindercol);
    }

    @Override
    public int getSnoozeIncrement() {
        return getValue().getSnoozeIncrement();
    }

    @Override
    public void setSnoozeIncrement(int snoozeIncrement) {
        getValue().setSnoozeIncrement(snoozeIncrement);
    }

    @Override
    public int getSnoozeIncrementTypeId() {
        return getValue().getSnoozeIncrementTypeId();
    }

    @Override
    public void setSnoozeIncrementTypeId(int snoozeIncrementTypeId) {
        getValue().setSnoozeIncrement(snoozeIncrementTypeId);
    }

    @Override
    public ZonedDateTime getReminderDate() {
        return getValue().getReminderDate();
    }

    @Override
    public void setReminderDate(ZonedDateTime reminderDate) {
        getValue().setReminderDate(reminderDate);
    }

    @Override
    public ZonedDateTime getReminderDate(Locale locale) {
        return getValue().getReminderDate(locale);
    }

    public ObjectProperty<ZonedDateTime> reminderDate() {
        return getValue().reminderDateProperty();
    }

    public StringProperty remindercolProperty() {
        return getValue().remindercolProperty();
    }

    public SimpleIntegerProperty snoozeIncrementProperty() {
        return getValue().snoozeIncrementProperty();
    }

    public SimpleIntegerProperty snoozeIncrementTypeIdProperty() {
        return getValue().snoozeIncrementTypeIdProperty();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().reminderDateProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().remindercolProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().snoozeIncrementProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().snoozeIncrementTypeIdProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    @Override
    public void set(Reminder newValue) {
        super.set(newValue);
        init();
    }

    @Override
    public void setValue(Reminder v) {
        super.setValue(v);
        init();
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getValue().equals(obj);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
