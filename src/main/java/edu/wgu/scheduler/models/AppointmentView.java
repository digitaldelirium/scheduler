package edu.wgu.scheduler.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:43.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentView implements IAppointmentView {
    // Interface needs these components
    protected StringProperty title;
    protected StringProperty description;
    protected StringProperty location;
    protected StringProperty contact;
    protected StringProperty url;
    protected StringProperty customerName;
    protected ObjectProperty<Date> start;
    protected ObjectProperty<Date> end;
    protected Date createdDate;
    protected String createdBy;
    protected ObjectProperty<Timestamp> lastUpdated;

    public AppointmentView(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title.getValue();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.getValue();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.getValue();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getContact() {
        return contact.getValue();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public String getUrl() {
        return url.getValueSafe();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public String getCustomerName() {
        return customerName.getValue();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public Date getStart() {
        return start.getValue();
    }

    public ObjectProperty<Date> startProperty() {
        return start;
    }

    public Date getEnd() {
        return end.getValue();
    }

    public ObjectProperty<Date> endProperty() {
        return end;
    }

    public Date getCreateDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdated.getValue();
    }

    public ObjectProperty<Timestamp> lastUpdatedProperty() {
        return lastUpdated;
    }
}
