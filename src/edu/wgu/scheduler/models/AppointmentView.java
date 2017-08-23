package edu.wgu.scheduler.models;

import javafx.beans.property.*;
import javafx.beans.property.ReadOnlyStringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:43.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentView implements IAppointmentView {
    private final Date createdDate;
    private final String createdBy;
    // Interface needs these components
    private ReadOnlyStringWrapper title;
    private ReadOnlyStringWrapper description;
    private ReadOnlyStringWrapper location;
    private ReadOnlyStringWrapper contact;
    private ReadOnlyStringWrapper url;
    private ReadOnlyStringWrapper customerName;
    private ReadOnlyObjectWrapper<Timestamp> start;
    private ReadOnlyObjectWrapper<Timestamp> end;
    private ReadOnlyProperty<Timestamp> lastUpdated;

    public AppointmentView(String title, String description, String location, String contact, String url, String customerName, Timestamp start, Timestamp end, Date createDate, String createdBy, Timestamp lastUpdate) {
        this.title.setValue(title);
        this.description.setValue(description);
        this.location.setValue(location);
        this.contact.setValue(contact);
        this.customerName.setValue(customerName);
        this.start.setValue(start);
        this.end.setValue(end);
        this.createdDate = createDate;
        this.createdBy = createdBy;

    }

    public String getTitle() {
        return title.getValue();
    }

    ReadOnlyStringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.getValue();
    }

    ReadOnlyStringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.getValue();
    }

    ReadOnlyStringProperty locationProperty() {
        return location;
    }

    public String getContact() {
        return contact.getValue();
    }

    ReadOnlyStringProperty contactProperty() {
        return contact;
    }

    public String getUrl() {
        return url.getValueSafe();
    }

    ReadOnlyStringProperty urlProperty() {
        return url;
    }

    public String getCustomerName() {
        return customerName.getValue();
    }

    ReadOnlyStringProperty customerNameProperty() {
        return customerName;
    }

    public ZonedDateTime getStart() {
        return ZonedDateTime.ofInstant(start.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> startProperty() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return ZonedDateTime.ofInstant(end.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> endProperty() {
        return end;
    }

    public LocalDate getCreateDate() {
        return createdDate.toLocalDate();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return ZonedDateTime.ofInstant(lastUpdated.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> lastUpdatedProperty() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppointmentView{");
        sb.append("createdDate=").append(createdDate);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", location=").append(location);
        sb.append(", contact=").append(contact);
        sb.append(", url=").append(url);
        sb.append(", customerName=").append(customerName);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
