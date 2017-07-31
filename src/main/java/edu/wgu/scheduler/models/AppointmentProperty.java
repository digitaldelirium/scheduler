package edu.wgu.scheduler.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:46.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentProperty extends SimpleObjectProperty<Appointment> implements IAppointment {
    public AppointmentProperty(LocalDate createDate, int appointmentId, String contact, String createdBy, int customerId, String description, String end, Timestamp lastUpdate, String lastUpdateBy, String location, String start, String title, String url) {
        super(new Appointment(createDate, appointmentId, contact, createdBy, customerId, description, end, lastUpdate, lastUpdateBy, location, start, title, url));
        init();
    }

    public AppointmentProperty(String createdBy, int customerId, String description, ZonedDateTime end, String location, ZonedDateTime start, String title, String url) {
        super(new Appointment(createdBy, customerId, description, end, location, start, title, url));
        init();
    }

    public AppointmentProperty(String createdBy, int customerId, String description, ZonedDateTime end, String location, ZonedDateTime start, String title) {
        super(new Appointment(createdBy, customerId, description, end, location, start, title));
        init();
    }

    public AppointmentProperty(Appointment ap) {
        super(ap);
        init();
    }

    public int getAppointmentId() {
        return getValue().getAppointmentId();
    }

    public String getContact() {
        return getValue().getContact();
    }

    public void setContact(String contact) {
        getValue().setContact(contact);
    }

    public StringProperty contact() {
        return getValue().contact();
    }

    @Override
    public LocalDate getCreateDate() {
        return getValue().getCreateDate();
    }

    @Override
    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public int getCustomerId() {
        return getValue().getCustomerId();
    }

    public void setCustomerId(int customerId) {
        getValue().setCustomerId(customerId);
    }

    public IntegerProperty customerId() {
        return getValue().customerId();
    }

    public String getDescription() {
        return getValue().getDescription();
    }

    public void setDescription(String description) {
        getValue().setDescription(description);
    }

    public StringProperty description() {
        return getValue().description();
    }

    public ZonedDateTime getEnd() {
        return getValue().getEnd();
    }

    public void setEnd(ZonedDateTime end) {
        getValue().setEnd(end);
    }

    public ObjectProperty<ZonedDateTime> end() {
        return getValue().end();
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    public StringProperty lastUpdateBy() {
        return getValue().lastUpdatedBy();
    }

    public String getLocation() {
        return getValue().getLocation();
    }

    public void setLocation(String location) {
        getValue().setLocation(location);
    }

    public StringProperty location() {
        return getValue().location();
    }

    public ZonedDateTime getStart() {
        return getValue().getStart();
    }

    public void setStart(ZonedDateTime start) {
        getValue().setStart(start);
    }

    public ObjectProperty<ZonedDateTime> start() {
        return getValue().start();
    }

    public String getTitle() {
        return getValue().getTitle();
    }

    public void setTitle(String title) {
        getValue().setTitle(title);
    }

    public StringProperty title() {
        return getValue().title();
    }

    public String getUrl() {
        return getValue().getUrl();
    }

    public void setUrl(String url) {
        getValue().setUrl(url);
    }

    public StringProperty url() {
        return getValue().url();
    }

    public void init() {
        if (get() == null) {
            return;
        }

        get().contact().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().customerId().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().description().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().end().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdatedBy().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().start().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().title().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().url().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    @Override
    public void set(Appointment appointment) {
        super.set(appointment);
        init();
    }

    @Override
    public void setValue(Appointment v) {
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
