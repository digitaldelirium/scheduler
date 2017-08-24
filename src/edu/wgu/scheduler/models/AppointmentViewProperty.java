package edu.wgu.scheduler.models;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:45.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentViewProperty extends SimpleObjectProperty<AppointmentView> implements IAppointmentView {

    public AppointmentViewProperty(String title, String description, String location, String contact, String url, String customerName, Timestamp start, Timestamp end, Date createDate, String createdBy, Timestamp lastUpdate) {
        super(new AppointmentView(title, description, location, contact, url, customerName, start, end, createDate, createdBy, lastUpdate));
        init();
    }

    public AppointmentViewProperty(AppointmentView app) {
        super(app);
        init();
    }

    public String getTitle() {
        return getValue().getTitle();
    }

    public ReadOnlyStringProperty titleProperty() {
        return getValue().titleProperty();
    }

    public String getDescription() {
        return getValue().getDescription();
    }

    public ReadOnlyStringProperty descriptionProperty() {
        return getValue().descriptionProperty();
    }

    public String getLocation() {
        return getValue().getLocation();
    }

    public ReadOnlyStringProperty locationProperty() {
        return getValue().locationProperty();
    }

    public String getContact() {
        return getValue().getContact();
    }

    public ReadOnlyStringProperty contactProperty() {
        return getValue().contactProperty();
    }

    public String getUrl() {
        return getValue().getUrl();
    }

    public ReadOnlyStringProperty urlProperty() {
        return getValue().urlProperty();
    }

    public String getCustomerName() {
        return getValue().getCustomerName();
    }

    public ReadOnlyStringProperty customerNameProperty() {
        return getValue().customerNameProperty();
    }

    public ZonedDateTime getStart() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getStart().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<Timestamp> startProperty() {
        return getValue().startProperty();
    }

    public ZonedDateTime getEnd() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getEnd().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<Timestamp> endProperty() {
        return getValue().endProperty();
    }

    public LocalDate getCreateDate() {
        return getValue().getCreateDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public ZonedDateTime getLastUpdate() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getLastUpdate().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<Timestamp> lastUpdatedProperty() {
        return getValue().lastUpdatedProperty();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().titleProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().descriptionProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().locationProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().contactProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().urlProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().customerNameProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().startProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().endProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdatedProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    @Override
    public void set(AppointmentView newValue) {
        super.set(newValue);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public void setValue(AppointmentView v) {
        super.setValue(v);
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getValue().equals(obj);
    }
}
