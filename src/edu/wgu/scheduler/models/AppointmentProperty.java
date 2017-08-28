package edu.wgu.scheduler.models;

import javafx.beans.property.*;

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

    public StringProperty contactProperty() {
        return getValue().contactProperty();
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

    public IntegerProperty customerIdProperty() {
        return getValue().customerIdProperty();
    }

    public String getDescription() {
        return getValue().getDescription();
    }

    public void setDescription(String description) {
        getValue().setDescription(description);
    }

    public StringProperty descriptionProperty() {
        return getValue().descriptionProperty();
    }

    public ZonedDateTime getEnd() {
        return getValue().getEnd();
    }

    public void setEnd(ZonedDateTime end) {
        getValue().setEnd(end);
    }

    public ObjectProperty<ZonedDateTime> endProperty() {
        return getValue().endProperty();
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    public StringProperty lastUpdatedByProperty() {
        return getValue().lastUpdatedByProperty();
    }

    @Override
    public ZonedDateTime getLastUpdate() {
        return getValue().getLastUpdate();
    }

    @Override
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        getValue().setLastUpdate(lastUpdate);
    }

    public ObjectProperty<Timestamp> lastUpdateProperty(){
        return getValue().lastUpdateProperty();
    }

    public String getLocation() {
        return getValue().getLocation();
    }

    public void setLocation(String location) {
        getValue().setLocation(location);
    }

    public StringProperty locationProperty() {
        return getValue().locationProperty();
    }

    public ZonedDateTime getStart() {
        return getValue().getStart();
    }

    public void setStart(ZonedDateTime start) {
        getValue().setStart(start);
    }

    public ObjectProperty<ZonedDateTime> startProperty() {
        return getValue().startProperty();
    }

    public String getTitle() {
        return getValue().getTitle();
    }

    public void setTitle(String title) {
        getValue().setTitle(title);
    }

    public StringProperty titleProperty() {
        return getValue().titleProperty();
    }

    public String getUrl() {
        return getValue().getUrl();
    }

    public void setUrl(String url) {
        getValue().setUrl(url);
    }

    public StringProperty urlProperty() {
        return getValue().urlProperty();
    }

    public void init() {
        if (get() == null) {
            return;
        }

        get().contactProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().customerIdProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().descriptionProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().endProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdatedByProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().startProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().titleProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().urlProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
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
